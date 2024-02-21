package com.elnar.telegrambot.TelegramBotWeather.service;

import com.elnar.telegrambot.TelegramBotWeather.model.WeatherModel;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

public class WeatherService {
	
	public static String getWeather(String cityName, String apiKey, WeatherModel model) throws IOException {
		URL url = new URL("http://api.openweathermap.org/data/2.5/weather?q=" + cityName +
				"&appid=" + apiKey);
		
		Scanner scanner = new Scanner(url.openStream());
		StringBuilder result = new StringBuilder();
		
		while (scanner.hasNext()) {
			result.append(scanner.nextLine());
		}
		
		scanner.close();
		
		JSONObject object = new JSONObject(result.toString());
		JSONArray weatherArray = object.getJSONArray("weather");
		JSONObject main = object.getJSONObject("main");
		JSONObject wind = object.getJSONObject("wind");
		JSONObject sys = object.getJSONObject("sys");
		
		double temperature = main.getDouble("temp");
		int humidity = main.getInt("humidity");
		String description = weatherArray.getJSONObject(0).getString("description");
		int speed = wind.getInt("speed");
		String country = sys.getString("country");
		
		model.setTemp(temperature);
		model.setHumidity(humidity);
		model.setDescription(description);
		model.setSpeed(speed);
		model.setCountry(country);
		
		return "Погода в городе " + cityName + ":\n" +
				"Температура: " + temperature + "°C\n" +
				"Влажность: " + humidity + "%\n" +
				"Описание: " + description + "\n" +
				"Скорость ветра: " + speed + " м/с\n" +
				"Страна: " + country;
	}
}
