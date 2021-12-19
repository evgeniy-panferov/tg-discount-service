package com.project.tgdiscountservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;


@Getter
@Setter
public class Partner {

    @JsonIgnore
    private Long id;

    @JsonProperty(value = "id")
    private Long admitadId;

    private String name;


    @JsonProperty(value = "image")
    private String imageUrl;

//    @JsonManagedReference
//    private List<Coupon> coupons;
//

    private LocalDateTime lastUpdate;

    @JsonProperty(value = "categories")
    @JsonManagedReference
    private Set<Category> categories;

    private String description;

    private Boolean exclusive;

}
