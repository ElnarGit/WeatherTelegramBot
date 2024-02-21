package com.elnar.telegrambot.TelegramBotWeather.service;

import com.elnar.telegrambot.TelegramBotWeather.config.BotConfig;
import com.elnar.telegrambot.TelegramBotWeather.model.WeatherModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class TelegramBotWeather extends TelegramLongPollingBot {
	private final BotConfig botConfig;
	
	@Override
	public String getBotUsername() {
		return botConfig.getBotName();
	}
	
	public String getBotToken() {
		return botConfig.getToken();
	}
	
	@Override
	public void onUpdateReceived(Update update) {
		WeatherModel weatherModel = new WeatherModel();
		String weatherInfo = "";
		
		if (update.hasMessage() && update.getMessage().hasText()) {
			String messageText = update.getMessage().getText();
			long chatId = update.getMessage().getChatId();
			
			switch (messageText) {
				case "/start":
					startCommandReceived(chatId, update.getMessage().getChat().getFirstName());
					break;
				default:
					try {
						weatherInfo = WeatherService.getWeather(messageText, botConfig.getApiKey(), weatherModel);
						sendMessage(chatId, weatherInfo);
					} catch (IOException e) {
						sendMessage(chatId, "Произошла ошибка при получении информации о погоде.");
					}
			}
		}
	}
	
	private void startCommandReceived(Long chatId, String name) {
		String answer = "Привет, " + name + ", приятно познакомиться!" + "\n" +
				"Введите название города, чтобы узнать текущую погоду в этом городе.";
		
		sendMessage(chatId, answer);
	}
	
	private void sendMessage(Long chatId, String textToSend) {
		SendMessage sendMessage = new SendMessage();
		sendMessage.setChatId(String.valueOf(chatId));
		sendMessage.setText(textToSend);
		
		try {
			execute(sendMessage);
		} catch (TelegramApiException e) {
			e.printStackTrace();
			System.out.println("Произошла ошибка при отправке сообщения.");
		}
	}
}
