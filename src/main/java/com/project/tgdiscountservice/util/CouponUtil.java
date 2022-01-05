package com.project.tgdiscountservice.util;

import com.project.tgdiscountservice.model.Coupon;
import com.project.tgdiscountservice.model.dto.CouponDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CouponUtil {


    public static List<Coupon> fromDtos(List<CouponDto> couponDtos) {
        return couponDtos.stream()
                .map(CouponUtil::fromDto)
                .collect(Collectors.toList());
    }

    public static Coupon fromDto(CouponDto couponDto) {
        var coupon = new Coupon();
        coupon.setId(couponDto.getId());
        coupon.setAdmitadId(couponDto.getAdmitadId());
        coupon.setName(couponDto.getName());
        coupon.setStatus(couponDto.getStatus());
        coupon.setPartner(couponDto.getPartner());
        coupon.setDescription(couponDto.getDescription());
        coupon.setRegions(couponDto.getRegions());
        coupon.setDiscount(couponDto.getDiscount());
        coupon.setSpecies(couponDto.getSpecies());
        coupon.setPromocode(couponDto.getPromocode());
        coupon.setFramesetLink(couponDto.getFramesetLink());
        coupon.setGotoLink(couponDto.getGotoLink());
        coupon.setShortName(couponDto.getShortName());
        coupon.setDateStart(couponDto.getDateStart());
        coupon.setDateEnd(couponDto.getDateEnd());
        coupon.setImageUrl(couponDto.getImageUrl());
        coupon.setLastUpdate(couponDto.getLastUpdate());
        return coupon;
    }
}
