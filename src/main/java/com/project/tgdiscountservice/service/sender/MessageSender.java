package com.project.tgdiscountservice.service.sender;

import com.project.tgdiscountservice.http.TelegramSender;
import com.project.tgdiscountservice.model.inner.InnerCallBackQuery;
import com.project.tgdiscountservice.model.inner.InnerMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.AnswerInlineQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.inlinequery.InlineQuery;
import org.telegram.telegrambots.meta.api.objects.inlinequery.result.InlineQueryResult;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class MessageSender {

    private final TelegramSender sender;

    public void sendMessage(InnerMessage tgMessage, StringBuilder messageBuilder, InlineKeyboardMarkup navigateKeyboard) {
        log.info("MessageSender sendMessage - {}, {}, {}", tgMessage, messageBuilder, navigateKeyboard);
        try {
            SendMessage message = new SendMessage();
            message.enableHtml(true);
            message.setChatId(tgMessage.getChat().getId().toString());
            message.setText(messageBuilder.toString());
            message.setReplyMarkup(navigateKeyboard);

            Message execute = sender.execute(message);
            log.info(execute.toString());
        } catch (TelegramApiException e) {
            e.printStackTrace();
            log.error("Message sending failed - {}", e.getMessage());
        }

    }

    public void sendAnswerCallbackQuery(String text, InnerCallBackQuery callBackQuery, InlineKeyboardMarkup navigateKeyboard) {
        log.info("MessageSender sendAnswerCallbackQuery - {}, {}, {}", text, callBackQuery, navigateKeyboard);
        try {
            EditMessageText editMarkup = new EditMessageText();
            editMarkup.setChatId(callBackQuery.getMessage().getChatId().toString());
            editMarkup.setInlineMessageId(callBackQuery.getInlineMessageId());
            editMarkup.setText(text);
            editMarkup.enableHtml(true);
            editMarkup.setMessageId(callBackQuery.getMessage().getMessageId());
            editMarkup.setReplyMarkup(navigateKeyboard);
            sender.execute(editMarkup);
        } catch (TelegramApiException e) {
            e.printStackTrace();
            log.error("Message sending failed - {}", e.getMessage());
        }
    }

    public void sendInlineQuery(List<InlineQueryResult> inlineQueryResults, InlineQuery inlineQuery) {
        log.info("MessageSender sendInlineQuery - {}, {}", inlineQueryResults, inlineQuery);
        try {
            AnswerInlineQuery answerInlineQuery = new AnswerInlineQuery();
            answerInlineQuery.setInlineQueryId(inlineQuery.getId());
            answerInlineQuery.setCacheTime(15);
            answerInlineQuery.setResults(inlineQueryResults);
            sender.execute(answerInlineQuery);
        } catch (TelegramApiException e) {
            e.printStackTrace();
            log.error("Message sending failed - {}", e.getMessage());
        }
    }

    public void sendMessage(InnerMessage tgMessage, StringBuilder messageBuilder) {
        log.info("MessageSender sendMessage - {}, {}", tgMessage, messageBuilder);
        try {
            SendMessage message = new SendMessage();
            message.setChatId(tgMessage.getChat().getId().toString());
            message.setText(messageBuilder.toString());

            Message execute = sender.execute(message);
            log.info(execute.toString());
        } catch (TelegramApiException e) {
            e.printStackTrace();
            log.error("Message sending failed - {}", e.getMessage());
        }
    }
}
