package com.project.tgdiscountservice.client;

import com.project.tgdiscountservice.configuration.TelegramProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
@RequiredArgsConstructor
@Component
public class TelegramBotImpl extends TelegramLongPollingBot {

    private final TelegramProperties properties;

    @Override
    public void onUpdateReceived(Update update) {
        log.info(String.valueOf(update));
        Long chatId = update.getMessage().getChatId();
        try {
            execute(new SendMessage(String.valueOf(chatId), "Салам"));
        } catch (TelegramApiException e) {
            log.error("Сообщение не отправилось");
        }
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
