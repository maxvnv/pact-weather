package com.max.pact.pactweather;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static com.max.pact.pactweather.Wind.W;
import static java.time.LocalDate.now;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class WeatherRestControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private WeatherService weatherService;

    @Test
    void shouldProvideCurrentWeather() throws Exception {
        //given
        weatherService.postWeather(now(),
                WeatherDto.builder()
                        .temperature(10)
                        .airPressure(100.5)
                        .humidity(30.5)
                        .wind(W)
                        .build()
        );

        // expect
        mockMvc.perform(get("/weather"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.temperature", equalTo(10)))
                .andExpect(jsonPath("$.airPressure", equalTo(100.5)))
                .andExpect(jsonPath("$.humidity", equalTo(30.5)))
                .andExpect(jsonPath("$.wind", equalTo("W")));
    }

    @Test
    void shouldProvideWeatherForecast() throws Exception {
        // given
        weatherService.postWeather(now().plusDays(1),
                WeatherDto.builder()
                        .temperature(10)
                        .airPressure(100.5)
                        .humidity(30.5)
                        .wind(W)
                        .build()
        );

        // expect
        mockMvc.perform(get("/weather").queryParam("date", now().plusDays(1).toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.temperature", equalTo(10)))
                .andExpect(jsonPath("$.airPressure", equalTo(100.5)))
                .andExpect(jsonPath("$.humidity", equalTo(30.5)))
                .andExpect(jsonPath("$.wind", equalTo("W")));
    }

    @Test
    void shouldReturn404WhenWeatherIsUnavailable() throws Exception {
        // given
        weatherService.removeWeather(now());

        // expect
        mockMvc.perform(get("/weather"))
                .andExpect(status().isNotFound());
    }
}