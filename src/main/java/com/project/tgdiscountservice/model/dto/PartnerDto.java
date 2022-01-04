package com.project.tgdiscountservice.model.dto;

import com.project.tgdiscountservice.model.Category;
import com.project.tgdiscountservice.model.Coupon;
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

    private List<Coupon> coupons;

    private LocalDateTime lastUpdate;

    private Set<Category> categories;

    private String description;

    private Boolean exclusive;

}
