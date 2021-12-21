package com.project.tgdiscountservice.service.message;

import com.project.tgdiscountservice.http.TelegramSender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
@Service
@RequiredArgsConstructor
public class CouponMessageResolver implements TelegramMessageResolver {

    private final TelegramSender sender;

    @Override
    public void prepareMessage(Message message) {
        String chatId = message.getChatId().toString();
        try {
            sender.execute(new SendMessage(chatId, "Салам"));
        } catch (TelegramApiException e) {
            log.error("Сообщение не отправлено");
        }
    }
}
