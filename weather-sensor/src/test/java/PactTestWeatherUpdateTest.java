import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import com.google.gson.Gson;
import com.max.pact.pactweather.WeatherDto;
import com.max.pact.pactweather.Wind;
import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class PactTestWeatherUpdateTest extends AbstractPactTest {

    @Pact(provider = "Weather Service", consumer = "Weather Sensor")
    public RequestResponsePact shouldAcceptWeatherUpdate(PactDslWithProvider builder) {
        return builder
//                .given("Weather for today is good")
                .uponReceiving("A request to update weather")
                .path("/weather")
                .body(new PactDslJsonBody()
                        .integerType("temperature", 25)
                        .decimalType("airPressure", 88.0)
                        .decimalType("humidity", 25.5)
                        .stringValue("wind", "NE")
                        .booleanValue("isSafe", true))
                .method("POST")
                .willRespondWith()
                .status(200)
                .toPact();
    }

    @Test
    void shouldAcceptWeatherUpdate(MockServer mockServer) throws IOException {
        HttpResponse httpResponse = Request.Post(mockServer.getUrl() + "/weather")
                .bodyString(new Gson().toJson(defaultWeather()), ContentType.APPLICATION_JSON)
                .execute()
                .returnResponse();
        assertThat(httpResponse.getStatusLine().getStatusCode()).isEqualTo(200);
    }

    private static WeatherDto defaultWeather() {
        return WeatherDto.builder()
                .temperature(25)
                .airPressure(88.0)
                .wind(Wind.NE)
                .humidity(25.5)
                .isSafe(true)
                .build();
    }
}
