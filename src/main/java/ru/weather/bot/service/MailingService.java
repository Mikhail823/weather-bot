package ru.weather.bot.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.weather.bot.bot.WeatherBot;
import ru.weather.bot.exception.LocationNotFoundException;
import ru.weather.bot.model.Subscriber;
import ru.weather.bot.repository.SubscribeRepository;
import ru.weather.bot.util.MessageUtil;
import ru.weather.bot.weather.api.Response;

import java.util.List;

/**
 * @author mikhail
 * Класс реализующий рассылку уведомлений пользователям,
 * имеющим подписку на прогноз погоды.
 */
@Component
@RequiredArgsConstructor
@EnableAsync
@ConditionalOnProperty(name = "scheduler.enabled", matchIfMissing = true)
@Slf4j
public class MailingService {

    @Autowired
    private final SubscribeRepository subscribeRepository;
    @Autowired
    private final RestServiceImpl restService;
    @Autowired
    private final WeatherBot weatherBot;

    /**
     * Метод рассылки уведомлений, использующий Scheduled
     * @throws  TelegramApiException возможен выброс исключения
     *
     **/

    @Async
    @Scheduled(initialDelay = 10000, fixedRateString = "${mailing.interval}")
    public void sendingNotifications() throws TelegramApiException {
        log.info("Рассылка уведомлений!");
        List<Subscriber> subscriberList = subscribeRepository.findAllByIsSubscription(true);
        for (Subscriber subscriber : subscriberList){
            ResponseEntity<Response> response = null;
            try {
                response = restService.requestApiWeather(subscriber.getCity());
            } catch (LocationNotFoundException e) {
                log.error(e.getMessage());
            }
            weatherBot.sendMessage(subscriber.getChatId(), MessageUtil.createMessage(response));
        }
    }
}
