package ru.weather.bot.util;

import org.springframework.http.ResponseEntity;
import ru.weather.bot.weather.api.Сonvector;
import ru.weather.bot.weather.api.Response;

/**
 * @author mikhail
 * Класс для создания сообщения пользователю о прогнозе погоды
 */
public class MessageUtil {

    /**
     * Метод для создания сообщения из тела сообщения
     * @param response - тип ResponseEntity<Response>
     * @return String - возвращается готовая строка сообщения
     */

    public static String createMessage(ResponseEntity<Response> response){
        return "Ваш город: " + response.getBody().getLocation().getName() + "\n" +
                "Регион: " + response.getBody().getLocation().getRegion() + "\n" +
                "Температура: " + response.getBody().getCurrent().temp_c() + " ⁰С" + "\n" +
                "Давление: " + Сonvector
                .convectorFromMBToMM(response.getBody().getCurrent().pressure_mb()) + " мм. рт. ст." + "\n"+
                "Скорость ветра: " + Сonvector.convectorFromKPHToMPS(response.getBody().getCurrent().wind_kph()) + " м/с" + "\n"+
                "Количество осадков: " + response.getBody().getCurrent().precip_mm() + " мм." + "\n"+
                "Влажность: " + response.getBody().getCurrent().humidity() + "%";
    }
}
