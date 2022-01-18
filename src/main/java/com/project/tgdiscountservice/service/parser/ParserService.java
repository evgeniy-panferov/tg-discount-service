package com.project.tgdiscountservice.service.parser;

import com.project.tgdiscountservice.model.inner.InnerUpdate;
import org.springframework.stereotype.Service;

@Service
public class ParserService {

    public Parser parseUpdate(String typeMessage, InnerUpdate update) {
        Parser parser = ParserCommand.getParserByCommand(typeMessage);
        return parser.parse(update);
    }

}
