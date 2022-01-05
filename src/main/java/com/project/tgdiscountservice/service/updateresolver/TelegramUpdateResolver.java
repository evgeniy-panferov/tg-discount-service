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
public abstract class TelegramUpdateResolver {

    protected InnerMessage tgMessage;
    protected String command = "";
    protected String navigateCommand = "";
    protected int index = 0;
    protected String callBackData = "";
    protected InnerCallBackQuery callbackQuery;
    protected Long chatId = 0L;
    protected String[] split;

    public abstract void prepareMessage(InnerUpdate update);

    public String[] variableInit(InnerUpdate update) {
        tgMessage = update.getMessage();

        if (tgMessage != null) {
            split = tgMessage.getText().split("_");
            command = split[0];
            navigateCommand = "";
            index = 0;
            chatId = tgMessage.getChat().getId();
        }

        if ((callbackQuery = update.getCallbackQuery()) != null) {
            callBackData = callbackQuery.getData();
            chatId = callbackQuery.getMessage().getChatId();
            split = callBackData.split("_");
            command = split[0];
            navigateCommand = split[1];
            index = Integer.parseInt(split[2]);
        }

        return split;
    }

    public void sendMessage(InnerUpdate update, StringBuilder message, InlineKeyboardMarkup navigateKeyboard, MessageSender messageSender) {

        InnerCallBackQuery callBackQuery = update.getCallbackQuery();
        if (tgMessage != null) {
            messageSender.sendMessage(tgMessage, message, navigateKeyboard);
        }

        if (callBackQuery != null) {
            messageSender.sendAnswerCallbackQuery(message.toString(), callBackQuery, navigateKeyboard);
        }
    }
}


