package com.project.tgdiscountservice.service.updateresolver;

import com.project.tgdiscountservice.http.TelegramSender;
import com.project.tgdiscountservice.model.Emoji;
import com.project.tgdiscountservice.model.inner.InnerMessage;
import com.project.tgdiscountservice.model.inner.InnerUpdate;
import com.project.tgdiscountservice.service.sender.MessageSender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class StartResolver implements TelegramUpdateResolver {

    private final static String TYPE_RESOLVER = "/start";
    private final MessageSender sender;

    @Override
    public void prepareMessage(InnerUpdate update) {
        InnerMessage tgMessage = update.getMessage();
        String command = tgMessage != null ? tgMessage.getText() : "";
        if (command.equals(TYPE_RESOLVER)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(Emoji.WAVING_HAND).append("Привет!").append(Emoji.WAVING_HAND).append("\n")
                    .append(Emoji.ROBOT).append("Я бот - Промокодич, ищу выгодня предложения по просторам интеренета.").append(Emoji.ROBOT).append("\n")
                    .append(Emoji.MAGNIFYING_GLASS_TILTED_RIGHT).append("Заходи может, что и найдешь!").append(Emoji.MAGNIFYING_GLASS_TILTED_LEFT);
            sender.sendMessage(tgMessage, stringBuilder);
        }
    }
}
