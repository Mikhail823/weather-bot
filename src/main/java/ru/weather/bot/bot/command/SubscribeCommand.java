package ru.weather.bot.bot.command;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;
import ru.weather.bot.model.Subscriber;
import ru.weather.bot.repository.SubscribeRepository;

import java.util.Optional;
import java.util.UUID;

/**
 * @author mikhail
 * Класс обработки команды subscribe
 */

@Component
@Slf4j
@RequiredArgsConstructor
public class SubscribeCommand implements IBotCommand {

    @Autowired
    private final SubscribeRepository repository;

    @Override
    public String getCommandIdentifier() {
        return "subscribe";
    }

    @Override
    public String getDescription() {
        return "Подписка на рассылку уведомлений на погоду";
    }


    @SneakyThrows
    @Override
    public void processMessage(AbsSender absSender, Message message, String[] strings) {
        log.info(message.getText());
        SendMessage send = new SendMessage();
        if (strings.length == 0 || strings[0] == null){
            send.setText("Вы забили указать город.");
            send.setChatId(message.getChatId());
            absSender.executeAsync(send);
            return;
        }

        if (message.getText() != null || message.hasText()){
            send.setText("Вы подписаны на прогноз погоды в городе " + strings[0]);
            send.setChatId(message.getChatId());
            saveSubscriber(message.getChatId(), strings[0]);
            absSender.executeAsync(send);
        }

    }

    /**
     * Метод сохранения подписки пользователя в базе данных
     * @param chatId - тип параметра Long
     * @param city - тип параметра String
     */

    public void saveSubscriber(Long chatId, String city){
        var v1 = repository.findFirstByChatId(chatId);
        if (v1.isPresent()){
            v1.get().setCity(city);
            v1.get().setSubscription(true);
            repository.save(v1.get());
        } else{
            Subscriber subscriber = Subscriber
                    .builder()
                    .id(UUID.randomUUID())
                    .chatId(chatId)
                    .city(city)
                    .isSubscription(true)
                    .build();

            repository.save(subscriber);
        }

        log.info("Сохранение рассылки для чата " + chatId);
    }
}
