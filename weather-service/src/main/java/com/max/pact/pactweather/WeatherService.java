package com.max.pact.pactweather;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class WeatherService {

    private final Map<LocalDate, WeatherDto> weatherByDate = new HashMap<>();

    WeatherDto getWeather(LocalDate date) {
        return Optional.ofNullable(weatherByDate.get(date))
                .orElseThrow(WeatherUnavailableException::new);
    }

    public void postWeather(LocalDate date, WeatherDto weather) {
        weatherByDate.put(date, weather);
    }

    public void removeWeather(LocalDate date) {
        weatherByDate.remove(date);
    }
}
