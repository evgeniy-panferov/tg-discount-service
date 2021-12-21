package com.project.tgdiscountservice.service.message;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageResolverImpl implements TelegramMessageResolver {

    private final List<TelegramMessageResolver> resolvers;

    @Override
    public void prepareMessage(Message message) {
        resolvers.forEach(service -> service.prepareMessage(message));
    }
}
