package com.max.pact.weather;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.client.RestTemplate;

import javax.validation.Validator;

@SpringBootApplication
public class MobileAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(MobileAppApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Bean
    public UrlOverride urlOverride() {
        return original -> original;
    }

    @Bean
    public Validator validator() {
        return new LocalValidatorFactoryBean();
    }
}
