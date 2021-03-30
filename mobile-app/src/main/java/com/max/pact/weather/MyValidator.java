package com.max.pact.weather;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.util.Set;

@Component
public class MyValidator {

    @Autowired
    private Validator validator;

    public <T> void validateObject(T object) {
        Set<ConstraintViolation<T>> constraints = validator.validate(object);
        if (!constraints.isEmpty()) {
            throw new ConstraintViolationException(constraints);
        }
    }


}
