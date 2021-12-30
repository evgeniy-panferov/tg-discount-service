package com.project.tgdiscountservice.model.inner;

import lombok.Getter;
import lombok.Setter;
import org.telegram.telegrambots.meta.api.objects.ChatLocation;
import org.telegram.telegrambots.meta.api.objects.ChatPermissions;
import org.telegram.telegrambots.meta.api.objects.ChatPhoto;
import org.telegram.telegrambots.meta.api.objects.Message;

@Getter
@Setter
public class InnerChat {
    private Long id; ///< Unique identifier for this chat, not exceeding 1e13 by absolute value

    private String type; ///< Type of the chat, one of “private”, “group” or “channel” or "supergroup"

    private String title; ///< Optional. Title of the chat, only for channels and group chat

    private String firstName; ///< Optional. Username of the chat, only for private chats and channels if available

    private String lastName; ///< Optional. Interlocutor's first name for private chats

    private String userName; ///< Optional. Interlocutor's last name for private chats

    private Boolean allMembersAreAdministrators;

    private ChatPhoto photo; ///< Optional. Chat photo. Returned only in getChat.

    private String description; ///< Optional. Description, for groups, supergroups and channel chats. Returned only in getChat.

    private String inviteLink; ///< Optional. Primary invite link, for groups, supergroups and channel chats. Returned only in getChat.

    private Message pinnedMessage; ///< Optional. The most recent pinned message (by sending date). Returned only in getChat.

    private String stickerSetName; ///< Optional. For supergroups, name of Group sticker set. Returned only in getChat.

    private Boolean canSetStickerSet; ///< Optional. True, if the bot can change group the sticker set. Returned only in getChat.

    private ChatPermissions permissions; ///< Optional. Default chat member permissions, for groups and supergroups. Returned only in getChat.

    private Integer slowModeDelay;

    private String bio; ///< Optional. Bio of the other party in a private chat. Returned only in getChat.

    private Long linkedChatId;

    private ChatLocation location; ///< Optional. For supergroups, the location to which the supergroup is connected. Returned only in getChat.

    private Integer messageAutoDeleteTime; ///< Optional. The time after which all messages sent to the chat will be automatically deleted; in seconds. Returned only in getChat.

    private Boolean allowSavingContent;

    private Boolean hasPrivateForwards;
}
