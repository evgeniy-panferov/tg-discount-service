package com.project.tgdiscountservice.service.parser;

import com.project.tgdiscountservice.model.inner.InnerUpdate;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Getter
public class CallBackParser extends Parser {

    @Override
    public CallBackParser parse(InnerUpdate update) {
        log.info("CallBackParser parse - {}", update);
        callbackQuery = update.getCallbackQuery();
        callBackData = callbackQuery.getData();
        chatId = callbackQuery.getMessage().getChatId();
        command = callBackData;
        if (callBackData.contains("_")) {
            split = callBackData.split("_");
            command = split[0];
            navigateCommand = split[1];
            index = Integer.parseInt(split[2]);
            if (update.getMessage() != null) {
                id = split[1];
            } else {
                id = split[3];
            }
        }
        return this;
    }
}
