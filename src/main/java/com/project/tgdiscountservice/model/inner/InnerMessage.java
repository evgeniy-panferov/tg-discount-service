package com.project.tgdiscountservice.model.inner;

import lombok.Getter;
import lombok.Setter;
import org.telegram.telegrambots.meta.api.objects.*;
import org.telegram.telegrambots.meta.api.objects.games.Animation;
import org.telegram.telegrambots.meta.api.objects.games.Game;
import org.telegram.telegrambots.meta.api.objects.passport.PassportData;
import org.telegram.telegrambots.meta.api.objects.payments.Invoice;
import org.telegram.telegrambots.meta.api.objects.payments.SuccessfulPayment;
import org.telegram.telegrambots.meta.api.objects.polls.Poll;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.stickers.Sticker;
import org.telegram.telegrambots.meta.api.objects.voicechat.VoiceChatEnded;
import org.telegram.telegrambots.meta.api.objects.voicechat.VoiceChatParticipantsInvited;
import org.telegram.telegrambots.meta.api.objects.voicechat.VoiceChatScheduled;
import org.telegram.telegrambots.meta.api.objects.voicechat.VoiceChatStarted;

import java.util.List;

@Getter
@Setter
public class InnerMessage {

    private Integer messageId; ///< Integer	Unique message identifier

    private User from; ///< Optional. Sender, can be empty for messages sent to channels

    private Integer date; ///< Date the message was sent in Unix time

    private InnerChat chat; ///< Conversation the message belongs to

    private User forwardFrom; ///< Optional. For forwarded messages, sender of the original message

    private InnerChat forwardFromChat;

    private Integer forwardDate; ///< Optional. For forwarded messages, date the original message was sent

    private String text; ///< Optional. For text messages, the actual UTF-8 text of the message

    private List<MessageEntity> entities;

    private List<MessageEntity> captionEntities;

    private Audio audio; ///< Optional. Message is an audio file, information about the file

    private Document document; ///< Optional. Message is a general file, information about the file

    private List<PhotoSize> photo; ///< Optional. Message is a photo, available sizes of the photo

    private Sticker sticker; ///< Optional. Message is a sticker, information about the sticker

    private Video video; ///< Optional. Message is a video, information about the video

    private Contact contact; ///< Optional. Message is a shared contact, information about the contact

    private Location location; ///< Optional. Message is a shared location, information about the location

    private Venue venue; ///< Optional. Message is a venue, information about the venue

    private Animation animation;

    private InnerMessage pinnedMessage; ///< Optional. Specified message was pinned. Note that the Message object in this field will not contain further reply_to_message fields even if it is itself a reply.

    private List<InnerUser> newChatMembers; ///< Optional. New members were added to the group or supergroup, information about them (the bot itself may be one of these members)

    private InnerUser leftChatMember; ///< Optional. A member was removed from the group, information about them (this member may be bot itself)

    private String newChatTitle; ///< Optional. A chat title was changed to this value

    private List<PhotoSize> newChatPhoto; ///< Optional. A chat photo was change to this value

    private Boolean deleteChatPhoto; ///< Optional. Informs that the chat photo was deleted

    private Boolean groupchatCreated; ///< Optional. Informs that the group has been created

    private InnerMessage replyToMessage;

    private Voice voice; ///< Optional. Message is a voice message, information about the file

    private String caption; ///< Optional. Caption for the document, photo or video, 0-200 characters

    private Boolean superGroupCreated;

    private Boolean channelChatCreated;

    private Long migrateToChatId; ///< Optional. The chat has been migrated to a chat with specified identifier, not exceeding 1e13 by absolute value

    private Long migrateFromChatId; ///< Optional. The chat has been migrated from a chat with specified identifier, not exceeding 1e13 by absolute value

    private Integer editDate; ///< Optional. Date the message was last edited in Unix time

    private Game game; ///< Optional. Message is a game, information about the game

    private Integer forwardFromMessageId; ///< Optional. For forwarded channel posts, identifier of the original message in the channel

    private Invoice invoice; ///< Optional. Message is an invoice for a payment, information about the invoice.

    private SuccessfulPayment successfulPayment; ///< Optional. Message is a service message about a successful payment, information about the payment.

    private VideoNote videoNote; ///< Optional. Message is a video note, information about the video message

    private String authorSignature;

    private String forwardSignature; ///< Optional. Post author signature for messages forwarded from channel chats

    private String mediaGroupId; ///< Optional. The unique identifier of a media message group this message belongs to

    private String connectedWebsite; ///< Optional. The domain name of the website on which the user has logged in

    private PassportData passportData; ///< Optional. Telegram Passport data

    private String forwardSenderName; ///< Optional. Sender's name for messages forwarded from users who disallow adding a link to their account in forwarded messages.

    private Poll poll; ///< Optional. Message is a native poll, information about the poll

    private InlineKeyboardMarkup replyMarkup;

    private Dice dice; // Optional. Message is a dice with random value from 1 to 6

    private InnerUser viaBot; // Optional. Bot through which the message was sent

    private InnerChat senderChat;

    private ProximityAlertTriggered proximityAlertTriggered;

    private MessageAutoDeleteTimerChanged messageAutoDeleteTimerChanged; ///< Optional. Service message: auto-delete timer settings changed in the chat

    private VoiceChatStarted voiceChatStarted; ///< Optional. Service message: voice chat started

    private VoiceChatEnded voiceChatEnded; ///< Optional. Service message: voice chat ended

    private VoiceChatParticipantsInvited voiceChatParticipantsInvited; ///< Optional. Service message: new members invited to a voice chat

    private VoiceChatScheduled voiceChatScheduled; ///< Optional. Service message: voice chat scheduled

    private Boolean isAutomaticForward;

    private Boolean canBeForwarded; ///< Optional. True, if the message can be forwarded
}
