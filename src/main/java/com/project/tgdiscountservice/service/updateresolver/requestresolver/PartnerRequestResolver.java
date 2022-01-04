package com.project.tgdiscountservice.service.updateresolver.requestresolver;


import com.project.tgdiscountservice.client.DiscountClientAdapterImpl;
import com.project.tgdiscountservice.model.dto.PartnerDto;
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

@Service
@RequiredArgsConstructor
@Slf4j
public class PartnerRequestResolver extends TelegramUpdateResolver {

    private final MessageSender messageSender;
    private final DiscountClientAdapterImpl discountClient;
    private final KeyboardPageGeneration<PartnerDto> pageGeneration;
    private static final String TYPE_RESOLVER = "/cr";

    public void prepareMessage(InnerUpdate update) {
        if (!variableInit(update)[0].equals(TYPE_RESOLVER)) {
            return;
        }
        String categoryId = variableInit(update)[1];

        List<PartnerDto> partnersByCategoryId = discountClient.getPartnersByCategoryId(Long.valueOf(categoryId));

        Pair<InlineKeyboardMarkup, List<PartnerDto>> keyboardAndCategories = pageGeneration.getPage(partnersByCategoryId, index, navigateCommand, TYPE_RESOLVER, 5);
        InlineKeyboardMarkup navigateKeyboard = keyboardAndCategories.getFirst();
        List<PartnerDto> page = keyboardAndCategories.getSecond();

        StringBuilder message = new StringBuilder();
        for (int i = 0; i < page.size(); i++) {
            message.append(i + 1).append(" ")
                    .append(page.get(i).getName()).append("\n")
                    .append("/cp_").append(page.get(i).getId()).append("\n");
        }

        sendMessage(update, message, navigateKeyboard, messageSender);
    }

}
