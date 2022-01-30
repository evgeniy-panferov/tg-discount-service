package com.project.tgdiscountservice.service;

import com.project.tgdiscountservice.service.handlers.UpdateResolverImpl;
import com.project.tgdiscountservice.util.UpdateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
@Service
@RequiredArgsConstructor
public class TelegramUpdateChecker {

    private final UpdateResolverImpl callbackResolver;

    public void resolveUpdate(Update update) {
        callbackResolver.prepareMessage(UpdateUtil.fromDto(update));
    }

}
