package ru.weather.bot.weather.api;

public record Current(Double wind_kph,
                      Double pressure_mb,
                      Double precip_mm,
                      Integer humidity,
                      Integer cloud,
                      Double temp_c) {}
