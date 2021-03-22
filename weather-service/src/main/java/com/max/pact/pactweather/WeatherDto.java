package com.max.pact.pactweather;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Value;

@Value
public class WeatherDto {
    String status;

    @JsonCreator
    public WeatherDto(String status) {
        this.status = status;
    }
}
