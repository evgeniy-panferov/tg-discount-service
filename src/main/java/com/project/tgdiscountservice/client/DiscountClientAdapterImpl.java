package com.project.tgdiscountservice.client;

import com.project.tgdiscountservice.cache.CategoryCacheImpl;
import com.project.tgdiscountservice.cache.CouponCacheImpl;
import com.project.tgdiscountservice.cache.PartnerCacheImpl;
import com.project.tgdiscountservice.model.Category;
import com.project.tgdiscountservice.model.Coupon;
import com.project.tgdiscountservice.model.Partner;
import com.project.tgdiscountservice.model.dto.CategoryDto;
import com.project.tgdiscountservice.model.dto.CouponDto;
import com.project.tgdiscountservice.model.dto.PartnerDto;
import com.project.tgdiscountservice.util.CategoryUtil;
import com.project.tgdiscountservice.util.CouponUtil;
import com.project.tgdiscountservice.util.PartnerUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class DiscountClientAdapterImpl implements DiscountAdapter {

    private final WebClient discountServiceClient;
    private final CategoryCacheImpl categoryCache;
    private final PartnerCacheImpl partnerCache;
    private final CouponCacheImpl couponCache;


    public List<Category> getCategories() {

        List<CategoryDto> categoriesDto = discountServiceClient
                .get()
                .uri("/categories")
                .retrieve()
                .bodyToFlux(CategoryDto.class)
                .collectList()
                .block();

        Set<Category> categories = CategoryUtil.fromDtos(new HashSet<>(categoriesDto));

        Map<String, Category> categoryById = categories.stream()
                .collect(Collectors.toMap(category -> category.getId().toString(), Function.identity()));

        categoryCache.saveAll(categoryById);
        return new ArrayList<>(categories);
    }

    public List<Coupon> getCouponsByPartnerId(Long id) {
        List<CouponDto> couponsDto = discountServiceClient
                .get()
                .uri("/coupons/" + id)
                .retrieve()
                .bodyToFlux(CouponDto.class)
                .collectList()
                .block();

        List<Coupon> coupons = CouponUtil.fromDtos(couponsDto);

        Map<String, Coupon> couponById = coupons
                .stream()
                .collect(Collectors.toMap(coupon -> coupon.getId().toString(), Function.identity()));

        couponCache.saveAll(couponById);
        return coupons;
    }

    public List<Partner> getPartners() {
        List<PartnerDto> partnersDto = discountServiceClient
                .get()
                .uri("/partners")
                .retrieve()
                .bodyToFlux(PartnerDto.class)
                .collectList()
                .block();

        List<Partner> partners = PartnerUtil.fromDtos(partnersDto);
        Map<String, Partner> partnerById = partners
                .stream()
                .collect(Collectors.toMap(partner -> partner.getId().toString(), Function.identity()));

        partnerCache.saveAll(partnerById);
        return partners;
    }

    public List<Partner> getPartnersById(Long id) {
        List<PartnerDto> partnerDtos = discountServiceClient
                .get()
                .uri("/partners/" + id)
                .retrieve()
                .bodyToFlux(PartnerDto.class)
                .collectList()
                .block();

        return PartnerUtil.fromDtos(partnerDtos);
    }
}
