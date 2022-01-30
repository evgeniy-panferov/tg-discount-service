package com.project.tgdiscountservice.service.handlers;

import com.project.tgdiscountservice.model.inner.InnerUpdate;
import com.project.tgdiscountservice.service.handlers.inlinequeries.QueryHandler;
import com.project.tgdiscountservice.service.parser.Parser;
import com.project.tgdiscountservice.service.parser.ParserService;
import com.project.tgdiscountservice.service.handlers.commands.MessageHandler;
import com.project.tgdiscountservice.service.handlers.callback.CallBackHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.project.tgdiscountservice.service.parser.ParserCommand.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class UpdateResolverImpl {

    private final List<CallBackHandler> callBackHandlers;
    private final List<MessageHandler> messageHandlers;
    private final List<QueryHandler> queryHandlers;
    private final ParserService factory;

    public void prepareMessage(InnerUpdate update) {
        log.info("UpdateResolverImpl prepareMessage - {}", update);
        String messageType = "";
        if (update.getMessage() != null) {
            messageType = MESSAGE.getCommand();
        }
        if (update.getCallbackQuery() != null) {
            messageType = CALLBACK.getCommand();
        }
        if (update.getInlineQuery() != null) {
            messageType = INLINE_QUERY.getCommand();
        }
        Parser parser = factory.parseUpdate(messageType, update);

        messageHandlers.forEach(service -> service.prepareMessage(update, parser));

        callBackHandlers.forEach(service -> service.prepareMessage(update, parser));

        queryHandlers.forEach(service -> service.prepareMessage(update, parser));

    }
}
