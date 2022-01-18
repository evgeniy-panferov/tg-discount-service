package com.project.tgdiscountservice.service.parser;

import com.project.tgdiscountservice.model.inner.InnerUpdate;
import org.springframework.stereotype.Service;

@Service
public class InlineQueryParser extends Parser {

    @Override
    public InlineQueryParser parse(InnerUpdate update) {
        return this;
    }
}
