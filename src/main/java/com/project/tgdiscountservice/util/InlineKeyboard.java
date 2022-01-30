package com.project.tgdiscountservice.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class InlineKeyboard {

    public static InlineKeyboardMarkup getNavigateKeyboard(String typeResolver, String index, String id, String... buttons) {
        log.info("InlineKeyboard getNavigateKeyboard - {}, {}, {}, {}", typeResolver, index, id, buttons);
        return InlineKeyboardMarkup.builder()
                .keyboard(getNavigateButton(typeResolver, index, id, buttons))
                .build();
    }

    public static List<List<InlineKeyboardButton>> getNavigateButton(String typeResolver, String index, String id, String... buttons) {
        log.info("InlineKeyboard getNavigateButton - {}, {}, {}, {}", typeResolver, index, id, buttons);
        List<List<InlineKeyboardButton>> keyboards = new ArrayList<>();
        List<InlineKeyboardButton> collect = Stream.of(buttons)
                .map(button -> InlineKeyboardButton.builder()
                        .text(button)
                        .callbackData(typeResolver + "_" + button + "_" + index + "_" + id)
                        .build())
                .collect(Collectors.toList());
        keyboards.add(collect);
        return keyboards;
    }

    public static InlineKeyboardMarkup getNavigateCallbackKeyboard(String typeResolver, String index, String id, String callbackData, String... buttons) {
        log.info("InlineKeyboard getNavigateCallbackKeyboard - {}, {}, {}, {}, {}", typeResolver, index, id, callbackData, buttons );
        return InlineKeyboardMarkup.builder()
                .keyboard(getNavigateCallbackButton(typeResolver, index, id, callbackData, buttons))
                .build();
    }

    public static List<List<InlineKeyboardButton>> getNavigateCallbackButton(String typeResolver, String index, String id, String callbackData, String... buttons) {
      log.info("InlineKeyboard getNavigateCallbackButton - {}, {}, {}, {}, {}", typeResolver, index, id, callbackData, buttons);
        List<List<InlineKeyboardButton>> keyboards = new ArrayList<>();
        List<InlineKeyboardButton> collect = Stream.of(buttons)
                .map(button -> InlineKeyboardButton.builder()
                        .text(button)
                        .callbackData(typeResolver + "_" + callbackData + "_" + index + "_" + id)
                        .build())
                .collect(Collectors.toList());
        keyboards.add(collect);
        return keyboards;
    }

}
