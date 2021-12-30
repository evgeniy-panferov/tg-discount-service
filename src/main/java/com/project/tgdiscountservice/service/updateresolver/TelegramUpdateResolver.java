package com.project.tgdiscountservice.service.updateresolver;

import com.project.tgdiscountservice.model.inner.InnerUpdate;
import org.springframework.stereotype.Service;

@Service
public interface TelegramUpdateResolver {

    void prepareMessage(InnerUpdate update);
}
