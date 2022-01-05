package com.project.tgdiscountservice.cache;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.project.tgdiscountservice.model.Category;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class CategoryCacheImpl implements CategoryCache {
    public static final Long DEFAULT_CACHE_TIMEOUT = 60000L;

    private final Cache<String, Category> categoryCache;

    public CategoryCacheImpl() {
        categoryCache = Caffeine.newBuilder()
                .expireAfterWrite(Duration.ofSeconds(DEFAULT_CACHE_TIMEOUT))
                .build();
    }

    @Override
    public Category find(String id) {
        log.info("CategoryCacheImpl find - {}", id);
        return categoryCache.get(id, null);
    }

    @Override
    public List<Category> findAll() {
        return new ArrayList<>(categoryCache.asMap().values());
    }

    @Override
    public void save(Category category) {
        log.info("CategoryCacheImpl save - {}", category);
        categoryCache.put(category.getAdmitadId().toString(), category);
    }

    @Override
    public void saveAll(Map<String, Category> categoryById) {
        log.info("CategoryCacheImpl saveAll - {}", categoryById);
        categoryCache.putAll(categoryById);
    }

    @Override
    public void clear() {
        log.info("CategoryCacheImpl clear");
        categoryCache.invalidateAll();
    }

    @Override
    public void invalidateCategory(String id) {
        log.info("CategoryCacheImpl invalidateCategory - {}", id);
        categoryCache.invalidate(id);
    }
}
