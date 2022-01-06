package com.project.tgdiscountservice.model.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryDto {

    private Long admitadId;

    private String name;

    private String language;

    @JsonBackReference("categories")
    private PartnerDto partner;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CategoryDto that = (CategoryDto) o;

        return admitadId != null ? admitadId.equals(that.admitadId) : that.admitadId == null;
    }

    @Override
    public int hashCode() {
        return admitadId != null ? admitadId.hashCode() : 0;
    }
}
