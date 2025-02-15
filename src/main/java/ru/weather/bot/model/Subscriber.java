package ru.weather.bot.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

/**
 * @author mikhail
 * Сущность Subscriber для работы с базой данных
 */

@Table
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Subscriber {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "chat_id")
    private Long chatId;

    private String city;

    private boolean isSubscription;

}
