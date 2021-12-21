package com.project.tgdiscountservice.service.callbackquery;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CallbackResolverImpl implements TelegramCallbackResolver {

    private final List<TelegramCallbackResolver> resolvers;

    @Override
    public void prepareMessage(CallbackQuery query) {
        resolvers.forEach(service -> service.prepareMessage(query));
    }
}
