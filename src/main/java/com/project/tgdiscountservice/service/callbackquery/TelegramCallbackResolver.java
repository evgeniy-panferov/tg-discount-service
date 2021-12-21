package com.project.tgdiscountservice.service.callbackquery;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

@Service
public interface TelegramCallbackResolver {

    void prepareMessage(CallbackQuery query);
}
