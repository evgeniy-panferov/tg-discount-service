package com.project.tgdiscountservice.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "telegram")
@Getter
@Setter
public class TelegramProperties {

    private String botUsername;

    private String botToken;

}
