package com.project.tgdiscountservice.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class InlineKeyboard {

    public static List<KeyboardRow> getKeyboard(List<String> buttonsText) {
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        List<KeyboardButton> buttons = getReplyButtons(buttonsText);
        KeyboardRow keyboardRow = new KeyboardRow();
        for (int i = 0; i < buttons.size(); i++) {
            keyboardRow.add(buttons.get(i));
            keyboardRows.add(keyboardRow);
            if (i % 4 == 0) {
                keyboardRow = new KeyboardRow();
            }
        }
        return keyboardRows;
    }

    public static InlineKeyboardMarkup getInlineKeyboard(List<String> buttonsText) {
        return InlineKeyboardMarkup.builder()
                .keyboard(getInlineButtons(buttonsText))
                .build();
    }

    public static List<KeyboardButton> getReplyButtons(List<String> buttonsText) {
        return buttonsText.stream().map(button -> KeyboardButton.builder()
                .text(button)
                .build()).collect(Collectors.toList());
    }

    public static List<List<InlineKeyboardButton>> getInlineButtons(List<String> buttonsText) {
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        List<InlineKeyboardButton> row = new ArrayList<>();
        for (int i = 0; i < buttonsText.size(); i++) {
            InlineKeyboardButton button = InlineKeyboardButton.builder()
                    .text(buttonsText.get(i))
                    .url("http://" + buttonsText.get(i) + ".ru")
                    .build();
            row.add(button);
            keyboard.add(row);
            if (i % 8 == 0) {
                row = new ArrayList<>();
            }
        }
        return keyboard;
    }

    public static InlineKeyboardMarkup getNavigateKeyboard(String typeResolver, String index) {
        return InlineKeyboardMarkup.builder()
                .keyboard(getNavigateButton(typeResolver, index))
                .build();
    }

    public static List<List<InlineKeyboardButton>> getNavigateButton(String typeResolver, String index) {
        List<List<InlineKeyboardButton>> keyboards = new ArrayList<>();
        List<InlineKeyboardButton> collect = Stream.of("<--", "-->")
                .map(button -> InlineKeyboardButton.builder()
                        .text(button)
                        .callbackData(typeResolver + ":" + button + ":" + index)
                        .build())
                .collect(Collectors.toList());
        keyboards.add(collect);
        return keyboards;
    }


}
