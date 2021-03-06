package com.max.pact.weather.contract;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Request;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class DevelopmentWeatherForSpecificDateTest extends AbstractPactTest {

    @Pact(provider = "Weather Service", consumer = "Mobile App")
    public RequestResponsePact shouldRespondWhenQueriedForSpecificDate(PactDslWithProvider builder) {
        return builder
                .given("Weather for 2021-03-21 is not safe")
                .uponReceiving("A request for weather with explicit request parameter")
                .path("/weather")
                .query("date=2021-03-21")
                .method("GET")
                .willRespondWith()
                .status(200)
                .body(new PactDslJsonBody()
                        .integerType("temperature")
                        .decimalType("airPressure")
                        .decimalType("humidity")
                        .stringMatcher("wind", "(N)|(S)|(W)|(E)|(NE)|(NW)|(SE)|(SW)")
                        .booleanValue("isSafe", false))
                .toPact();
    }

    @Test
    void shouldRespondWhenQueriedForSpecificDate(MockServer mockServer) throws IOException {
        HttpResponse httpResponse = Request.Get(mockServer.getUrl() + "/weather?date=2021%2D03%2D21").execute().returnResponse();
        assertThat(httpResponse.getStatusLine().getStatusCode()).isEqualTo(200);
    }
}
