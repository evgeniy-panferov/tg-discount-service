package com.project.tgdiscountservice.client;

import com.project.tgdiscountservice.cache.CategoryCacheImpl;
import com.project.tgdiscountservice.model.Category;
import com.project.tgdiscountservice.model.CategoryDto;
import com.project.tgdiscountservice.model.Coupon;
import com.project.tgdiscountservice.model.Partner;
import com.project.tgdiscountservice.util.CategoryUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class DiscountClientAdapterImpl implements DiscountAdapter {

    private final WebClient discountServiceClient;
    private final CategoryCacheImpl categoryCache;

    public List<Category> getCategories() {

        List<CategoryDto> categoriesDto = discountServiceClient
                .get()
                .uri("/categories")
                .retrieve()
                .bodyToFlux(CategoryDto.class)
                .collectList()
                .block();

        List<Category> categories = CategoryUtil.fromDtos(categoriesDto);
        Map<String, Category> categoryById = categories.stream()
                .collect(Collectors.toMap(category -> category.getAdmitadId().toString(), Function.identity()));

        categoryCache.saveAll(categoryById);
        return categories;
    }

    public List<Coupon> getCouponsByPartnerId(Long id) {
        return discountServiceClient
                .get()
                .uri("/coupons/" + id)
                .retrieve()
                .bodyToFlux(Coupon.class)
                .collectList()
                .block();
    }

    public List<Partner> getPartners() {
        return discountServiceClient
                .get()
                .uri("/partners")
                .retrieve()
                .bodyToFlux(Partner.class)
                .collectList()
                .block();
    }

    public List<Partner> getPartnersByCategoryId(Long id) {
        return discountServiceClient
                .get()
                .uri("/partners/" + id)
                .retrieve()
                .bodyToFlux(Partner.class)
                .collectList()
                .block();
    }
}
