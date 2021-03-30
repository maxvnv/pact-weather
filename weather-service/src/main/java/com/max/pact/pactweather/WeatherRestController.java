package com.max.pact.pactweather;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Optional;

@RestController
@RequestMapping("weather")
public class WeatherRestController {

    @Autowired
    private WeatherService weatherService;

    @GetMapping
    public WeatherDto getWeather(@RequestParam(required = false) LocalDate date) {
        return weatherService.getWeather(optionalDate(date));
    }

    @PostMapping
    public void postWeather(@PathVariable(required = false) LocalDate day, @RequestBody @Valid WeatherDto weatherDto) {
        weatherService.postWeather(optionalDate(day), weatherDto);
    }

    private static LocalDate optionalDate(@RequestParam(required = false) LocalDate date) {
        return Optional.ofNullable(date).orElse(LocalDate.now());
    }
}
