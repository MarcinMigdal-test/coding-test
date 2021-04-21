package com.tingco.codechallenge.elevator.util;

import com.google.common.base.Preconditions;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class SystemPropertyValidator {

    private final static Logger LOG = LoggerFactory
        .getLogger(SystemPropertyValidator.class.getCanonicalName());

    private SystemPropertyValidator() {
    }

    public static void validateProperty(String propertyName,
        Optional<String> optionalPropertyValue) {
        if (optionalPropertyValue.isPresent()) {
            String presentValue = optionalPropertyValue.get();
            LOG.info(String
                .format("Found custom property %s with value %s", propertyName, presentValue));
            int value = Integer.parseInt(presentValue);
            Preconditions.checkArgument(value > 0);
        }
    }

    public static Optional<String> readPropertyValue(String propertyName) {
        return Optional.ofNullable(System.getProperty(propertyName));
    }

}
