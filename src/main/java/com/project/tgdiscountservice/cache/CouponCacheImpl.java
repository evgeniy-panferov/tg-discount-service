package com.project.tgdiscountservice.cache;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.project.tgdiscountservice.model.Coupon;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Slf4j
@Component
public class CouponCacheImpl implements CouponCache {

    public static final Long DEFAULT_CACHE_TIMEOUT = 60000L;

    protected Cache<String, Coupon> couponCache;

    {
        couponCache = Caffeine.newBuilder()
                .expireAfterWrite(Duration.ofSeconds(DEFAULT_CACHE_TIMEOUT))
                .build();
    }

    @Override
    public Coupon find(String id) {
        log.info("CouponCacheImpl find - {}", id);
        return couponCache.get(id, null);
    }

    @Override
    public void save(Coupon coupon) {
        log.info("CouponCacheImpl save - {}", coupon);
        couponCache.put(coupon.getAdmitadId().toString(), coupon);
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
