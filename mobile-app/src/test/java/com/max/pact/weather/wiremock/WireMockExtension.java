package com.max.pact.weather.wiremock;


import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.stereotype.Component;

@Component
public class WireMockExtension implements AfterTestExecutionCallback {

    private WireMockBean wireMockBean;

    public WireMockExtension(WireMockBean wireMockBean) {
        this.wireMockBean = wireMockBean;
    }

    @Override
    public void afterTestExecution(ExtensionContext extensionContext) {
        wireMockBean.stop();
    }
}
