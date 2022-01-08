package com.project.tgdiscountservice.service.updateresolver;

import com.project.tgdiscountservice.cache.PartnerCacheImpl;
import com.project.tgdiscountservice.model.Category;
import com.project.tgdiscountservice.model.inner.InnerUpdate;
import com.project.tgdiscountservice.service.KeyboardPageGeneration;
import com.project.tgdiscountservice.service.sender.MessageSender;
import com.project.tgdiscountservice.util.CategoryUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.glassfish.grizzly.utils.Pair;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.List;
import java.util.stream.Collectors;

import static com.project.tgdiscountservice.model.Emoji.*;


@Slf4j
@Service
@RequiredArgsConstructor
public class CategoriesResolver extends TelegramUpdateResolver {

    private final MessageSender messageSender;

    private final PartnerCacheImpl partnerCache;
    private final KeyboardPageGeneration<Category> pageGeneration;
    private static final String TYPE_RESOLVER = "/categories";

    @Override
    public void prepareMessage(InnerUpdate update) {

        if (!variableInit(update)[0].equals(TYPE_RESOLVER)) {
            return;
        }

        List<Category> categories = partnerCache.findAll()
                .stream()
                .filter(partner -> !partner.getCoupons().isEmpty())
                .flatMap(partner -> partner.getCategories().stream())
                .collect(Collectors.toList());

        categories.add(0, CategoryUtil.create("<b>Первая страничка.</b>\n"));
        categories.add(CategoryUtil.create("<b>Последняя страничка.</b>\n"));

        Pair<InlineKeyboardMarkup, List<Category>> keyboardAndCategories = pageGeneration.getPage(
                categories, index, navigateCommand, TYPE_RESOLVER, 10, "0");

        InlineKeyboardMarkup navigateKeyboard = keyboardAndCategories.getFirst();
        List<Category> page = keyboardAndCategories.getSecond();

        StringBuilder message = new StringBuilder();
        for (int i = 0; i < page.size(); i++) {
            Category pageCategory = page.get(i);

            if (pageCategory.getId().equals(-1L)) {
                message.append("\n")
                        .append(RED_EXCLAMATION_MARK)
                        .append(pageCategory.getName())
                        .append("\n");
            } else {
                message.append(STAR).append(" ")
                        .append(pageCategory.getName())
                        .append(" ")
                        .append("/cr_").append(pageCategory.getAdmitadId())
                        .append("\n");
            }
        }
        categories.remove(0);
        categories.remove(categories.size() - 1);
        sendMessage(update, message, navigateKeyboard, messageSender);
    }

}

