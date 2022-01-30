package com.project.tgdiscountservice.service.handlers.callback;

import com.project.tgdiscountservice.model.inner.InnerUpdate;
import com.project.tgdiscountservice.service.parser.Parser;

public interface CallBackHandler {
    void prepareMessage(InnerUpdate update, Parser parser);
}
