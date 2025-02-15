package ru.weather.bot.bot.command;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

/**
 * @author mikhail
 * Класс для обработки команды /start
 */
@Service
@Slf4j
public class StartCommand implements IBotCommand {
    @Override
    public String getCommandIdentifier() {
        return "start";
    }

    @Override
    public String getDescription() {
        return "Запуск бота";
    }

    @Override
    public void processMessage(AbsSender absSender, Message message, String[] strings) {
        log.info(message.getText());
        SendMessage answer = new SendMessage();
        answer.setChatId(message.getChatId());
        answer.setText("""
                Привет! Я WeatherBot! 
                Помогаю узнавать погоду в Вашем городе.
                Поддерживаемые команды:
                /weather_in [Город] - показать погоду в Вашем городе
                /subscribe [Город] - подписка на рассылку погоды
                /unsubscribe - отписка от рассылки уведомлений
                """);
        try {
            absSender.execute(answer);
        } catch (TelegramApiException e) {
            log.error("Error occurred in /start command", e);
        }
    }
}
