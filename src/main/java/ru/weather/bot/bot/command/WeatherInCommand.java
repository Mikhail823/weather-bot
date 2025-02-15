package ru.weather.bot.bot.command;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;
import ru.weather.bot.service.RestServiceImpl;
import ru.weather.bot.util.MessageUtil;


/**
 * @author mikhail
 * Класс для обработки команды /weather_in
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class WeatherInCommand implements IBotCommand {

    @Autowired
    private final RestServiceImpl restService;

    @Override
    public String getCommandIdentifier() {
        return "weather_in";
    }

    @Override
    public String getDescription() {
        return "Показывает прогноз погоды в указанном городе";
    }

    @SneakyThrows
    @Override
    public void processMessage(AbsSender absSender, Message message, String[] strings) {
        SendMessage send = new SendMessage();
        if (strings.length == 0 || strings[0] == null){
            send.setText("Вы забили указать город.");
            send.setChatId(message.getChatId());
            absSender.executeAsync(send);
            return;
        }
        try{
            if (message.hasText() && message.getText() != null){
                send.setText(MessageUtil.createMessage(restService.requestApiWeather(strings[0])));
                send.setChatId(message.getChatId());
                absSender.executeAsync(send);
            }
        } catch (HttpClientErrorException e){
            send.setText("Вы не правильно указали город!");
            send.setChatId(message.getChatId());
            absSender.executeAsync(send);
            log.error(e.getMessage());
        }
    }
}
