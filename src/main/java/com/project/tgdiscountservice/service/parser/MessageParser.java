package com.project.tgdiscountservice.service.parser;

import com.project.tgdiscountservice.model.inner.InnerUpdate;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Getter
public class MessageParser extends Parser {

    @Override
    public MessageParser parse(InnerUpdate update) {
        log.info("MessageParser parse - {}", update);
        tgMessage = update.getMessage();
        String message = tgMessage.getText();
        chatId = tgMessage.getChat().getId();
        command = message;
        if (message.contains("_")) {
            split = message.split("_");
            id = split[1];
            command = split[0];
        }
        return this;
    }
}
