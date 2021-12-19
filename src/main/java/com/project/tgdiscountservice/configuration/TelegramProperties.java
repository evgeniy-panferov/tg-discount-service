package com.project.tgdiscountservice.configuration;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "telegram")
@Getter
public class TelegramProperties {

    private String botUsername = "discount_service_bot";

    private String botToken = "5082235523:AAGHWkaNSh1rrCz8Kr9rN8rP08bkZrhyLQk";

}
