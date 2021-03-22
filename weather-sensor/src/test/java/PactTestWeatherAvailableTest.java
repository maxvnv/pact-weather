import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class PactTestWeatherAvailableTest extends AbstractPactTest {

    @Pact(provider = "Weather Service", consumer = "Weather Sensor")
    public RequestResponsePact shouldAcceptWeatherUpdate(PactDslWithProvider builder) {
        return builder
//                .given("Weather for today is good")
                .uponReceiving("A request to update weather")
                .path("/weather/status")
                .body(new JSONObject().put("status", "OK"))
                .method("POST")
                .willRespondWith()
                .status(200)
                .toPact();
    }

    @Test
    @PactTestFor(pactMethod = "shouldAcceptWeatherUpdate")
    void shouldAcceptWeatherUpdate(MockServer mockServer) throws IOException {
        HttpResponse httpResponse = Request.Post(mockServer.getUrl() + "/weather/status")
                .bodyString(new JSONObject().put("status", "OK").toString(), ContentType.APPLICATION_JSON)
                .execute()
                .returnResponse();
        assertThat(httpResponse.getStatusLine().getStatusCode()).isEqualTo(200);
    }
}
