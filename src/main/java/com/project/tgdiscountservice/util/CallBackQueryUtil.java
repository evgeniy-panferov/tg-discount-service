package com.project.tgdiscountservice.util;

import com.project.tgdiscountservice.model.inner.InnerCallBackQuery;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CallBackQueryUtil {

    public static InnerCallBackQuery fromDto(CallbackQuery callbackQuery) {
        var innerCallbackQuery = new InnerCallBackQuery();
        if (callbackQuery != null) {
            innerCallbackQuery.setId(callbackQuery.getId());
            innerCallbackQuery.setMessage(callbackQuery.getMessage());
            innerCallbackQuery.setInlineMessageId(callbackQuery.getInlineMessageId());
            innerCallbackQuery.setData(callbackQuery.getData());
            innerCallbackQuery.setGameShortName(callbackQuery.getGameShortName());
            innerCallbackQuery.setChatInstance(callbackQuery.getChatInstance());
        }
        return innerCallbackQuery;
    }
}
