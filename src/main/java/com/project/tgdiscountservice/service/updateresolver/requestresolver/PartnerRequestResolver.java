package com.project.tgdiscountservice.service.updateresolver.requestresolver;


import com.project.tgdiscountservice.client.DiscountClientAdapterImpl;
import com.project.tgdiscountservice.model.Partner;
import com.project.tgdiscountservice.model.inner.InnerUpdate;
import com.project.tgdiscountservice.service.KeyboardPageGeneration;
import com.project.tgdiscountservice.service.sender.MessageSender;
import com.project.tgdiscountservice.service.updateresolver.TelegramUpdateResolver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.glassfish.grizzly.utils.Pair;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PartnerRequestResolver extends TelegramUpdateResolver {

    private final MessageSender messageSender;
    private final DiscountClientAdapterImpl discountClient;
    private final KeyboardPageGeneration<Partner> pageGeneration;
    private static final String TYPE_RESOLVER = "/cr";

    public void prepareMessage(InnerUpdate update) {
        String[] split = variableInit(update);

        if (!split[0].equals(TYPE_RESOLVER)) {
            return;
        }

        String categoryId;
        if (update.getMessage() != null) {
            categoryId = split[1];
        } else {
            categoryId = split[3];
        }

        List<Partner> partnersByCategoryId = discountClient.getPartnersById(Long.valueOf(categoryId))
                .stream()
                .filter(partner -> !partner.getCoupons().isEmpty())
                .collect(Collectors.toList());

        Pair<InlineKeyboardMarkup, List<Partner>> keyboardAndCategories = pageGeneration.getPage(
                partnersByCategoryId, index, navigateCommand, TYPE_RESOLVER, 5, categoryId);
        InlineKeyboardMarkup navigateKeyboard = keyboardAndCategories.getFirst();
        List<Partner> page = keyboardAndCategories.getSecond();

        StringBuilder message = new StringBuilder();
        for (int i = 0; i < page.size(); i++) {
            message.append(i + 1).append(" ")
                    .append(page.get(i).getName()).append("\n")
                    .append("/cp_").append(page.get(i).getId()).append("\n");
        }

        sendMessage(update, message, navigateKeyboard, messageSender);
    }

}
