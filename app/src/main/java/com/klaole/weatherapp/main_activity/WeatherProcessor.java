package com.klaole.weatherapp.main_activity;

import com.klaole.weatherapp.models.ConsolidatedWeather;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

public abstract class WeatherProcessor {

    public static ConsolidatedWeather getOneDayForecast(List<ConsolidatedWeather> list) {

        Map<String, Long> counts =
                list.stream().collect(Collectors.groupingBy(ConsolidatedWeather::getWeatherStateAbbr, Collectors.counting()));

        String weatherState = Collections.max(counts.entrySet(), (entry1, entry2) -> entry1.getValue().intValue() - entry2.getValue().intValue()).getKey();

        double averageTemp = calculateAverageTemp(list);

        ConsolidatedWeather consolidatedWeather = new ConsolidatedWeather();
        consolidatedWeather.setWeatherStateAbbr(weatherState);
        consolidatedWeather.setTheTemp(averageTemp);
        consolidatedWeather.setApplicableDate(list.get(0).getApplicableDate());


        return consolidatedWeather;
    }

    private static double calculateAverageTemp(List<ConsolidatedWeather> list) {

        OptionalDouble minAvg = list.stream().map(ConsolidatedWeather::getMinTemp).filter(Objects::nonNull).mapToDouble(a -> a).average();
        OptionalDouble maxAvg = list.stream().map(ConsolidatedWeather::getMaxTemp).filter(Objects::nonNull).mapToDouble(a -> a).average();

        return ((minAvg.isPresent() ? minAvg.getAsDouble() : 0) + (maxAvg.isPresent() ? maxAvg.getAsDouble() : 0)) / 2;
    }


}
