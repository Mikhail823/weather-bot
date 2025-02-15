package ru.weather.bot.weather.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public record WeatherApiConfig(@Value("${url.api}") String urlApi) {
}
