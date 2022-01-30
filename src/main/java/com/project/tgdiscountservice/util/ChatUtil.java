package com.project.tgdiscountservice.util;

import com.project.tgdiscountservice.model.inner.InnerChat;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.objects.Chat;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ChatUtil {

    public static InnerChat fromDto(Chat chat) {
        log.info("ChatUtil fromDto - {}", chat);
        var innerChat = new InnerChat();
        if (chat != null) {
            innerChat.setId(chat.getId());
            innerChat.setType(chat.getType());
            innerChat.setTitle(chat.getTitle());
            innerChat.setFirstName(chat.getFirstName());
            innerChat.setLastName(chat.getLastName());
            innerChat.setUserName(chat.getUserName());
            innerChat.setAllMembersAreAdministrators(chat.getAllMembersAreAdministrators());
            innerChat.setPhoto(chat.getPhoto());
            innerChat.setDescription(chat.getDescription());
            innerChat.setInviteLink(chat.getInviteLink());
            innerChat.setPinnedMessage(chat.getPinnedMessage());
            innerChat.setStickerSetName(chat.getStickerSetName());
            innerChat.setCanSetStickerSet(chat.getCanSetStickerSet());
            innerChat.setPermissions(chat.getPermissions());
            innerChat.setSlowModeDelay(chat.getSlowModeDelay());
            innerChat.setBio(chat.getBio());
            innerChat.setLinkedChatId(chat.getLinkedChatId());
            innerChat.setLocation(chat.getLocation());
            innerChat.setMessageAutoDeleteTime(chat.getMessageAutoDeleteTime());
            innerChat.setAllowSavingContent(chat.getAllowSavingContent());
            innerChat.setHasPrivateForwards(chat.getHasPrivateForwards());
        }
        return innerChat;
    }

    public static InnerChat chatDto(Long chatId) {
        log.info("ChatUtil chatDto - {}", chatId);
        var chat = new InnerChat();
        chat.setId(chatId);
        return chat;
    }
}
