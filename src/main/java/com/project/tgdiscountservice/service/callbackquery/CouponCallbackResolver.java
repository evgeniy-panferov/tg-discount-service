package com.project.tgdiscountservice.service.callbackquery;

import com.project.tgdiscountservice.http.TelegramSender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

@Slf4j
@Service
@RequiredArgsConstructor
public class CouponCallbackResolver implements TelegramCallbackResolver {

    private final TelegramSender sender;

    @Override
    public void prepareMessage(CallbackQuery query) {

    }
}
