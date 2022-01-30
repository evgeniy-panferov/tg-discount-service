package com.project.tgdiscountservice.util;

import com.project.tgdiscountservice.model.inner.InnerUpdate;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateUtil {

    public static InnerUpdate fromDto(Update update) {
        log.info("UpdateUtil fromDto - {}", update);
        if (update == null) {
            return null;
        }
        var innerUpdate = new InnerUpdate();
        innerUpdate.setUpdateId(update.getUpdateId());
        innerUpdate.setMessage(MessageUtil.fromDto(update.getMessage()));
        innerUpdate.setInlineQuery(update.getInlineQuery());
        innerUpdate.setChosenInlineQuery(update.getChosenInlineQuery());
        innerUpdate.setCallbackQuery(CallBackQueryUtil.fromDto(update.getCallbackQuery()));
        innerUpdate.setEditedMessage(MessageUtil.fromDto(update.getEditedMessage()));
        innerUpdate.setChannelPost(MessageUtil.fromDto(update.getChannelPost()));
        innerUpdate.setEditedChannelPost(MessageUtil.fromDto(update.getEditedChannelPost()));
        innerUpdate.setShippingQuery(update.getShippingQuery());
        innerUpdate.setPreCheckoutQuery(update.getPreCheckoutQuery());
        innerUpdate.setPoll(update.getPoll());
        innerUpdate.setPollAnswer(update.getPollAnswer());
        innerUpdate.setMyChatMember(update.getMyChatMember());
        innerUpdate.setChatMember(update.getChatMember());
        innerUpdate.setChatJoinRequest(update.getChatJoinRequest());
        return innerUpdate;

    }

    public static InnerUpdate dtoUpdate(long chatId, String command) {
        log.info("UpdateUtil dtoUpdate - {}, {}", chatId, command);
        var innerUpdate = new InnerUpdate();
        innerUpdate.setMessage(MessageUtil.dtoMessage(chatId, command));
        return innerUpdate;
    }

}
