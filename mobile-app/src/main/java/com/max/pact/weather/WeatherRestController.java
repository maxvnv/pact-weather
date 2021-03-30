package com.max.pact.weather;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("mobile/weather")
public class WeatherRestController {

    @Autowired
    private WeatherServiceDao weatherServiceDao;

    @GetMapping
    public WeatherDto getWeather() {
        return weatherServiceDao.getWeather();
    }


}
