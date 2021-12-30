package com.project.tgdiscountservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class Category {

    @JsonIgnore
    private Long id;

    private String name;

    @JsonProperty(value = "id")
    private Long admitadId;

    private String language;

}
