package com.project.tgdiscountservice.service.sender;

import com.project.tgdiscountservice.http.TelegramSender;
import com.project.tgdiscountservice.model.inner.InnerCallBackQuery;
import com.project.tgdiscountservice.model.inner.InnerMessage;
import com.project.tgdiscountservice.service.SVGToJpegConverter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;


@Slf4j
@Service
@RequiredArgsConstructor
public class MessageSender {

    private final TelegramSender sender;
    private final SVGToJpegConverter converter;

    public void sendMessage(InnerMessage tgMessage, StringBuilder messageBuilder, InlineKeyboardMarkup navigateKeyboard) {
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

    public void sendMessage(InnerMessage tgMessage, StringBuilder messageBuilder) {
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

    @SneakyThrows
    public void sendPhotoMessage(InnerMessage tgMessage, StringBuilder messageBuilder, String imageUrl, InlineKeyboardMarkup keyboard) {
        try {
            SendPhoto message = new SendPhoto();
            message.setChatId(tgMessage.getChat().getId().toString());
            message.setCaption(messageBuilder.toString());

            if (imageUrl.contains(".svg")) {
                converter.transcodeSVGToBufferedImage(imageUrl);
                File pic = new File("src/main/resources/pictures/pic.png");
                message.setPhoto(new InputFile(pic));
            } else {
                message.setPhoto(new InputFile(imageUrl));
            }

            message.setReplyMarkup(keyboard);
            Message execute = sender.execute(message);
            log.info(execute.toString());
        } catch (TelegramApiException e) {
            e.printStackTrace();
            log.error("Message sending failed - {}", e.getMessage());
        }
    }

    public void sendAnswerCallbackQuery(String text, InnerCallBackQuery callBackQuery, InlineKeyboardMarkup navigateKeyboard) {
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

    public void sendAnswerCallbackQuery(String text, InnerCallBackQuery callBackQuery) {
        try {
            EditMessageText editMarkup = new EditMessageText();
            editMarkup.setChatId(callBackQuery.getMessage().getChatId().toString());
            editMarkup.setInlineMessageId(callBackQuery.getInlineMessageId());
            editMarkup.setText(text);
            editMarkup.enableHtml(true);
            editMarkup.setMessageId(callBackQuery.getMessage().getMessageId());
            sender.execute(editMarkup);
        } catch (TelegramApiException e) {
            e.printStackTrace();
            log.error("Message sending failed - {}", e.getMessage());
        }
    }
}
