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

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class WeatherForDefaultDateTest extends AbstractPactTest {

    @Pact(provider = "Weather Service", consumer = "Mobile App")
    public RequestResponsePact shouldRespondWhenQueriedForDefaultDate(PactDslWithProvider builder) {
        return builder
                .given("Weather for today is safe")
                .uponReceiving("A request for weather with no request parameter")
                .path("/weather")
                .method("GET")
                .willRespondWith()
                .status(200)
                .body(new PactDslJsonBody()
                        .integerType("temperature")
                        .decimalType("airPressure")
                        .decimalType("humidity")
                        .stringMatcher("wind", "(N)|(S)|(W)|(E)|(NE)|(NW)|(SE)|(SW)")
                        .booleanType("isSafe")
                        .stringValue("developmentField", "oi"))
                .toPact();
    }

    @Test
    void shouldRespondWhenQueriedForDefaultDate(MockServer mockServer) throws IOException {
        HttpResponse httpResponse = Request.Get(mockServer.getUrl() + "/weather").execute().returnResponse();
        assertThat(httpResponse.getStatusLine().getStatusCode()).isEqualTo(200);
    }
}
