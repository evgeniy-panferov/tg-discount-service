package com.project.tgdiscountservice.service.updateresolver;

import com.project.tgdiscountservice.cache.CategoryCacheImpl;
import com.project.tgdiscountservice.client.DiscountClientAdapterImpl;
import com.project.tgdiscountservice.http.TelegramSender;
import com.project.tgdiscountservice.model.Category;
import com.project.tgdiscountservice.model.inner.InnerCallBackQuery;
import com.project.tgdiscountservice.model.inner.InnerMessage;
import com.project.tgdiscountservice.model.inner.InnerUpdate;
import com.project.tgdiscountservice.service.MessagePageGeneration;
import com.project.tgdiscountservice.util.InlineKeyboard;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoriesResolver implements TelegramUpdateResolver {

    private final TelegramSender sender;
    private final DiscountClientAdapterImpl discountClientAdapter;
    private final CategoryCacheImpl categoryCache;
    private final MessagePageGeneration<Category> pageGeneration;
    private final static String TYPE_RESOLVER = "/categories";

    @Override
    public void prepareMessage(InnerUpdate update) {
        InnerMessage tgMessage = update.getMessage();
        String command = tgMessage != null ? tgMessage.getText() : "";
        String navigateCommand = "";
        String index = "0";
        String callBackData = "";
        if ((callBackData = update.getCallbackQuery().getData()) != null) {
            String[] split = callBackData.split(":");
            command = split[0];
            navigateCommand = split[1];
            index = split[2];
        }

        if (command.equals(TYPE_RESOLVER)) {

            discountClientAdapter.getCategories();

            List<Category> categories = categoryCache.findAll();

            Map<String, Category> categoryByAdmitadId = pageGeneration.getPage(categories, Integer.parseInt(index), navigateCommand)
                    .stream()
                    .collect(Collectors.toMap(category -> String.valueOf(category.getAdmitadId()), Function.identity()));

            Integer currentIndex = pageGeneration.getIndex(Integer.parseInt(index));

            InlineKeyboardMarkup navigateKeyboard = InlineKeyboard.getNavigateKeyboard(TYPE_RESOLVER, currentIndex.toString());

            StringBuilder messageBuilder = new StringBuilder();
            categoryByAdmitadId.forEach((key, value) ->
                    messageBuilder.append(key).append(" ")
                            .append(value.getName())
                            .append("\n"));

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
            log.error("Отправка сообщения не удалась");
        }
    }
}
