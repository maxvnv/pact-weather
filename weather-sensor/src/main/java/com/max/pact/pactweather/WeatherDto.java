package com.max.pact.pactweather;

import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Value
@Builder
public class WeatherDto {

    @NotNull
    Integer temperature;
    @NotNull
    @Min(87)
    @Max(109)
    Double airPressure;
    @NotNull
    @Min(0)
    @Max(100)
    Double humidity;
    @NotNull
    Wind wind;
    @NotNull
    Boolean isSafe;


}
