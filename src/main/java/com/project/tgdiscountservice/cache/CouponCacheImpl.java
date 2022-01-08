package com.project.tgdiscountservice.cache;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.project.tgdiscountservice.client.DiscountClientAdapterImpl;
import com.project.tgdiscountservice.model.Coupon;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Component
public class CouponCacheImpl implements CouponCache {

    public static final Long DEFAULT_CACHE_TIMEOUT = 60000L;

    protected Cache<String, Coupon> couponCache;
    private final DiscountClientAdapterImpl discountClientAdapter;

    public CouponCacheImpl(DiscountClientAdapterImpl discountClientAdapter) {
        this.discountClientAdapter = discountClientAdapter;
        couponCache = Caffeine.newBuilder()
                .expireAfterWrite(Duration.ofSeconds(DEFAULT_CACHE_TIMEOUT))
                .build();
    }

    @PostConstruct
    public void init() {
        //TODO Create a service(queue or scheduler) that will update cache
        List<Coupon> coupons = discountClientAdapter.getCoupons();

        Map<String, Coupon> couponById = coupons
                .stream()
                .collect(Collectors.toMap(coupon -> coupon.getId().toString(), Function.identity()));

        saveAll(couponById);
    }

    @Override
    public Coupon findCoupons(String id) {
        log.info("CouponCacheImpl find - {}", id);
        return couponCache.getIfPresent(id);
    }

    @Override
    public List<Coupon> findAll() {
        log.info("CategoryCacheImpl findAll");
        return new ArrayList<>(couponCache.asMap().values());
    }

    @Override
    public void save(Coupon coupon) {
        log.info("CouponCacheImpl save - {}", coupon);
        couponCache.put(coupon.getAdmitadId().toString(), coupon);
    }

    @Override
    public void saveAll(Map<String, Coupon> couponById) {
        log.info("CouponCacheImpl saveAll - {}", couponById);
        couponCache.putAll(couponById);
    }

    @Override
    public void clear() {
        log.info("CouponCacheImpl clear");
        couponCache.invalidateAll();
    }

    @Override
    public void invalidateMessage(String id) {
        log.info("CouponCacheImpl invalidateCoupons");
        couponCache.invalidate(id);
    }
}
