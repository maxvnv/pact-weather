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
    private UrlOverride urlOverride;

    public <T> T getFor(String urlPath, Class<T> clazz) {
        return restTemplate.getForEntity(format("%s%s", urlOverride.override(getUrl()), urlPath), clazz)
                .getBody();
    }

    abstract String getUrl();
}
