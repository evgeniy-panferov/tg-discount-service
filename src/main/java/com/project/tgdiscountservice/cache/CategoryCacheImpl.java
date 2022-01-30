package com.project.tgdiscountservice.cache;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.project.tgdiscountservice.client.DiscountClientAdapterImpl;
import com.project.tgdiscountservice.model.Category;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Component
public class CategoryCacheImpl implements CategoryCache {
    public static final Long DEFAULT_CACHE_TIMEOUT = 60000L;

    private final Cache<String, Category> categoryCache;
    private final DiscountClientAdapterImpl discountClientAdapter;

    @PostConstruct
    public void init() {
        //TODO Create a service(queue or scheduler) that will update cache
        ExecutorService cacheInit = Executors.newSingleThreadExecutor();
        cacheInit.execute(this::cacheInit);
    }

    public void cacheInit() {
        List<Category> categories = discountClientAdapter.getCategories();

        Map<String, Category> categoryById = categories.stream()
                .collect(Collectors.toMap(category -> category.getId().toString(), Function.identity()));

        saveAll(categoryById);
    }

    public CategoryCacheImpl(DiscountClientAdapterImpl discountClientAdapter) {
        this.discountClientAdapter = discountClientAdapter;
        categoryCache = Caffeine.newBuilder()
                .expireAfterWrite(Duration.ofSeconds(DEFAULT_CACHE_TIMEOUT))
                .build();
    }

    @Override
    public Category findCoupons(String id) {
        log.info("CategoryCacheImpl find - {}", id);
        return categoryCache.getIfPresent(id);
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
