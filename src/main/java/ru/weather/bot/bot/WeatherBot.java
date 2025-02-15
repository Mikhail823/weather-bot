package ru.weather.bot.bot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

@Component
@Slf4j
public class WeatherBot extends TelegramLongPollingCommandBot {

    private final String botName;

    public WeatherBot(@Value("${bot.token}")String botToken,
                      @Value("${bot.name}") String botName,
            List<IBotCommand> commandList) {
        super(botToken);
        this.botName = botName;
        commandList.forEach(this::register);
    }

    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public void processNonCommandUpdate(Update update) {
    }

    /**
     * Метод для отправки текстовых сообщений
     * @param chatId - тип Long
     * @param msg - тип String
     * @throws TelegramApiException - обрабатываемое исключение
     */
    public void sendMessage(Long chatId, String msg) throws TelegramApiException {
        var sm = SendMessage.builder()
                .chatId(chatId)
                .text(msg)
                .build();
        execute(sm);
    }
}
