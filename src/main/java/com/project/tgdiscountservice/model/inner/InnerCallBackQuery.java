package com.project.tgdiscountservice.model.inner;

import lombok.Getter;
import lombok.Setter;
import org.telegram.telegrambots.meta.api.objects.Message;

@Getter
@Setter
public class InnerCallBackQuery {
    private String id;
    private Message message;
    private String inlineMessageId;
    private String data;
    private String gameShortName;
    private String chatInstance;
}
