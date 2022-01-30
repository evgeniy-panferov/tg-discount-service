package com.project.tgdiscountservice.service.handlers;

import com.project.tgdiscountservice.model.inner.InnerCallBackQuery;
import com.project.tgdiscountservice.model.inner.InnerMessage;
import com.project.tgdiscountservice.model.inner.InnerUpdate;
import com.project.tgdiscountservice.service.sender.MessageSender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.inlinequery.InlineQuery;
import org.telegram.telegrambots.meta.api.objects.inlinequery.result.InlineQueryResult;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageSenderFacade {

    private final MessageSender messageSender;

    public void sendMessage(InnerUpdate update, StringBuilder message, InlineKeyboardMarkup navigateKeyboard) {

        InnerCallBackQuery callBackQuery = update.getCallbackQuery();
        InnerMessage tgMessage = update.getMessage();
        if (tgMessage != null) {
            messageSender.sendMessage(tgMessage, message, navigateKeyboard);
        }

        if (callBackQuery != null) {
            messageSender.sendAnswerCallbackQuery(message.toString(), callBackQuery, navigateKeyboard);
        }
    }

    public void sendMessage(InnerMessage tgMessage, StringBuilder messageBuilder) {
        messageSender.sendMessage(tgMessage, messageBuilder);
    }

    public void sendInlineQuery(List<InlineQueryResult> resultList, InlineQuery inlineQuery){
        messageSender.sendInlineQuery(resultList, inlineQuery);
    }

}


