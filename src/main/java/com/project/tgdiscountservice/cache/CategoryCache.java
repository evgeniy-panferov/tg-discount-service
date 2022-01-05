package com.project.tgdiscountservice.cache;

import com.project.tgdiscountservice.model.Category;

import java.util.List;
import java.util.Map;

public interface CategoryCache extends TelegramRepository<Category> {

    Category find(String id);

    List<Category> findAll();

    void save(Category t);

    void saveAll(Map<String, Category> categoryByAdmitadId);

    void clear();

    void invalidateCategory(String id);

}
