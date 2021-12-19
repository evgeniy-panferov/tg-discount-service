package com.project.tgdiscountservice.client;

import com.project.tgdiscountservice.model.CategoryDto;
import com.project.tgdiscountservice.model.Coupon;
import com.project.tgdiscountservice.model.Partner;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DiscountClientAdapterImpl implements DiscountAdapter {

    private final WebClient discountServiceClient;

    public List<CategoryDto> getCategories() {
        return discountServiceClient
                .get()
                .uri("/categories")
                .retrieve()
                .bodyToFlux(CategoryDto.class)
                .collectList()
                .block();
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
                .uri("/partners/"+ id)
                .retrieve()
                .bodyToFlux(Partner.class)
                .collectList()
                .block();
    }
}
