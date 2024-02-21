package com.elnar.telegrambot.TelegramBotWeather.config;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("application.properties")
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class BotConfig {
	
	@Value("${bot.name}")
	String botName;
	
	@Value("${bot.token}")
	String token;
	
	@Value("${bot.api}")
	private String apiKey;
}
