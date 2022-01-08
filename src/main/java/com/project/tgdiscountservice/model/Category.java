package com.project.tgdiscountservice.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;


@Getter
@Setter
public class Category {

    private Long id;

    private String name;

    private Long admitadId;

    private String language;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equals(name, category.name) && Objects.equals(admitadId, category.admitadId) && Objects.equals(language, category.language);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, admitadId, language);
    }
}
