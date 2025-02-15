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

@Component
@RequiredArgsConstructor
@Slf4j
public class UnsubscribeCommand implements IBotCommand {

    @Autowired
    private final SubscribeRepository subscribeRepository;

    @Override
    public String getCommandIdentifier() {
        return "unsubscribe";
    }

    @Override
    public String getDescription() {
        return "Отписка от уведомлений";
    }

    @SneakyThrows
    @Override
    public void processMessage(AbsSender absSender, Message message, String[] strings) {
        if (message.hasText() || message.getText() != null){
            var subscriber = subscribeRepository.findFirstByChatId(message.getChatId());

            if (subscriber.isPresent()){
                subscriber.get().setSubscription(false);
                subscribeRepository.save(subscriber.get());
                log.info("Подписка для чата " + message.getChatId() + " отключена");
                var sendMessage = new SendMessage();
                sendMessage.setChatId(message.getChatId());
                sendMessage.setText("Рассылка уведомлений отключена!");
                absSender.execute(sendMessage);

            }

        }
    }
}
