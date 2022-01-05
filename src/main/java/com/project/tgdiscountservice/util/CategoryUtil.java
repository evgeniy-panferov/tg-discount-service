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
}
