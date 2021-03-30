package com.max.pact.weather.integration;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.google.gson.Gson;
import com.max.pact.weather.SpringTestConfig;
import com.max.pact.weather.WeatherDto;
import com.max.pact.weather.WeatherServiceDao;
import com.max.pact.weather.Wind;
import com.max.pact.weather.wiremock.WireMockExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration(classes = SpringTestConfig.class)
class WeatherRestControllerTest extends AbstractSpringIT {

    @Autowired
    @RegisterExtension
    WireMockExtension wireMockExtension;

    @Autowired
    private WeatherServiceDao weatherServiceDao;

    @Test
    void shouldForwardValidWeatherResponse() throws Exception {
        // given
        stubFor(WireMock.get(urlEqualTo("/weather"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", APPLICATION_JSON_VALUE)
                        .withStatus(200)
                        .withBody(new Gson().toJson(WeatherDto.builder()
                                .temperature(20)
                                .airPressure(100.5)
                                .humidity(30.5)
                                .wind(Wind.W)
                                .isSafe(true)
                                .build()))
                )
        );
        //expect
        mockMvc.perform(get("/mobile/weather"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.temperature", equalTo(20)))
                .andExpect(jsonPath("$.airPressure", equalTo(100.5)))
                .andExpect(jsonPath("$.humidity", equalTo(30.5)))
                .andExpect(jsonPath("$.wind", equalTo("W")));
    }

}