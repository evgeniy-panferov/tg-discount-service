//package com.project.tgdiscountservice.http;
//
//import com.project.tgdiscountservice.service.CouponService;
//import com.project.tgdiscountservice.service.updateresolver.CouponUpdateResolver;
//import lombok.RequiredArgsConstructor;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequiredArgsConstructor
//public class CouponController {
//
//    private final CouponUpdateResolver couponUpdateResolver;
//    private final CouponService couponService;
//
//    @GetMapping("chatId/{chatId}/coupons/{categoryId}")
//    public void getCouponsByCategory(@PathVariable Long chatId, @PathVariable Long categoryId) {
//        couponService.getCoupons(chatId, categoryId);
//    }
//
//}
