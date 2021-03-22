package com.max.pact.weather.integration;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.max.pact.weather.SpringTestConfig;
import com.max.pact.weather.WeatherServiceDao;
import com.max.pact.weather.wiremock.WireMockExtension;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
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
    void test() throws Exception {
        // given
        stubFor(WireMock.get(urlEqualTo("/weather/status"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", APPLICATION_JSON_VALUE)
                        .withStatus(200)
                        .withBody("{\"status\" : \"good!\"}"))
        );
        //expect
        mockMvc.perform(get("/mobile/weather/status"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", Matchers.equalTo("good!")));
    }

}