package com.project.tgdiscountservice.service.parser;

import com.project.tgdiscountservice.model.inner.InnerUpdate;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.inlinequery.InlineQuery;

@Slf4j
@Service
@Getter
public class InlineQueryParser extends Parser {

    private String query;
    private InlineQuery inlineQuery;

    @Override
    public InlineQueryParser parse(InnerUpdate update) {
        log.info("InlineQueryParser parse - {}", update);
        inlineQuery = update.getInlineQuery();
        query = inlineQuery.getQuery();
        chatId = Long.valueOf(inlineQuery.getId());
        return this;
    }
}
