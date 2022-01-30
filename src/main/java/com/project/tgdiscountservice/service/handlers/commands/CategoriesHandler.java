package com.project.tgdiscountservice.service.handlers.commands;

import com.project.tgdiscountservice.cache.PartnerCacheImpl;
import com.project.tgdiscountservice.model.Category;
import com.project.tgdiscountservice.model.TgPage;
import com.project.tgdiscountservice.model.inner.InnerUpdate;
import com.project.tgdiscountservice.service.KeyboardPageGeneration;
import com.project.tgdiscountservice.service.parser.Parser;
import com.project.tgdiscountservice.service.handlers.MessageSenderFacade;
import com.project.tgdiscountservice.util.CategoryUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.List;
import java.util.stream.Collectors;

import static com.project.tgdiscountservice.model.Emoji.RED_EXCLAMATION_MARK;
import static com.project.tgdiscountservice.model.Emoji.STAR;


@Slf4j
@Service
@RequiredArgsConstructor
public class CategoriesHandler implements MessageHandler {

    private final PartnerCacheImpl partnerCache;
    private final MessageSenderFacade messageSenderFacade;
    private final KeyboardPageGeneration<Category> pageGeneration;
    private static final String TYPE_RESOLVER = "/categories";

    @Override
    public void prepareMessage(InnerUpdate update, Parser parserData) {
        log.info("CategoriesHandler prepareMessage - {}, {}", update, parserData);
        String command = parserData.getCommand();

        if (!command.equals(TYPE_RESOLVER)) {
            return;
        }

        List<Category> categories = partnerCache.findAll()
                .stream()
                .filter(partner -> !partner.getCoupons().isEmpty())
                .flatMap(partner -> partner.getCategories().stream())
                .distinct()
                .collect(Collectors.toList());

        categories.add(0, CategoryUtil.create("<b>Первая страничка.</b>\n"));
        categories.add(CategoryUtil.create("<b>Последняя страничка.</b>\n"));

        int index = parserData.getIndex();
        String navigateCommand = parserData.getNavigateCommand();
        TgPage<Category> page = pageGeneration.getPage(
                categories, index, navigateCommand, TYPE_RESOLVER, 10, "0");

        InlineKeyboardMarkup navigateKeyboard = page.getInlineKeyboardMarkup();
        List<Category> categoryList = page.getPage();

        StringBuilder message = new StringBuilder();
        for (int i = 0; i < categoryList.size(); i++) {
            Category pageCategory = categoryList.get(i);

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
        messageSenderFacade.sendMessage(update, message, navigateKeyboard);
    }

}

