package com.project.tgdiscountservice.http;

import com.project.tgdiscountservice.cache.CouponCacheImpl;
import com.project.tgdiscountservice.model.Coupon;
import com.project.tgdiscountservice.model.dto.CouponDto;
import com.project.tgdiscountservice.util.CouponUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/coupons")
public class CouponController {

    private final CouponCacheImpl couponCache;

    @PostMapping
    public ResponseEntity<?> couponsHandler(@RequestBody List<CouponDto> couponsDto) {
        log.info("CouponController couponsHandler - {}", couponsDto);
        List<Coupon> coupons = CouponUtil.fromDtos(couponsDto);

        Map<String, Coupon> couponById = coupons
                .stream()
                .collect(Collectors.toMap(coupon -> coupon.getId().toString(), Function.identity()));

        couponCache.saveAll(couponById);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
