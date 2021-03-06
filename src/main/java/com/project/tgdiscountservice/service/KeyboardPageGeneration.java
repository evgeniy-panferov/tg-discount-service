package com.project.tgdiscountservice.service;

import com.project.tgdiscountservice.model.Emoji;
import com.project.tgdiscountservice.model.TgPage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.List;

import static com.project.tgdiscountservice.util.InlineKeyboard.getNavigateKeyboard;

@Slf4j
@Service
public class KeyboardPageGeneration<T> {

    // TODO remove Error
    public TgPage<T> getPage(List<T> collection, Integer startIndex, String navigateCommand, String typeResolver, int pageSize, String id) {
        log.info("KeyboardPageGeneration getPage - {}, {}, {}, {}, {}, {}", collection, startIndex, navigateCommand, typeResolver, pageSize, id);
        int size = collection.size();
        if (size < pageSize) {
            pageSize = size;
        }

        int finishIndex = 0;
        InlineKeyboardMarkup navigateKeyboard = null;
        if (navigateCommand.equals(Emoji.RIGHT_ARROW.toString()) && startIndex <= size) {
            finishIndex = startIndex + pageSize;
            navigateKeyboard = getNavigateKeyboard(typeResolver, String.valueOf(finishIndex), id, Emoji.LEFT_ARROW.toString(), Emoji.RIGHT_ARROW.toString());
            finishIndex = finishIndex > size ? finishIndex - (finishIndex - size) : finishIndex;
        }

        if (navigateCommand.equals(Emoji.LEFT_ARROW.toString())) {
            finishIndex = startIndex - pageSize;
            navigateKeyboard = getNavigateKeyboard(typeResolver, String.valueOf(finishIndex), id, Emoji.LEFT_ARROW.toString(), Emoji.RIGHT_ARROW.toString());
            startIndex = Math.max((finishIndex - pageSize), 0);
        }

        if (navigateCommand.equals("")) {
            finishIndex = startIndex + pageSize;
            navigateKeyboard = getNavigateKeyboard(typeResolver, String.valueOf(finishIndex), id, Emoji.LEFT_ARROW.toString(), Emoji.RIGHT_ARROW.toString());
        }

        int finalStartIndex = Math.min(startIndex, finishIndex);
        int finalEndIndex = Math.max(startIndex, finishIndex);
        List<T> page = collection.subList(finalStartIndex, finalEndIndex);

        TgPage<T> tgPage = new TgPage<>();
        tgPage.setInlineKeyboardMarkup(navigateKeyboard);
        tgPage.setPage(page);
        return tgPage;
    }
}