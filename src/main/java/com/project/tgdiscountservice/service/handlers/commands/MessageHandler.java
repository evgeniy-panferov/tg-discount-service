package com.project.tgdiscountservice.service.handlers.commands;

import com.project.tgdiscountservice.model.inner.InnerUpdate;
import com.project.tgdiscountservice.service.parser.Parser;

public interface MessageHandler {
    void prepareMessage(InnerUpdate update, Parser parser);
}
