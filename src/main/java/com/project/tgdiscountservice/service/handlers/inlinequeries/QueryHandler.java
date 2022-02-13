package com.project.tgdiscountservice.service.handlers.inlinequeries;


import com.project.tgdiscountservice.client.DiscountClientAdapterImpl;
import com.project.tgdiscountservice.client.request.TgRequest;
import com.project.tgdiscountservice.model.Coupon;
import com.project.tgdiscountservice.model.inner.InnerUpdate;
import com.project.tgdiscountservice.service.CouponTextCreator;
import com.project.tgdiscountservice.service.handlers.MessageSenderFacade;
import com.project.tgdiscountservice.service.parser.InlineQueryParser;
import com.project.tgdiscountservice.service.parser.Parser;
import com.project.tgdiscountservice.util.CouponUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.inlinequery.InlineQuery;
import org.telegram.telegrambots.meta.api.objects.inlinequery.inputmessagecontent.InputTextMessageContent;
import org.telegram.telegrambots.meta.api.objects.inlinequery.result.InlineQueryResult;
import org.telegram.telegrambots.meta.api.objects.inlinequery.result.InlineQueryResultArticle;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class QueryHandler implements InlineQueryHandler {

    private final DiscountClientAdapterImpl adapter;
    private final MessageSenderFacade messageSenderFacade;
    private final CouponTextCreator textCreator;

    @Override
    public void prepareMessage(InnerUpdate update, Parser parser) {
        log.info("QueryHandler prepareMessage - {}, {}", update, parser);
        if(update.getInlineQuery()==null){
            return;
        }
        InlineQueryParser parserData = (InlineQueryParser) parser;
        String query = parserData.getQuery();
        InlineQuery inlineQuery = parserData.getInlineQuery();

        List<Coupon> coupons = makeCouponSearch(query);
        List<InlineQueryResult> queryResults = new ArrayList<>();

        if (coupons.isEmpty()) {
            String id = "-1L";
            String name = "Купоны не найдены";
            String gotoLink = "";
            String messageText = "Попробуйте ввести другое слово для поиска";
            String description = "Попробуйте ввести другое слово для поиска";
            InlineQueryResultArticle article = createAnswerByQuery(id, name, messageText, description, gotoLink,"");
            queryResults.add(article);
        } else if (coupons.get(0).getId() == -1L) {
            String id = "-1L";
            String name = "Поле ввода пустое";
            String gotoLink = "";
            String messageText = "Чтобы найти купон, введите слово, по которому нужно провести поиск";
            String description = "Чтобы найти купон, введите слово, по которому нужно провести поиск";
            InlineQueryResultArticle article = createAnswerByQuery(id, name, messageText, description, gotoLink,"");
            queryResults.add(article);
        } else {
            for (Coupon coupon : coupons) {
                String id = coupon.getId().toString();
                String name = coupon.getName();
                String gotoLink = coupon.getGotoLink();
                String messageText = textCreator.createText(coupon).toString();
                String description = coupon.getDescription().isEmpty() ? "Партнер не предоставил описание" : coupon.getDescription();
                InlineQueryResultArticle article = createAnswerByQuery(id, name, messageText, description, gotoLink, coupon.getImageUrl());
                queryResults.add(article);
            }
        }

        messageSenderFacade.sendInlineQuery(queryResults, inlineQuery);
    }

    private InlineQueryResultArticle createAnswerByQuery(
            String id,
            String title,
            String messageText,
            String description,
            String url,
            String thumbUrl
    ) {
        InputTextMessageContent messageContent = new InputTextMessageContent();
        messageContent.setDisableWebPagePreview(false);
        messageContent.setParseMode("HTML");
        messageContent.setMessageText(messageText);
        InlineQueryResultArticle article = new InlineQueryResultArticle();
        article.setInputMessageContent(messageContent);
        article.setId(id);
        article.setTitle(title);
        article.setDescription(description);
        article.setUrl(url);
        article.setThumbUrl(thumbUrl);
        return article;
    }

    private List<Coupon> makeCouponSearch(String query) {
        log.info("QueryHandler makeCouponSearch - {}", query);
        List<Coupon> coupons = List.of(CouponUtil.create("Поле ввода пустое", "Введите слово, по которому необходимо провести поиск"));
        if (!query.isEmpty()) {
            TgRequest request = new TgRequest();
            request.setWord(query);
            coupons = adapter.searchCoupons(request);
        }
        return coupons;
    }
}
