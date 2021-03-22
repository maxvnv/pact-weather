package com.max.pact.weather.wiremock;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Component;

@Component
public class WireMockBean {

    private WireMockServer wireMockServer;

    public WireMockBean() {
        wireMockServer = new WireMockServer(WireMockConfiguration.options().port(randomPort()));
        wireMockServer.start();
        WireMock.configureFor("localhost", getWireMockPort());
    }

    private static int randomPort() {
        return RandomUtils.nextInt(8_000, 50_000);
    }

    public int getWireMockPort() {
        return wireMockServer.port();
    }

    public void stop() {
        wireMockServer.stop();
    }
}
