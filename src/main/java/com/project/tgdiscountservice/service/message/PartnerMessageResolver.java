package com.project.tgdiscountservice.service.message;

import com.project.tgdiscountservice.http.TelegramSender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;

@Service
@RequiredArgsConstructor
public class PartnerMessageResolver implements TelegramMessageResolver {

    private final TelegramSender sender;

    @Override
    public void prepareMessage(Message message) {
    }
}
