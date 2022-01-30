package com.project.tgdiscountservice.service.handlers.inlinequeries;


import com.project.tgdiscountservice.client.DiscountClientAdapterImpl;
import com.project.tgdiscountservice.client.request.TgRequest;
import com.project.tgdiscountservice.model.Coupon;
import com.project.tgdiscountservice.model.inner.InnerUpdate;
import com.project.tgdiscountservice.service.CouponTextCreator;
import com.project.tgdiscountservice.service.handlers.MessageSenderFacade;
import com.project.tgdiscountservice.service.parser.InlineQueryParser;
import com.project.tgdiscountservice.service.parser.Parser;
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
        if(coupons==null){
            return;
        }
        for (Coupon coupon : coupons) {
            InputTextMessageContent messageContent = new InputTextMessageContent();
            messageContent.setDisableWebPagePreview(false);
            messageContent.setParseMode("HTML");
            messageContent.setMessageText(textCreator.createText(coupon).toString());
            InlineQueryResultArticle article = new InlineQueryResultArticle();
            article.setInputMessageContent(messageContent);
            article.setId(coupon.getId().toString());
            article.setTitle(coupon.getName());
            article.setDescription(coupon.getDescription().isEmpty() ? "Партнер не предоставил описание" : coupon.getDescription());
            article.setThumbUrl(coupon.getGotoLink());
            queryResults.add(article);
        }

        messageSenderFacade.sendInlineQuery(queryResults, inlineQuery);
    }

    private List<Coupon> makeCouponSearch(String query) {
        log.info("QueryHandler makeCouponSearch - {}", query);
        List<Coupon> coupons = null;
        if (!query.isEmpty()) {
            TgRequest request = new TgRequest();
            request.setWord(query);
            coupons = adapter.searchCoupons(request);
        }
        return coupons;
    }
}
