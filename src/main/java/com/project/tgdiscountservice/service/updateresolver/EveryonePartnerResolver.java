package com.project.tgdiscountservice.service.updateresolver;

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
    private final DiscountClientAdapterImpl discountClient;
    @Value("${image.url}")
    private String image;


    @Override
    public void prepareMessage(InnerUpdate update) {

        tgMessage = update.getMessage();
        String command = tgMessage != null ? tgMessage.getText() : "";
        if (command.equals(TYPE_RESOLVER)) {

            List<Partner> partners = discountClient.getPartners();
            for (int i = 0; i < partners.size(); i++) {
                StringBuilder message = new StringBuilder();
                Partner partner = partners.get(i);
                String imageUrl = partner.getImageUrl();
                if (imageUrl.contains(".svg")) {
                    imageUrl = image;
                }

                message.append("<a href=\"").append(imageUrl).append("\">").append(" ").append("</a>")
                        .append("<b><u>").append(partner.getName()).append("</b></u>").append("\n");
                InlineKeyboardMarkup navigateKeyboard = getNavigateCallbackKeyboard("/cp", "0", partner.getId().toString(),
                        "", "Список акций");
                //     sendPhotoMessage(update,message,sender, imageUrl, navigateKeyboard);
                sendMessage(update, message, navigateKeyboard, sender);
            }
        }
    }
}