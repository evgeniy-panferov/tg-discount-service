package com.project.tgdiscountservice.service.callbackquery;

import com.project.tgdiscountservice.http.TelegramSender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

@Service
@RequiredArgsConstructor
public class PartnerCallbackResolver implements TelegramCallbackResolver {

    private final TelegramSender sender;

    @Override
    public void prepareMessage(CallbackQuery query) {
    }
}
