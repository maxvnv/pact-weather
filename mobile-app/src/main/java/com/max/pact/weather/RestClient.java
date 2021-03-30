package com.max.pact.weather;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import static java.lang.String.format;

@Component
public abstract class RestClient {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private MyValidator validator;
    @Autowired
    private UrlOverride urlOverride;

    public <T> T getFor(String urlPath, Class<T> clazz) {
        T response = restTemplate.getForEntity(format("%s%s", urlOverride.override(getUrl()), urlPath), clazz)
                .getBody();
        validator.validateObject(response);
        return response;
    }

    abstract String getUrl();
}
