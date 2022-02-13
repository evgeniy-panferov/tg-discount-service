package com.project.tgdiscountservice.service.handlers.callback;

import com.project.tgdiscountservice.cache.PartnerCacheImpl;
import com.project.tgdiscountservice.model.Coupon;
import com.project.tgdiscountservice.model.TgPage;
import com.project.tgdiscountservice.model.inner.InnerUpdate;
import com.project.tgdiscountservice.service.CouponTextCreator;
import com.project.tgdiscountservice.service.KeyboardPageGeneration;
import com.project.tgdiscountservice.service.handlers.MessageSenderFacade;
import com.project.tgdiscountservice.service.parser.Parser;
import com.project.tgdiscountservice.util.CouponUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CouponRequestHandler implements CallBackHandler {

    private final CouponTextCreator textCreator;
    private final PartnerCacheImpl partnerCache;
    private final KeyboardPageGeneration<Coupon> pageGeneration;
    private final MessageSenderFacade messageSenderFacade;
    private static final String TYPE_RESOLVER = "/cp";

    @Override
    public void prepareMessage(InnerUpdate update, Parser parser) {
        log.info("CouponRequestHandler prepareMessage -{}, {}", update, parser);
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

        TgPage<Coupon> page = pageGeneration.getPage(
                coupons, index, navigateCommand, TYPE_RESOLVER, 1, partnerId);
        InlineKeyboardMarkup navigateKeyboard = page.getInlineKeyboardMarkup();
        List<Coupon> couponsList = page.getPage();


        for (int i = 0; i < couponsList.size(); i++) {
            Coupon couponPage = couponsList.get(i);
            StringBuilder message = textCreator.createText(couponPage);
            messageSenderFacade.sendMessage(update, message, navigateKeyboard);
        }
    }
}
