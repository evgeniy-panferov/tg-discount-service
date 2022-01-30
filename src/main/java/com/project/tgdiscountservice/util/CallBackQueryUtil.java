package com.project.tgdiscountservice.util;

import com.project.tgdiscountservice.model.inner.InnerCallBackQuery;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CallBackQueryUtil {

    public static InnerCallBackQuery fromDto(CallbackQuery callbackQuery) {
        log.info("CallBackQueryUtil fromDto - {}", callbackQuery);
        if (callbackQuery == null) {
            return null;
        }
        var innerCallbackQuery = new InnerCallBackQuery();
        innerCallbackQuery.setId(callbackQuery.getId());
        innerCallbackQuery.setMessage(callbackQuery.getMessage());
        innerCallbackQuery.setInlineMessageId(callbackQuery.getInlineMessageId());
        innerCallbackQuery.setData(callbackQuery.getData());
        innerCallbackQuery.setGameShortName(callbackQuery.getGameShortName());
        innerCallbackQuery.setChatInstance(callbackQuery.getChatInstance());
        return innerCallbackQuery;
    }
}
