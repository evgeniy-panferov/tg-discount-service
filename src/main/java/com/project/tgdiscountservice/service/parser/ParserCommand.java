package com.project.tgdiscountservice.service.parser;

import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
public enum ParserCommand {
    CALLBACK("callback", new CallBackParser()),
    MESSAGE("message", new MessageParser()),
    INLINE_QUERY("inlineQuery", new InlineQueryParser());

    private final String command;
    private final Parser parser;
    private static final Map<String, Parser> parserByCommand = Arrays.stream(values())
            .collect(Collectors.toMap(ParserCommand::getCommand, ParserCommand::getParser));

    ParserCommand(String command, Parser parser) {
        this.command = command;
        this.parser = parser;
    }

    public static Parser getParserByCommand(String command) {
        return parserByCommand.get(command);
    }
}
