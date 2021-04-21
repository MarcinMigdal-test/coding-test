package com.tingco.codechallenge.elevator;

import com.tingco.codechallenge.elevator.util.SystemPropertyValidator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Preconfigured Spring Application boot class.
 *
 */
@SpringBootApplication
@ComponentScan(basePackages = { "com.tingco.codechallenge.elevator" })
public class ElevatorApplication {

    private static final String ELEVATORS_NUMBER_PROPERTY = "com.tingco.elevator.elevatorsNumber";
    private static final String FLOORS_NUMBER_PROPERTY = "com.tingco.elevator.floorsNumber";
    /**
     * Start method that will be invoked when starting the Spring context.
     *
     * @param args Not in use
     */
    public static void main(final String[] args) {
        SystemPropertyValidator.validateProperty(
            ELEVATORS_NUMBER_PROPERTY, SystemPropertyValidator.readPropertyValue(ELEVATORS_NUMBER_PROPERTY));
        SystemPropertyValidator.validateProperty(
            FLOORS_NUMBER_PROPERTY, SystemPropertyValidator.readPropertyValue(FLOORS_NUMBER_PROPERTY));
        SpringApplication.run(ElevatorApplication.class, args);
    }
}
