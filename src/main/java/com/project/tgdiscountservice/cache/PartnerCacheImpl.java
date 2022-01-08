package com.project.tgdiscountservice.cache;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.project.tgdiscountservice.client.DiscountClientAdapterImpl;
import com.project.tgdiscountservice.model.Coupon;
import com.project.tgdiscountservice.model.Partner;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PartnerCacheImpl implements PartnerCache {

    private final Cache<String, Partner> partnerCache;
    private final DiscountClientAdapterImpl discountClientAdapter;
    public static final Long DEFAULT_CACHE_TIMEOUT = 60000L;

    public PartnerCacheImpl(DiscountClientAdapterImpl discountClientAdapter) {
        this.discountClientAdapter = discountClientAdapter;
        partnerCache = Caffeine.newBuilder()
                .expireAfterWrite(Duration.ofSeconds(DEFAULT_CACHE_TIMEOUT))
                .build();
    }

    @PostConstruct
    public void init() {
        //TODO Create a service(queue or scheduler) that will update cache
        List<Partner> partners = discountClientAdapter.getPartners();
        Map<String, Partner> partnerById = partners
                .stream()
                .collect(Collectors.toMap(partner -> partner.getId().toString(), Function.identity()));

        saveAll(partnerById);
    }


    @Override
    public List<Coupon> findCoupons(String id) {
        log.info("PartnerCacheImpl find - {}", id);
        return Objects.requireNonNull(partnerCache.getIfPresent(id)).getCoupons();
    }

    @Override
    public void save(Partner partner) {
        log.info("PartnerCacheImpl save - {}", partner);
        partnerCache.put(partner.getAdmitadId().toString(), partner);
    }

    @Override
    public void clear() {
        log.info("PartnerCacheImpl clear cache");
        partnerCache.invalidateAll();
    }

    @Override
    public void invalidatePartner(String id) {
        log.info("PartnerCacheImpl invalidatePartner - {}", id);
        partnerCache.invalidate(id);
    }

    @Override
    public List<Partner> findAll() {
        log.info("PartnerCacheImpl findAll");
        return new ArrayList<>(partnerCache.asMap().values());
    }

    @Override
    public void saveAll(Map<String, Partner> partnerById) {
        log.info("PartnerCacheImpl saveAll - {}", partnerById);
        partnerCache.putAll(partnerById);
    }
}
