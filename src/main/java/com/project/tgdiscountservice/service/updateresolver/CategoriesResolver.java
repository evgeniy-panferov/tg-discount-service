package com.project.tgdiscountservice.service.updateresolver;

import com.project.tgdiscountservice.cache.PartnerCacheImpl;
import com.project.tgdiscountservice.client.DiscountClientAdapterImpl;
import com.project.tgdiscountservice.model.Category;
import com.project.tgdiscountservice.model.Partner;
import com.project.tgdiscountservice.model.inner.InnerUpdate;
import com.project.tgdiscountservice.service.KeyboardPageGeneration;
import com.project.tgdiscountservice.service.sender.MessageSender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.glassfish.grizzly.utils.Pair;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Slf4j
@Service
@RequiredArgsConstructor
public class CategoriesResolver extends TelegramUpdateResolver {

    private final MessageSender messageSender;
    private final DiscountClientAdapterImpl discountClientAdapter;
    private final PartnerCacheImpl partnerCache;
    private final KeyboardPageGeneration<Category> pageGeneration;
    private static final String TYPE_RESOLVER = "/categories";

    @Override
    public void prepareMessage(InnerUpdate update) {

        if (!variableInit(update)[0].equals(TYPE_RESOLVER)) {
            return;
        }

        List<Partner> partners = discountClientAdapter.getPartners();

        Set<Category> categories = partnerCache.findAll()
                .stream()
                .filter(partner -> !partner.getCoupons().isEmpty())
                .flatMap(partner -> partner.getCategories().stream())
                .collect(Collectors.toSet());

        Pair<InlineKeyboardMarkup, List<Category>> keyboardAndCategories = pageGeneration.getPage(
                new ArrayList<>(categories), index, navigateCommand, TYPE_RESOLVER, 10, "0");

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

