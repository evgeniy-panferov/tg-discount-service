package com.project.tgdiscountservice.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;


@Getter
@Setter
public class Partner {

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
