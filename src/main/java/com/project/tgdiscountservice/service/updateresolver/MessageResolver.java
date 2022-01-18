package com.project.tgdiscountservice.service.updateresolver;

import com.project.tgdiscountservice.model.inner.InnerUpdate;
import com.project.tgdiscountservice.service.parser.Parser;

public interface MessageResolver {
    void prepareMessage(InnerUpdate update, Parser parser);
}
