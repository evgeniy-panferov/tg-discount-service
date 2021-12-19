package com.project.tgdiscountservice.configuration;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(value = TelegramProperties.class)
public class AppConfiguration {

}
