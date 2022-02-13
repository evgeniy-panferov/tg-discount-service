package com.project.tgdiscountservice.client;

import com.project.tgdiscountservice.client.request.TgRequest;
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
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.*;

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
                .retryWhen(Retry.backoff(3, Duration.ofSeconds(5)))
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
                .retryWhen(Retry.backoff(3, Duration.ofSeconds(5)))
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
                .retryWhen(Retry.backoff(3, Duration.ofSeconds(5)))
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
                .retryWhen(Retry.backoff(3, Duration.ofSeconds(5)))
                .block()
                .getBody();

        return CouponUtil.fromDtos(couponsDto);
    }

    public List<Coupon> searchCoupons(TgRequest request) {
        List<CouponDto> couponsDto = discountServiceClient
                .post()
                .uri("/coupons/search")
                .bodyValue(request)
                .retrieve()
                .toEntityList(CouponDto.class)
                .block()
                .getBody();

        return couponsDto.isEmpty()? Collections.emptyList() : CouponUtil.fromDtos(couponsDto);
    }

}
