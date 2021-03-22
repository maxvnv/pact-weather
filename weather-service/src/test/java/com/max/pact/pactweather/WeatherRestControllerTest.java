package com.max.pact.pactweather;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

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
    void test() throws Exception {
        //given
        weatherService.postWeather(now(), new WeatherDto("good!"));

        mockMvc.perform(get("/weather/status"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", equalTo("good!")));
    }
}