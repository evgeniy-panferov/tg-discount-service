package com.project.tgdiscountservice.cache;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.project.tgdiscountservice.model.inner.InnerMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Slf4j
@Component
public class MessageCacheImpl implements MessageCache {

    public static final Long DEFAULT_CACHE_TIMEOUT = 60000L;

    protected Cache<String, InnerMessage> messageCache;

    {
        messageCache = Caffeine.newBuilder()
                .expireAfterWrite(Duration.ofSeconds(DEFAULT_CACHE_TIMEOUT))
                .build();
    }

    @Override
    public InnerMessage find(String id) {
        log.info("MessageCacheImpl find - {}", id);
        return messageCache.get(id, null);
    }

    @Override
    public void save(InnerMessage message) {
        log.info("MessageCacheImpl save - {}", message);
        messageCache.put(message.getMessageId().toString(), message);
    }

    @Override
    public void clear() {
        log.info("MessageCacheImpl clear");
        messageCache.invalidateAll();
    }

    @Override
    public void invalidateMessage(String id) {
        log.info("MessageCacheImpl invalidateMessage");
        messageCache.invalidate(id);
    }
}
