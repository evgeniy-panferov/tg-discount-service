package com.project.tgdiscountservice.service.updateresolver;

import com.project.tgdiscountservice.model.Emoji;
import com.project.tgdiscountservice.model.inner.InnerMessage;
import com.project.tgdiscountservice.model.inner.InnerUpdate;
import com.project.tgdiscountservice.service.parser.Parser;
import com.project.tgdiscountservice.service.sender.MessageSender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HelpResolver implements MessageResolver {

    private static final String TYPE_RESOLVER = "/help";
    private final MessageSender sender;

    @Override
    public void prepareMessage(InnerUpdate update, Parser parser) {
        String command = parser.getCommand();

        if (command.equals(TYPE_RESOLVER)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(Emoji.WAVING_HAND).append("Привет!").append(Emoji.WAVING_HAND).append("\n")
                    .append(Emoji.ROBOT).append("Я бот - Промокодич, ищу выгодня предложения по просторам интеренета.").append(Emoji.ROBOT).append("\n")
                    .append(Emoji.MAGNIFYING_GLASS_TILTED_RIGHT).append("Заходи может, что и найдешь!").append(Emoji.MAGNIFYING_GLASS_TILTED_LEFT).append("\n").append("\n")
                    .append("Вводи в строку @freeskidka_bot + то, что ищешь и мы попробуем найти.").append("\n").append("\n")

                    .append(Emoji.DOWN_LEFT_ARROW).append("Команды слева в меню!");

            InnerMessage tgMessage = update.getMessage();
            sender.sendMessage(tgMessage, stringBuilder);
        }
    }

}
