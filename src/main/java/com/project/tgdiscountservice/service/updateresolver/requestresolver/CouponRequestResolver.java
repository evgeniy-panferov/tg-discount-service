package com.project.tgdiscountservice.service.updateresolver.requestresolver;

import com.project.tgdiscountservice.client.DiscountClientAdapterImpl;
import com.project.tgdiscountservice.model.Coupon;
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

@Slf4j
@Service
@RequiredArgsConstructor
public class CouponRequestResolver extends TelegramUpdateResolver {

    private final MessageSender messageSender;
    private final DiscountClientAdapterImpl discountClient;
    private final KeyboardPageGeneration<Coupon> pageGeneration;
    private static final String TYPE_RESOLVER = "/cp";

    @Override
    public void prepareMessage(InnerUpdate update) {
        if (!variableInit(update)[0].equals(TYPE_RESOLVER)) {
            return;
        }

        String partnerId;
        if (update.getMessage() != null) {
            partnerId = split[1];
        } else {
            partnerId = split[3];
        }

        List<Coupon> partnersByCategoryId = discountClient.getCouponsByPartnerId(Long.valueOf(partnerId));

        Pair<InlineKeyboardMarkup, List<Coupon>> keyboardAndCategories = pageGeneration.getPage(
                partnersByCategoryId, index, navigateCommand, TYPE_RESOLVER, 1, partnerId);
        InlineKeyboardMarkup navigateKeyboard = keyboardAndCategories.getFirst();
        List<Coupon> page = keyboardAndCategories.getSecond();

        StringBuilder message = new StringBuilder();
        for (int i = 0; i < page.size(); i++) {
            message.append(i + 1).append(" ")
                    .append(page.get(i).getName()).append("\n")
                    .append(page.get(i).getDescription()).append("\n");
        }

        sendMessage(update, message, navigateKeyboard, messageSender);
    }

}
