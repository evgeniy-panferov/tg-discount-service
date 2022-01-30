package com.project.tgdiscountservice.service.parser;

import com.project.tgdiscountservice.model.inner.InnerUpdate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ParserService {

    public Parser parseUpdate(String typeMessage, InnerUpdate update) {
        log.info("ParserService parseUpdate - {}, {}", typeMessage, update);
        Parser parser = ParserCommand.getParserByCommand(typeMessage);
        return parser.parse(update);
    }

}
