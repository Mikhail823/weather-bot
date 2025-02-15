package ru.weather.bot.weather.api;

import static java.lang.String.format;

/**
 * @author mikhail
 * Класс предназначен для преобразования атмосферного давления
 * из милиБарелей в милиметры ртутного столба и преобразование
 * скорости ветра из км/ч в м/с
 */
public class Сonvector {

    private final static Double ONE_MM_OF_MERCURY_COLUMN = 0.750064;

    /**
     * Коэффициент скорости
     */
    private final static Double RATIO = 3.6;

    /**
     * Метод для преобразования атмосферного давления
     * @param pressureMB - тип double. Значение в милиБарах
     * @return - тип String. Возвращается строка с результатом
     */
    public static String convectorFromMBToMM(Double pressureMB){
        double result = pressureMB * ONE_MM_OF_MERCURY_COLUMN;
        return format("%.2f", result);
    }

    /**
     * Метод для преобразования скорости ветра из км/ч в м/с
     * @param windKph - тип double. Значение в км/ч
     * @return - тип String. Возвращается строка с результатом
     */
    public static String convectorFromKPHToMPS(Double windKph){
        double result = windKph / RATIO;
        return format("%.1f", result);
    }
}
