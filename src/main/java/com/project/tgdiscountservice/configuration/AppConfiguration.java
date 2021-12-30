package com.project.tgdiscountservice.configuration;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
@EnableConfigurationProperties(value = TelegramProperties.class)
public class AppConfiguration {

}
