package com.project.tgdiscountservice.service;

import lombok.extern.slf4j.Slf4j;
import org.glassfish.grizzly.utils.Pair;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.List;

import static com.project.tgdiscountservice.util.InlineKeyboard.getNavigateKeyboard;

@Slf4j
@Service
public class KeyboardPageGeneration<T> {

    private static final Integer PAGE_SIZE = 10;

    // TODO remove Error
    public Pair<InlineKeyboardMarkup, List<T>> getPage(List<T> collection, Integer startIndex, String navigateCommand, String typeResolver) {
        int size = collection.size();
        int finishIndex = 0;
        InlineKeyboardMarkup navigateKeyboard = null;
        if (navigateCommand.equals("-->") && startIndex <= size) {
            finishIndex = startIndex + PAGE_SIZE;
            navigateKeyboard = getNavigateKeyboard(typeResolver, String.valueOf(finishIndex));
            finishIndex = finishIndex > size ? finishIndex - (finishIndex - size) : finishIndex;
        }

        if (navigateCommand.equals("<--")) {
            finishIndex = startIndex - PAGE_SIZE;
            navigateKeyboard = getNavigateKeyboard(typeResolver, String.valueOf(finishIndex));
            startIndex = Math.max((finishIndex - PAGE_SIZE), 0);
        }

        if (navigateCommand.equals("")) {
            finishIndex = startIndex + PAGE_SIZE;
            navigateKeyboard = getNavigateKeyboard(typeResolver, String.valueOf(finishIndex));
        }

        int finalStartIndex = Math.min(startIndex, finishIndex);
        int finalEndIndex = Math.max(startIndex, finishIndex);

        List<T> page = collection.subList(finalStartIndex, finalEndIndex);
        return new Pair<>(navigateKeyboard, page);
    }
}