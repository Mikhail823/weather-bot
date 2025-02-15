package ru.weather.bot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.weather.bot.model.Subscriber;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SubscribeRepository extends JpaRepository<Subscriber, UUID> {
    List<Subscriber> findAllByIsSubscription(boolean isSub);
    Optional<Subscriber> findFirstByChatId(Long chatId);
}
