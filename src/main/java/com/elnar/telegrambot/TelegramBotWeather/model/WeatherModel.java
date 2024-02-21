package com.elnar.telegrambot.TelegramBotWeather.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WeatherModel {
	private Double temp;
	private String description;
	private Integer speed;
	private Integer humidity;
	private String country;
}

