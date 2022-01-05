package com.project.tgdiscountservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@EqualsAndHashCode
public class Category {

    @JsonIgnore
    private Long id;

    private String name;

    @JsonProperty(value = "id")
    private Long admitadId;

    private String language;


}
