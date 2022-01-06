package com.project.tgdiscountservice.model.dto;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id", scope = PartnerDto.class)
public class CouponDto {

    private Long id;

    private Long admitadId;

    private String name;

    private String status;

    private PartnerDto partner;

    private String description;

    private List<String> regions;

    private String discount;

    private String species;

    private String promocode;

    private String framesetLink;

    private String gotoLink;

    private String shortName;

    private LocalDateTime dateStart;

    private LocalDateTime dateEnd;

    private String imageUrl;

    private LocalDateTime lastUpdate;
}
