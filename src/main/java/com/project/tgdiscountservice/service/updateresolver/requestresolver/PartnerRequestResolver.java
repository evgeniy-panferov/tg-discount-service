package com.project.tgdiscountservice.service.updateresolver.requestresolver;


import com.project.tgdiscountservice.cache.PartnerCacheImpl;
import com.project.tgdiscountservice.model.Category;
import com.project.tgdiscountservice.model.Partner;
import com.project.tgdiscountservice.model.inner.InnerUpdate;
import com.project.tgdiscountservice.service.sender.MessageSender;
import com.project.tgdiscountservice.service.updateresolver.TelegramUpdateResolver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.project.tgdiscountservice.util.InlineKeyboard.getNavigateCallbackKeyboard;

@Service
@RequiredArgsConstructor
@Slf4j
public class PartnerRequestResolver extends TelegramUpdateResolver {

    private final MessageSender messageSender;
    private final PartnerCacheImpl partnerCache;
    private static final String TYPE_RESOLVER = "/cr";

    @Value("${app.host}")
    private String host;

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

        List<Partner> partners = partnerCache.findAll()
                .stream()
                .filter(partner -> {
                    boolean condition = false;
                    Set<Category> categories = partner.getCategories();
                    for (Category category : categories) {
                        if (!partner.getCoupons().isEmpty() && String.valueOf(category.getAdmitadId()).equals(categoryId)) {
                            condition = true;
                        }
                    }
                    return condition;
                }).collect(Collectors.toList());

        for (int i = 0; i < partners.size(); i++) {
            StringBuilder message = new StringBuilder();
            Partner partner = partners.get(i);
            String imageUrl = partner.getImageUrl();
            if (imageUrl.contains(".svg")) {
                message.append("<a href=\"").append(host).append("/pictures?imageUrl=").append(imageUrl).append("\">").append(" ").append("</a>");
            } else {
                message.append("<a href=\"").append(imageUrl).append("\">").append(" ").append("</a>");
            }
            message.append("<b><u>").append(partner.getName()).append("</u></b>").append("\n");
            InlineKeyboardMarkup navigateKeyboard = getNavigateCallbackKeyboard("/cp", "0", partner.getId().toString(),
                    "", "Список акций");
            sendMessage(update, message, navigateKeyboard, messageSender);
        }
    }

}
