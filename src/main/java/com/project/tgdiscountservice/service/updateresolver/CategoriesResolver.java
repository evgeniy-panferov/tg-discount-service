package com.project.tgdiscountservice.service.updateresolver;

import com.project.tgdiscountservice.cache.CategoryCacheImpl;
import com.project.tgdiscountservice.client.DiscountClientAdapterImpl;
import com.project.tgdiscountservice.model.Category;
import com.project.tgdiscountservice.model.inner.InnerUpdate;
import com.project.tgdiscountservice.service.KeyboardPageGeneration;
import com.project.tgdiscountservice.service.sender.MessageSender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.glassfish.grizzly.utils.Pair;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class CategoriesResolver extends TelegramUpdateResolver {

    private final MessageSender messageSender;
    private final DiscountClientAdapterImpl discountClientAdapter;
    private final CategoryCacheImpl categoryCache;
    private final KeyboardPageGeneration<Category> pageGeneration;
    private static final String TYPE_RESOLVER = "/categories";

    @Override
    public void prepareMessage(InnerUpdate update) {

        if (!variableInit(update)[0].equals(TYPE_RESOLVER)) {
            return;
        }
        //TODO throw out client from here and make scheduler
        discountClientAdapter.getCategories();

        List<Category> categories = categoryCache.findAll();

        Pair<InlineKeyboardMarkup, List<Category>> keyboardAndCategories = pageGeneration.getPage(categories, index, navigateCommand, TYPE_RESOLVER, 10);
        InlineKeyboardMarkup navigateKeyboard = keyboardAndCategories.getFirst();
        List<Category> page = keyboardAndCategories.getSecond();

        StringBuilder message = new StringBuilder();
        for (int i = 0; i < page.size(); i++) {
            message.append(i + 1).append(" ")
                    .append(page.get(i).getName())
                    .append("\n")
                    .append("/cr_").append(page.get(i).getAdmitadId())
                    .append("\n");
        }
        sendMessage(update, message, navigateKeyboard, messageSender);
    }

}

