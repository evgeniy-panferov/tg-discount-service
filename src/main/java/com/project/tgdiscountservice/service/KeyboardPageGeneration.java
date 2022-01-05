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

    // TODO remove Error
    public Pair<InlineKeyboardMarkup, List<T>> getPage(List<T> collection, Integer startIndex, String navigateCommand, String typeResolver, int pageSize, String id) {
        int size = collection.size();
        if (size < pageSize) {
            pageSize = size;
        }

        int finishIndex = 0;
        InlineKeyboardMarkup navigateKeyboard = null;
        if (navigateCommand.equals("-->") && startIndex <= size) {
            finishIndex = startIndex + pageSize;
            navigateKeyboard = getNavigateKeyboard(typeResolver, String.valueOf(finishIndex), id);
            finishIndex = finishIndex > size ? finishIndex - (finishIndex - size) : finishIndex;
        }

        if (navigateCommand.equals("<--")) {
            finishIndex = startIndex - pageSize;
            navigateKeyboard = getNavigateKeyboard(typeResolver, String.valueOf(finishIndex), id);
            startIndex = Math.max((finishIndex - pageSize), 0);
        }

        if (navigateCommand.equals("")) {
            finishIndex = startIndex + pageSize;
            navigateKeyboard = getNavigateKeyboard(typeResolver, String.valueOf(finishIndex), id);
        }

        int finalStartIndex = Math.min(startIndex, finishIndex);
        int finalEndIndex = Math.max(startIndex, finishIndex);

        List<T> page = collection.subList(finalStartIndex, finalEndIndex);
        return new Pair<>(navigateKeyboard, page);
    }
}