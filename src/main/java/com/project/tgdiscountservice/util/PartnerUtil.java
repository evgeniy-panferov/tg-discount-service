package com.project.tgdiscountservice.util;

import com.project.tgdiscountservice.model.Partner;
import com.project.tgdiscountservice.model.dto.PartnerDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PartnerUtil {

    public static List<Partner> fromDtos(List<PartnerDto> partnersDto) {
        return partnersDto.stream()
                .map(PartnerUtil::fromDto)
                .collect(Collectors.toList());
    }

    public static Partner fromDto(PartnerDto partnerDto) {
        var partner = new Partner();
        partner.setId(partnerDto.getId());
        partner.setAdmitadId(partnerDto.getAdmitadId());
        partner.setName(partnerDto.getName());
        partner.setImageUrl(partnerDto.getImageUrl());
        partner.setCoupons(CouponUtil.fromDtos(partnerDto.getCoupons()));
        partner.setLastUpdate(partnerDto.getLastUpdate());
        partner.setCategories(CategoryUtil.fromDtos(partnerDto.getCategories()));
        partner.setDescription(partnerDto.getDescription());
        partner.setExclusive(partnerDto.getExclusive());
        return partner;

    }

}
