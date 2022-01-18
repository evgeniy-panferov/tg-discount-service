package com.project.tgdiscountservice.service.updateresolver;

import com.project.tgdiscountservice.model.inner.InnerCallBackQuery;
import com.project.tgdiscountservice.model.inner.InnerMessage;
import com.project.tgdiscountservice.model.inner.InnerUpdate;
import com.project.tgdiscountservice.service.sender.MessageSender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

@Service
@RequiredArgsConstructor
public class MessageSenderTypeUpdateChecker {

    public void sendMessage(InnerUpdate update, StringBuilder message, InlineKeyboardMarkup navigateKeyboard, MessageSender messageSender) {

        InnerCallBackQuery callBackQuery = update.getCallbackQuery();
        InnerMessage tgMessage = update.getMessage();
        if (tgMessage != null) {
            messageSender.sendMessage(tgMessage, message, navigateKeyboard);
        }

        if (callBackQuery != null) {
            messageSender.sendAnswerCallbackQuery(message.toString(), callBackQuery, navigateKeyboard);
        }
    }

    public void sendPhotoMessage(InnerUpdate update, StringBuilder message, MessageSender messageSender, String imageUrl, InlineKeyboardMarkup keyboard) {

        InnerCallBackQuery callBackQuery = update.getCallbackQuery();
        InnerMessage tgMessage = update.getMessage();
        if (tgMessage != null) {
            messageSender.sendPhotoMessage(tgMessage, message, imageUrl, keyboard);
        }

        if (callBackQuery != null) {
            messageSender.sendAnswerCallbackQuery(message.toString(), callBackQuery);
        }
    }
}


