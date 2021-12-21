package com.project.tgdiscountservice.service;

import com.project.tgdiscountservice.service.callbackquery.CallbackResolverImpl;
import com.project.tgdiscountservice.service.message.MessageResolverImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
@Service
@RequiredArgsConstructor
public class TelegramUpdateChecker {

    private final CallbackResolverImpl callbackResolver;
    private final MessageResolverImpl messageResolver;

    public void resolveUpdate(Update update) {
        Message message = update.getMessage();
        messageResolver.prepareMessage(message);

        CallbackQuery callbackQuery = update.getCallbackQuery();
        callbackResolver.prepareMessage(callbackQuery);
    }

}
