package com.project.tgdiscountservice.service.parser;

import com.project.tgdiscountservice.model.inner.InnerCallBackQuery;
import com.project.tgdiscountservice.model.inner.InnerMessage;
import com.project.tgdiscountservice.model.inner.InnerUpdate;
import lombok.Getter;

@Getter
public abstract class Parser {

    protected InnerMessage tgMessage;
    protected String command = "";
    protected String navigateCommand = "";
    protected int index = 0;
    protected String callBackData = "";
    protected InnerCallBackQuery callbackQuery;
    protected Long chatId = 0L;
    protected String[] split;
    protected String id;

    abstract Parser parse(InnerUpdate update);

}
