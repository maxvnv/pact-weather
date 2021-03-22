package com.max.pact.weather;

import com.max.pact.weather.wiremock.WireMockBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

@TestConfiguration
public class SpringTestConfig {

    @Autowired
    private WireMockBean wireMockBean;

    @Bean
    @Primary
    UrlOverride testUrlOverride() {
        return original -> "http://localhost:" + wireMockBean.getWireMockPort();
    }
}
