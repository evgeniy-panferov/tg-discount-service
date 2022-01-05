package com.project.tgdiscountservice.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;


@Getter
@Setter
public class PartnerDto {

    private Long id;

    private Long admitadId;

    private String name;

    private String imageUrl;

    private List<CouponDto> coupons;

    private LocalDateTime lastUpdate;

    private Set<CategoryDto> categories;

    private String description;

    private Boolean exclusive;

}
