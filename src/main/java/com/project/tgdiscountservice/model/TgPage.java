package com.project.tgdiscountservice.model;

import lombok.Getter;
import lombok.Setter;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.List;

@Getter
@Setter
public class TgPage<T> {

    private InlineKeyboardMarkup inlineKeyboardMarkup;
    private List<T> page;
}
