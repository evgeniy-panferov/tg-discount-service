package com.project.tgdiscountservice.service.updateresolver;

import com.project.tgdiscountservice.cache.CategoryCacheImpl;
import com.project.tgdiscountservice.client.DiscountClientAdapterImpl;
import com.project.tgdiscountservice.http.TelegramSender;
import com.project.tgdiscountservice.model.Category;
import com.project.tgdiscountservice.model.inner.InnerCallBackQuery;
import com.project.tgdiscountservice.model.inner.InnerMessage;
import com.project.tgdiscountservice.model.inner.InnerUpdate;
import com.project.tgdiscountservice.service.KeyboardPageGeneration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.glassfish.grizzly.utils.Pair;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoriesResolver implements TelegramUpdateResolver {

    private final TelegramSender sender;
    private final DiscountClientAdapterImpl discountClientAdapter;
    private final CategoryCacheImpl categoryCache;
    private final KeyboardPageGeneration<Category> pageGeneration;
    private final static String TYPE_RESOLVER = "/categories";

    @Override
    public void prepareMessage(InnerUpdate update) {
        InnerMessage tgMessage = update.getMessage();
        String command = tgMessage != null ? tgMessage.getText() : "";
        String navigateCommand = "";
        int index = 0;
        String callBackData = "";
        InnerCallBackQuery callbackQuery;
        if ((callbackQuery = update.getCallbackQuery()) != null) {
            callBackData = callbackQuery.getData();
            String[] split = callBackData.split(":");
            command = split[0];
            navigateCommand = split[1];
            index = Integer.parseInt(split[2]);
        }

        if (command.equals(TYPE_RESOLVER)) {

            discountClientAdapter.getCategories();

            List<Category> categories = categoryCache.findAll();

            Pair<InlineKeyboardMarkup, List<Category>> keyboardAndCategories = pageGeneration.getPage(categories, index, navigateCommand, TYPE_RESOLVER);
            InlineKeyboardMarkup navigateKeyboard = keyboardAndCategories.getFirst();
            List<Category> page = keyboardAndCategories.getSecond();

            StringBuilder messageBuilder = new StringBuilder();
            for (int i = 0; i < page.size(); i++) {
                messageBuilder.append(i + 1).append(" ")
                        .append(page.get(i).getName())
                        .append("\n");
            }

            if (tgMessage != null) {
                sendMessage(tgMessage, messageBuilder, navigateKeyboard);
            }

            InnerCallBackQuery callBackQuery = update.getCallbackQuery();
            if (callBackQuery != null) {
                sendAnswerCallbackQuery(messageBuilder.toString(), callBackQuery, navigateKeyboard);
            }

        }
    }

    private void sendMessage(InnerMessage tgMessage, StringBuilder messageBuilder, InlineKeyboardMarkup navigateKeyboard) {
        try {
            SendMessage message = new SendMessage();
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

    private void sendAnswerCallbackQuery(String text, InnerCallBackQuery callBackQuery, InlineKeyboardMarkup navigateKeyboard) {
        try {
            EditMessageText editMarkup = new EditMessageText();
            editMarkup.setChatId(callBackQuery.getMessage().getChatId().toString());
            editMarkup.setInlineMessageId(callBackQuery.getInlineMessageId());
            editMarkup.setText(text);
            editMarkup.enableMarkdown(true);
            editMarkup.setMessageId(callBackQuery.getMessage().getMessageId());
            editMarkup.setReplyMarkup(navigateKeyboard);
            sender.execute(editMarkup);
        } catch (TelegramApiException e) {
            e.printStackTrace();
            log.error("Message sending failed - {}", e.getMessage());
        }
    }
}
