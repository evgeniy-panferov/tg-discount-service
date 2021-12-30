package com.project.tgdiscountservice.model.inner;

import lombok.Getter;
import lombok.Setter;
import org.telegram.telegrambots.meta.api.objects.ChatJoinRequest;
import org.telegram.telegrambots.meta.api.objects.ChatMemberUpdated;
import org.telegram.telegrambots.meta.api.objects.inlinequery.ChosenInlineQuery;
import org.telegram.telegrambots.meta.api.objects.inlinequery.InlineQuery;
import org.telegram.telegrambots.meta.api.objects.payments.PreCheckoutQuery;
import org.telegram.telegrambots.meta.api.objects.payments.ShippingQuery;
import org.telegram.telegrambots.meta.api.objects.polls.Poll;
import org.telegram.telegrambots.meta.api.objects.polls.PollAnswer;

@Getter
@Setter
public class InnerUpdate {

    private Integer updateId;

    private InnerMessage message; ///< Optional. New incoming message of any kind — text, photo, sticker, etc.

    private InlineQuery inlineQuery; ///< Optional. New incoming inline query

    private ChosenInlineQuery chosenInlineQuery; ///< Optional. The result of a inline query that was chosen by a user and sent to their chat partner

    private InnerCallBackQuery callbackQuery; ///< Optional. New incoming callback query

    private InnerMessage editedMessage; ///< Optional. New version of a message that is known to the bot and was edited

    private InnerMessage channelPost; ///< Optional. New incoming channel post of any kind — text, photo, sticker, etc.

    private InnerMessage editedChannelPost; ///< Optional. New version of a channel post that is known to the bot and was edited

    private ShippingQuery shippingQuery; ///< Optional. New incoming shipping query. Only for invoices with flexible price

    private PreCheckoutQuery preCheckoutQuery; ///< Optional. New incoming pre-checkout query. Contains full information about checkout

    private Poll poll; ///< Optional. New poll state. Bots receive only updates about polls, which are sent by the bot.

    private PollAnswer pollAnswer;

    private ChatMemberUpdated myChatMember;

    private ChatMemberUpdated chatMember;

    private ChatJoinRequest chatJoinRequest;
}
