package com.project.tgdiscountservice.service.updateresolver.requestresolver;

import com.project.tgdiscountservice.model.inner.InnerUpdate;
import com.project.tgdiscountservice.service.parser.Parser;

public interface CallBackResolver {
    void prepareMessage(InnerUpdate update, Parser parser);
}
