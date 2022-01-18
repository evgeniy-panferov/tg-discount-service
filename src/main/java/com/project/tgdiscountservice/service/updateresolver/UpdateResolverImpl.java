package com.project.tgdiscountservice.service.updateresolver;

import com.project.tgdiscountservice.model.inner.InnerUpdate;
import com.project.tgdiscountservice.service.parser.Parser;
import com.project.tgdiscountservice.service.parser.ParserService;
import com.project.tgdiscountservice.service.updateresolver.requestresolver.CallBackResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.project.tgdiscountservice.service.parser.ParserCommand.*;

@Service
@RequiredArgsConstructor
public class UpdateResolverImpl {

    private final List<CallBackResolver> callBackResolvers;
    private final List<MessageResolver> messageResolvers;
    private final ParserService factory;

    public void prepareMessage(InnerUpdate update) {

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

        messageResolvers.forEach(service -> service.prepareMessage(update, parser));

        callBackResolvers.forEach(service -> service.prepareMessage(update, parser));

    }
}
