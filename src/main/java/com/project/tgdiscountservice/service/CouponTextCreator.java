package com.project.tgdiscountservice.service;

import com.project.tgdiscountservice.model.Coupon;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static com.project.tgdiscountservice.model.Emoji.*;

@Slf4j
@Service
public class CouponTextCreator {

    @Value("${app.host}")
    private String host;

    public StringBuilder createText(Coupon coupon) {
        log.info("CouponTextCreator createText - {}", coupon);
        StringBuilder message = new StringBuilder();
        String imageUrl = coupon.getImageUrl();
        message.append("<a href=\"");
        if (imageUrl.contains(".svg")) {
            message.append(host).append("/pictures?imageUrl=").append(imageUrl).append("\">").append(" ").append("</a>");
        } else {
            message.append(imageUrl).append("\">").append(" ").append("</a>");
        }

        if (coupon.getId().equals(-1L)) {
            message.append("<b><u>").append(coupon.getPartner().getName()).append("</u></b>").append("\n")
                    .append("\n").append(coupon.getName()).append("\n");
        } else {

            message.append("<b><u>").append(coupon.getPartner().getName()).append("</u></b>").append("\n")
                    .append("\n")
                    .append(LIGHT_BULB).append("Название купона: ")
                    .append(coupon.getName()).append("\n").append("\n")

                    .append(SCROLL).append("Описание спецпредложения: ")
                    .append(coupon.getDescription()).append("\n").append("\n");

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            String date = coupon.getDateEnd() == null ? "Дата окончания отсутствует" : coupon.getDateEnd().format(formatter);

            message.append(TIMER_CLOCK).append("Дата окончания акции: ").append("<b>").append("\n")
                    .append(date).append("</b>").append("\n").append("\n")

                    .append(SHOPPING_BAGS).append("Промокод: ").append("<b>")
                    .append(coupon.getPromocode())
                    .append("</b>").append("\n").append("\n");

            String discount = Optional.ofNullable(coupon.getDiscount()).orElse("Процент скидки не указан.");
            message.append(CREDIT_CARD).append("Размер скидки: ").append("<b>").append(discount).append("</b>").append("\n").append("\n")

                    .append("<a href=\"").append(coupon.getGotoLink()).append("\">").append(RIGHT_ARROW)

                    .append("Перейти в " + coupon.getPartner().getName() + "!").append(LEFT_ARROW).append("</a>")
                    .append("\n");
        }
        return message;
    }
}