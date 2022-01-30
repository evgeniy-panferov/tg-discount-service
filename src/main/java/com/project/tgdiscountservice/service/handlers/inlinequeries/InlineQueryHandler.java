package com.project.tgdiscountservice.service.handlers.inlinequeries;


import com.project.tgdiscountservice.model.inner.InnerUpdate;
import com.project.tgdiscountservice.service.parser.Parser;

public interface InlineQueryHandler {
    void prepareMessage(InnerUpdate update, Parser parser);
}
