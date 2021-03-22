package com.max.pact.weather.contract;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Request;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class WeatherUnavailableTest extends AbstractPactTest {

    @Pact(provider = "Weather Service", consumer = "Mobile App")
    public RequestResponsePact shouldRespondWith404WhenWeatherIsUnavailable(PactDslWithProvider builder) {
        return builder
                .given("Weather for today is unavailable")
                .uponReceiving("A request for weather with no request parameter")
                .path("/weather/status")
                .method("GET")
                .willRespondWith()
                .status(404)
                .body(new PactDslJsonBody().includesStr("message", "No weather available"))
                .toPact();
    }

    @Test
    void shouldRespondWith404WhenWeatherIsUnavailable(MockServer mockServer) throws IOException {
        HttpResponse httpResponse = Request.Get(mockServer.getUrl() + "/weather/status").execute().returnResponse();
        assertThat(httpResponse.getStatusLine().getStatusCode()).isEqualTo(404);
        assertThat(IOUtils.toString(httpResponse.getEntity().getContent())).contains("No weather available");
    }
}
