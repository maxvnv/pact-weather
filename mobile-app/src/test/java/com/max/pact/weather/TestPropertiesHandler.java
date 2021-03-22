package com.max.pact.weather;

import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.stereotype.Component;

import static org.springframework.test.context.support.TestPropertySourceUtils.addInlinedPropertiesToEnvironment;

@Deprecated
@Component
public class TestPropertiesHandler {

    private ConfigurableEnvironment environment;

    public TestPropertiesHandler(ConfigurableEnvironment environment) {
        this.environment = environment;
    }

    void overrideProperty(String key, String value) {
        addInlinedPropertiesToEnvironment(environment, String.format("%s=%s", key, value));
    }
}
