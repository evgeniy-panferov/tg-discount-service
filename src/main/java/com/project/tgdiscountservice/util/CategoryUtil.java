package com.project.tgdiscountservice.util;

import com.project.tgdiscountservice.model.Category;
import com.project.tgdiscountservice.model.dto.CategoryDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CategoryUtil {

    public static Category fromDto(CategoryDto dto) {
        Category category = new Category();
        category.setId(dto.getId());
        category.setName(dto.getName());
        category.setAdmitadId(dto.getAdmitadId());
        category.setLanguage(dto.getLanguage());
        return category;
    }

    public static Set<Category> fromDtos(Set<CategoryDto> categories) {
        return categories
                .stream().map(CategoryUtil::fromDto)
                .collect(Collectors.toSet());
    }

    public static Category create(String text) {
        var category = new Category();
        category.setId(-1L);
        category.setName(text);
        category.setAdmitadId(-1L);
        category.setLanguage("RU");
        return category;
    }
}
