package com.project.tgdiscountservice.service.updateresolver;

import com.project.tgdiscountservice.cache.CouponCacheImpl;
import com.project.tgdiscountservice.client.DiscountClientAdapterImpl;
import com.project.tgdiscountservice.model.Coupon;
import com.project.tgdiscountservice.model.inner.InnerCallBackQuery;
import com.project.tgdiscountservice.model.inner.InnerMessage;
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
public class CouponUpdateResolver implements TelegramUpdateResolver {

    private final MessageSender messageSender;
    private final DiscountClientAdapterImpl discountClientAdapter;
    private final CouponCacheImpl couponCache;
    private final KeyboardPageGeneration<Coupon> pageGeneration;
    private final static String TYPE_RESOLVER = "/coupons";
    private final static String URL = "http://localhost:8080";
    private Long categoryId;

    public void prepareMessage(InnerUpdate update, Long categoryId) {
        prepareMessage(update);
        this.categoryId = categoryId;
    }

    @Override
    public void prepareMessage(InnerUpdate update) {
        InnerMessage tgMessage = update.getMessage();
        String command = tgMessage != null ? tgMessage.getText() : "";
        String navigateCommand = "";
        int index = 0;
        String callBackData = "";
        InnerCallBackQuery callbackQuery;
        Long chatId = tgMessage != null ? tgMessage.getChat().getId() : 0L;
        if ((callbackQuery = update.getCallbackQuery()) != null) {
            callBackData = callbackQuery.getData();
            chatId = callbackQuery.getMessage().getChatId();
            String[] split = callBackData.split(":");
            command = split[0];
            navigateCommand = split[1];
            index = Integer.parseInt(split[2]);
        }

        if (command.equals(TYPE_RESOLVER)) {

            //TODO throw out client from here and make scheduler
            List<Coupon> coupons = discountClientAdapter.getCouponsByPartnerId(categoryId);


            Pair<InlineKeyboardMarkup, List<Coupon>> keyboardAndCategories = pageGeneration.getPage(coupons, index, navigateCommand, TYPE_RESOLVER);
            InlineKeyboardMarkup navigateKeyboard = keyboardAndCategories.getFirst();
            List<Coupon> page = keyboardAndCategories.getSecond();

            StringBuilder messageBuilder = new StringBuilder();
            for (int i = 0; i < page.size(); i++) {
                messageBuilder.append(i + 1).append(" ")
                        .append(page.get(i).getName())
                        .append("\n")
                        .append("<a href=\"")
                        .append(URL).append("/").append(chatId)
                        .append("\"><b>Прислать</b></a>")
                        .append("\n");
            }

            if (tgMessage != null) {
                messageSender.sendMessage(tgMessage, messageBuilder, navigateKeyboard);
            }

            InnerCallBackQuery callBackQuery = update.getCallbackQuery();
            if (callBackQuery != null) {
                messageSender.sendAnswerCallbackQuery(messageBuilder.toString(), callBackQuery, navigateKeyboard);
            }

        }


    }
}
