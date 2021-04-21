package com.tingco.codechallenge.elevator.util;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Optional;
import org.junit.jupiter.api.Test;

public class SystemPropertyValidatorTest {

    private String propertyName = "someProperty";

    @Test
    void canPositiveIntegerPropertyBeValidated() {
        //given
        Optional<String> propertyValue = Optional.of("5");
        //when
        SystemPropertyValidator.validateProperty(propertyName, propertyValue);
    }

    @Test
    void canNegativeIntegerPropertyBeValidated() {
        //given
        Optional<String> propertyValue = Optional.of("-5");
        //when
        assertThrows(IllegalArgumentException.class, () -> SystemPropertyValidator.validateProperty(propertyName, propertyValue));
    }

    @Test
    void canStringPropertyBeValidated() {
        //given
        Optional<String> propertyValue = Optional.of("true");
        //when
        assertThrows(IllegalArgumentException.class, () ->  SystemPropertyValidator.validateProperty(propertyName, propertyValue));
    }
}