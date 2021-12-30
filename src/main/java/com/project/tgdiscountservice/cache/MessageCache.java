package com.project.tgdiscountservice.cache;

import com.project.tgdiscountservice.model.inner.InnerMessage;

public interface MessageCache extends TelegramRepository<InnerMessage> {

    InnerMessage find(String id);

    void save(InnerMessage t);

    void clear();

    void invalidateMessage(String id);
}
