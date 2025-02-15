package ru.weather.bot.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import ru.weather.bot.weather.api.Response;
import ru.weather.bot.weather.api.WeatherApiConfig;

/**@author mikhail
 * Класс для отправки GET - запроса к api weather service
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class RestServiceImpl{

    private static final String AQI = "&aqi=no";

    /**
     * WeatherApiConfig - класс с настройками API www.weatherapi.com
     */
    @Autowired
    private final WeatherApiConfig weatherApiConfig;


    /**
     * Метод отправляет get - запрос на weatherapi.com
     * @param city - указывается населенный пункт
     * @return - возвращается ответ ResponseEntity с телом
     * ответа Response.class
     */
    public ResponseEntity<Response> requestApiWeather(String city) throws HttpClientErrorException {
        String url = weatherApiConfig.urlApi() + city + AQI;
        RestTemplate restTemplate = new RestTemplate();
        log.info("Отправка GET - запроса");
        return restTemplate.getForEntity(url, Response.class);
    }
}
