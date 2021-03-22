package com.max.pact.pactweather;

import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junitsupport.Provider;
import au.com.dius.pact.provider.junitsupport.State;
import au.com.dius.pact.provider.junitsupport.StateChangeAction;
import au.com.dius.pact.provider.junitsupport.loader.PactBroker;
import au.com.dius.pact.provider.spring.junit5.PactVerificationSpringProvider;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@Provider("Weather Service")
@PactBroker(url = "http://localhost:9292")
public class PactTest {

    @Autowired
    private WeatherService weatherService;

    @TestTemplate
    @ExtendWith(PactVerificationSpringProvider.class)
    void pactVerificationTestTemplate(PactVerificationContext context) {
        context.verifyInteraction();
    }

    @State("Weather for today is good")
    public void weatherForTodayIsGood() {
        weatherService.postWeather(LocalDate.now(), new WeatherDto("good!"));
    }

    @State(value = "Weather for 2021-03-21 is meh")
    public void weatherFor20210321IsMeh() {
        weatherService.postWeather(LocalDate.of(2021, 3, 21), new WeatherDto("meh"));
    }

    @State(value = "Weather for 2021-03-21 is meh", action = StateChangeAction.TEARDOWN)
    public void weatherFor20210321IsMehTearDown() {
        weatherService.removeWeather(LocalDate.of(2021, 3, 21));
    }

    @State("Weather for today is unavailable")
    public void weatherUnavailable() {
        weatherService.removeWeather(LocalDate.now());
    }
}
