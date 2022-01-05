package com.project.tgdiscountservice.cache;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.project.tgdiscountservice.model.Partner;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class PartnerCacheImpl implements PartnerCache {

    private final Cache<String, Partner> partnerCache;
    public static final Long DEFAULT_CACHE_TIMEOUT = 60000L;

    public PartnerCacheImpl() {
        partnerCache = Caffeine.newBuilder()
                .expireAfterWrite(Duration.ofSeconds(DEFAULT_CACHE_TIMEOUT))
                .build();
    }

    @Override
    public Partner find(String id) {
        log.info("PartnerCacheImpl find - {}", id);
        return partnerCache.get(id, null);
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
        log.info("CategoryCacheImpl invalidatePartner - {}", id);
        partnerCache.invalidate(id);
    }

    @Override
    public List<Partner> findAll() {
        log.info("CategoryCacheImpl findAll");
        return new ArrayList<>(partnerCache.asMap().values());
    }

    @Override
    public void saveAll(Map<String, Partner> partnerById) {
        log.info("CategoryCacheImpl saveAll - {}", partnerById);
        partnerCache.putAll(partnerById);
    }
}
