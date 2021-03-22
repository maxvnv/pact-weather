package com.max.pact.pactweather;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No weather available")
public class WeatherUnavailableException extends RuntimeException {
}