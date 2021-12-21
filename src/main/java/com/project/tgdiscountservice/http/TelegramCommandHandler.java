package com.project.tgdiscountservice.http;

import com.project.tgdiscountservice.configuration.TelegramProperties;
import com.project.tgdiscountservice.service.TelegramUpdateChecker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
@RequiredArgsConstructor
@Component
public class TelegramCommandHandler extends TelegramLongPollingBot {

    private final TelegramProperties properties;
    private final TelegramUpdateChecker telegramServiceResolver;

    @Override
    public void onUpdateReceived(Update update) {
        log.info(String.valueOf(update));
        telegramServiceResolver.resolveUpdate(update);
    }

    @Override
    public String getBotUsername() {
        return properties.getBotUsername();
    }

    @Override
    public String getBotToken() {
        return properties.getBotToken();
    }

}
