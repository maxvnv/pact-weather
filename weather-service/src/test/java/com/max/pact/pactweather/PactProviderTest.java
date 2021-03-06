package com.max.pact.pactweather;

import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junitsupport.Provider;
import au.com.dius.pact.provider.junitsupport.State;
import au.com.dius.pact.provider.junitsupport.StateChangeAction;
import au.com.dius.pact.provider.junitsupport.loader.PactBroker;
import au.com.dius.pact.provider.junitsupport.loader.VersionSelector;
import au.com.dius.pact.provider.spring.junit5.PactVerificationSpringProvider;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;

import static com.max.pact.pactweather.Wind.W;
import static java.time.LocalDate.now;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@Provider("Weather Service")
@PactBroker(url = "http://localhost:9292", tags = {"master"}, providerTags = "master")
@VersionSelector(tag = "master")
public class PactProviderTest {

    @Autowired
    private WeatherService weatherService;

    @TestTemplate
    @ExtendWith(PactVerificationSpringProvider.class)
    void pactVerificationTestTemplate(PactVerificationContext context) {
        context.verifyInteraction();
    }

    @State("Weather for today is safe")
    public void weatherForTodayIsSafe() {
        weatherService.postWeather(now(), WeatherDto.builder()
                .temperature(25)
                .airPressure(100.5)
                .humidity(30.5)
                .wind(W)
                .isSafe(true)
                .build()
        );
    }

    @State(value = "Weather for 2021-03-21 is not safe")
    public void weatherFor20210321IsNotSafe() {
        weatherService.postWeather(LocalDate.of(2021, 3, 21), WeatherDto.builder()
                .temperature(2)
                .airPressure(100.5)
                .humidity(98.0)
                .wind(W)
                .isSafe(false)
                .build()
        );
    }

    @State(value = "Weather for 2021-03-21 is not safe", action = StateChangeAction.TEARDOWN)
    public void weatherFor20210321IsMehTearDown() {
        weatherService.removeWeather(LocalDate.of(2021, 3, 21));
    }

    @State("Weather for today is unavailable")
    public void weatherUnavailable() {
        weatherService.removeWeather(now());
    }
}
