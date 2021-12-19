package com.project.tgdiscountservice.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;


import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
public class Coupon {

    @JsonIgnore

    private Long id;

    @JsonProperty(value = "id")

    private Long admitadId;


    private String name;

    private String status;

    @JsonProperty(value = "campaign")

    @JsonBackReference
    private Partner partner;

    private String description;

    private List<String> regions;

    private String discount;

    private String species;

    private String promocode;

    @JsonProperty(value = "frameset_link")
    private String framesetLink;

    @JsonProperty(value = "goto_link")
    private String gotoLink;

    @JsonProperty(value = "short_name")
    private String shortName;

    @JsonProperty(value = "date_start")
    private LocalDateTime dateStart;

    @JsonProperty(value = "date_end")
    private LocalDateTime dateEnd;

    @JsonProperty(value = "image")
    private String imageUrl;

    private LocalDateTime lastUpdate;

}
