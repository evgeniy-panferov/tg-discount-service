package com.project.tgdiscountservice.service.updateresolver;

import com.project.tgdiscountservice.cache.PartnerCacheImpl;
import com.project.tgdiscountservice.client.DiscountClientAdapterImpl;
import com.project.tgdiscountservice.model.Partner;
import com.project.tgdiscountservice.model.inner.InnerUpdate;
import com.project.tgdiscountservice.service.sender.MessageSender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.List;

import static com.project.tgdiscountservice.util.InlineKeyboard.getNavigateCallbackKeyboard;

@Slf4j
@Service
@RequiredArgsConstructor
public class EveryonePartnerResolver extends TelegramUpdateResolver {

    private static final String TYPE_RESOLVER = "/find_shops";
    private final MessageSender sender;
    private final PartnerCacheImpl partnerCache;
    @Value("${app.host}")
    private String host;


    @Override
    public void prepareMessage(InnerUpdate update) {

        tgMessage = update.getMessage();
        String command = tgMessage != null ? tgMessage.getText() : "";
        if (command.equals(TYPE_RESOLVER)) {

            List<Partner> partners = partnerCache.findAll();
            for (int i = 0; i < partners.size(); i++) {
                StringBuilder message = new StringBuilder();
                Partner partner = partners.get(i);
                String imageUrl = partner.getImageUrl();
                if (imageUrl.contains(".svg")) {
                    message.append("<a href=\"").append(host).append("/pictures?imageUrl=").append(partner.getImageUrl()).append("\">").append(" ").append("</a>");
                } else {
                    message.append("<a href=\"").append(partner.getImageUrl()).append("\">").append(" ").append("</a>");
                }


                message.append(partner.getName()).append("\n");
                InlineKeyboardMarkup navigateKeyboard = getNavigateCallbackKeyboard("/cp", "0", partner.getId().toString(),
                        "", "Список акций");
                sendMessage(update, message, navigateKeyboard, sender);
            }
        }
    }
}