package ru.weather.bot.weather.api;

import lombok.Data;

@Data
public class Response {
    private Location location;
    private Current current;
}
