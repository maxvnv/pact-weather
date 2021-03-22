package com.max.pact.weather;

import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.stereotype.Component;

@Component
public class WeatherServiceDao extends RestClient {

    private ConfigurableEnvironment environment;

    public WeatherServiceDao(ConfigurableEnvironment environment) {
        this.environment = environment;
    }

    WeatherDto getWeather() {
        return getFor("/weather/status", WeatherDto.class);
    }

    private String getWeatherServiceUrl() {
        return environment.getProperty("weather.service.url");
    }

    @Override
    String getUrl() {
        return getWeatherServiceUrl();
    }

}
