package com.project.tgdiscountservice.service.message;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;

@Service
public interface TelegramMessageResolver {

    void prepareMessage(Message message);
}
