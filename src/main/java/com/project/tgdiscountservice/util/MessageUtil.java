package com.project.tgdiscountservice.util;

import com.project.tgdiscountservice.model.inner.InnerMessage;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.Message;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MessageUtil {

    public static InnerMessage fromDto(Message message) {
        if (message == null) {
            return null;
        }
        var innerMessage = new InnerMessage();
        innerMessage.setMessageId(message.getMessageId());
        innerMessage.setFrom(message.getFrom());
        innerMessage.setDate(message.getDate());
        innerMessage.setChat(ChatUtil.fromDto(message.getChat()));
        innerMessage.setForwardFrom(message.getForwardFrom());
        innerMessage.setForwardFromChat(ChatUtil.fromDto(message.getForwardFromChat()));
        innerMessage.setForwardDate(message.getForwardDate());
        innerMessage.setText(message.getText());
        innerMessage.setAudio(message.getAudio());
        innerMessage.setDocument(message.getDocument());
        innerMessage.setSticker(message.getSticker());
        innerMessage.setVideo(message.getVideo());
        innerMessage.setContact(message.getContact());
        innerMessage.setLocation(message.getLocation());
        innerMessage.setVenue(message.getVenue());
        innerMessage.setAnimation(message.getAnimation());
        innerMessage.setPinnedMessage(MessageUtil.fromDto(message.getPinnedMessage()));
        innerMessage.setLeftChatMember(UserUtil.fromDto(message.getLeftChatMember()));
        innerMessage.setNewChatTitle(message.getNewChatTitle());
        innerMessage.setDeleteChatPhoto(message.getDeleteChatPhoto());
        innerMessage.setGroupchatCreated(message.getGroupchatCreated());
        innerMessage.setReplyToMessage(MessageUtil.fromDto(message.getReplyToMessage()));
        innerMessage.setVoice(message.getVoice());
        innerMessage.setCaption(message.getCaption());
        innerMessage.setSuperGroupCreated(message.getSuperGroupCreated());
        innerMessage.setChannelChatCreated(message.getChannelChatCreated());
        innerMessage.setMigrateToChatId(message.getMigrateToChatId());
        innerMessage.setMigrateFromChatId(message.getMigrateFromChatId());
        innerMessage.setEditDate(message.getEditDate());
        innerMessage.setGame(message.getGame());
        innerMessage.setForwardFromMessageId(message.getForwardFromMessageId());
        innerMessage.setInvoice(message.getInvoice());
        innerMessage.setSuccessfulPayment(message.getSuccessfulPayment());
        innerMessage.setVideoNote(message.getVideoNote());
        innerMessage.setAuthorSignature(message.getAuthorSignature());
        innerMessage.setForwardSignature(message.getForwardSignature());
        innerMessage.setMediaGroupId(message.getMediaGroupId());
        innerMessage.setConnectedWebsite(message.getConnectedWebsite());
        innerMessage.setPassportData(message.getPassportData());
        innerMessage.setForwardSenderName(message.getForwardSenderName());
        innerMessage.setPoll(message.getPoll());
        innerMessage.setReplyMarkup(message.getReplyMarkup());
        innerMessage.setDice(message.getDice());
        innerMessage.setViaBot(UserUtil.fromDto(message.getViaBot()));
        innerMessage.setSenderChat(ChatUtil.fromDto(message.getSenderChat()));
        innerMessage.setProximityAlertTriggered(message.getProximityAlertTriggered());
        innerMessage.setMessageAutoDeleteTimerChanged(message.getMessageAutoDeleteTimerChanged());
        innerMessage.setVoiceChatStarted(message.getVoiceChatStarted());
        innerMessage.setVoiceChatEnded(message.getVoiceChatEnded());
        innerMessage.setVoiceChatParticipantsInvited(message.getVoiceChatParticipantsInvited());
        innerMessage.setVoiceChatScheduled(message.getVoiceChatScheduled());
        innerMessage.setIsAutomaticForward(message.getIsAutomaticForward());
        innerMessage.setCanBeForwarded(message.getCanBeForwarded());
        innerMessage.setEntities(message.getEntities());
        innerMessage.setCaptionEntities(message.getCaptionEntities());
        innerMessage.setPhoto(message.getPhoto());
        innerMessage.setNewChatMembers(UserUtil.fromDtos(message.getNewChatMembers()));
        innerMessage.setNewChatPhoto(message.getNewChatPhoto());
        return innerMessage;
    }
}
