package com.project.tgdiscountservice.service.updateresolver.requestresolver;

import com.project.tgdiscountservice.cache.PartnerCacheImpl;
import com.project.tgdiscountservice.model.Coupon;
import com.project.tgdiscountservice.model.inner.InnerUpdate;
import com.project.tgdiscountservice.service.KeyboardPageGeneration;
import com.project.tgdiscountservice.service.parser.Parser;
import com.project.tgdiscountservice.service.sender.MessageSender;
import com.project.tgdiscountservice.service.updateresolver.MessageSenderTypeUpdateChecker;
import com.project.tgdiscountservice.util.CouponUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.glassfish.grizzly.utils.Pair;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.project.tgdiscountservice.model.Emoji.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class CouponRequestResolver implements CallBackResolver {

    private final MessageSender messageSender;
    private final PartnerCacheImpl partnerCache;
    private final KeyboardPageGeneration<Coupon> pageGeneration;
    private final MessageSenderTypeUpdateChecker messageSenderTypeUpdateChecker;
    private static final String TYPE_RESOLVER = "/cp";

    @Value("${app.host}")
    private String host;

    @Override
    public void prepareMessage(InnerUpdate update, Parser parser) {
        String command = parser.getCommand();

        if (!command.equals(TYPE_RESOLVER)) {
            return;
        }

        String partnerId = parser.getId();

        List<Coupon> coupons = new ArrayList<>(partnerCache.findCoupons(partnerId));

        Coupon coupon = coupons.get(0);

        Coupon first = CouponUtil.create("<b>❗Первая страничка!</b>\n\nЛистайте стрелочками ⬅ ➡, находятся под сообщением снизу.", coupon);
        Coupon end = CouponUtil.create("<b>❗Последняя страничка!</b>\n\nЛистайте стрелочками ⬅ ➡, находятся под сообщением снизу.", coupon);

        coupons.add(0, first);
        coupons.add(end);

        int index = parser.getIndex();
        String navigateCommand = parser.getNavigateCommand();

        Pair<InlineKeyboardMarkup, List<Coupon>> keyboardAndCategories = pageGeneration.getPage(
                coupons, index, navigateCommand, TYPE_RESOLVER, 1, partnerId);
        InlineKeyboardMarkup navigateKeyboard = keyboardAndCategories.getFirst();
        List<Coupon> page = keyboardAndCategories.getSecond();


        for (int i = 0; i < page.size(); i++) {
            StringBuilder message = new StringBuilder();
            Coupon couponPage = page.get(i);
            String imageUrl = couponPage.getImageUrl();

            message.append("<a href=\"");
            if (imageUrl.contains(".svg")) {
                message.append(host).append("/pictures?imageUrl=").append(imageUrl).append("\">").append(" ").append("</a>");
            } else {
                message.append(imageUrl).append("\">").append(" ").append("</a>");
            }

            if (couponPage.getId().equals(-1L)) {
                message.append("<b><u>").append(couponPage.getPartner().getName()).append("</u></b>").append("\n")
                        .append("\n").append(couponPage.getName()).append("\n");
            } else {

                message.append("<b><u>").append(couponPage.getPartner().getName()).append("</u></b>").append("\n")
                        .append("\n")
                        .append(LIGHT_BULB).append("Название купона: ")
                        .append(couponPage.getName()).append("\n").append("\n")

                        .append(SCROLL).append("Описание спецпредложения: ")
                        .append(couponPage.getDescription()).append("\n").append("\n");

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

                String date = couponPage.getDateEnd() == null ? "Дата окончания отсутствует" : couponPage.getDateEnd().format(formatter);

                message.append(TIMER_CLOCK).append("Дата окончания акции: ").append("<b>").append("\n")
                        .append(date).append("</b>").append("\n").append("\n")

                        .append(SHOPPING_BAGS).append("Промокод: ").append("<b>")
                        .append(couponPage.getPromocode())
                        .append("</b>").append("\n").append("\n");

                String discount = Optional.ofNullable(couponPage.getDiscount()).orElse("Процент скидки не указан.");
                message.append(CREDIT_CARD).append("Размер скидки: ").append("<b>").append(discount).append("</b>").append("\n").append("\n")

                        .append("<a href=\"").append(couponPage.getGotoLink()).append("\">").append(RIGHT_ARROW)

                        .append("Перейти в " + couponPage.getPartner().getName() + "!").append(LEFT_ARROW).append("</a>")
                        .append("\n");

            }
            messageSenderTypeUpdateChecker.sendMessage(update, message, navigateKeyboard, messageSender);
        }
    }
}
