package com.project.tgdiscountservice.model.inner;

import lombok.Getter;
import org.telegram.telegrambots.meta.api.objects.User;

@Getter
public class InnerEntity {
    private String type;
    private Integer offset;
    private Integer length;
    private String url;
    private User user;
    private String language;
    private String text;
}
