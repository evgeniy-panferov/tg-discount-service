package com.project.tgdiscountservice.http;

import com.project.tgdiscountservice.configuration.TelegramProperties;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.bots.DefaultBotOptions;

@Component
public class TelegramSender extends DefaultAbsSender {

    private final TelegramProperties properties;

    protected TelegramSender(TelegramProperties properties) {
        super(new DefaultBotOptions());
        this.properties = properties;
    }

    @Override
    public String getBotToken() {
        return properties.getBotToken();
    }
}
