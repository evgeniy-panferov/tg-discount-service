package com.project.tgdiscountservice.client;

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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class DiscountClientAdapterImpl implements DiscountAdapter {

    private final WebClient discountServiceClient;


    public List<Category> getCategories() {

        List<CategoryDto> categoriesDto = discountServiceClient
                .get()
                .uri("/categories")
                .retrieve()
                .toEntityList(CategoryDto.class)
                .block()
                .getBody();

        Set<Category> categories = CategoryUtil.fromDtos(new HashSet<>(categoriesDto));
        return new ArrayList<>(categories);
    }

    public List<Partner> getPartners() {
        List<PartnerDto> partnersDto = discountServiceClient
                .get()
                .uri("/partners")
                .retrieve()
                .toEntityList(PartnerDto.class)
                .block()
                .getBody();

        return PartnerUtil.fromDtos(partnersDto);

    }

    public List<Partner> getPartnersById(Long id) {
        List<PartnerDto> partnerDtos = discountServiceClient
                .get()
                .uri("/partners/" + id)
                .retrieve()
                .toEntityList(PartnerDto.class)
                .block()
                .getBody();

        return PartnerUtil.fromDtos(partnerDtos);
    }

    public List<Coupon> getCoupons() {
        List<CouponDto> couponsDto = discountServiceClient
                .get()
                .uri("/coupons")
                .retrieve()
                .toEntityList(CouponDto.class)
                .block()
                .getBody();

        return CouponUtil.fromDtos(couponsDto);
    }
}
