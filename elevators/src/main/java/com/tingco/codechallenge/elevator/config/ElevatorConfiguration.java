package com.tingco.codechallenge.elevator.config;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@Configuration
@EnableAutoConfiguration
@PropertySources({@PropertySource("classpath:application.properties")})
public class ElevatorConfiguration {

    @Value("${com.tingco.elevator.elevatorsNumber}")
    private int elevatorsNumber;

    public int getElevatorsNumber() {
        return elevatorsNumber;
    }

    @Value("${com.tingco.elevator.floorsNumber}")
    private int floorsNumber;

    public int getFloorsNumber() {
        return floorsNumber;
    }

    @Value("${com.tingco.elevator.movementInterval}")
    private int movementInterval;

    public int getElevatorMovemenentInterval() {
        return movementInterval;
    }

    @Value("${com.tingco.elevator.stopInterval}")
    private int stopInterval;

    public int getElevatorStopInterval() {
        return stopInterval;
    }


    /**
     * Create a default thread pool for your convenience.
     *
     * @return Executor thread pool
     */
    @Bean(destroyMethod = "shutdown")
    public Executor taskExecutor() {
        return Executors.newFixedThreadPool(elevatorsNumber);
    }

    /**
     * Create an event bus for your convenience.
     *
     * @return EventBus for async task execution
     */
    @Bean
    public EventBus eventBus() {
        return new AsyncEventBus(Executors.newCachedThreadPool());
    }

}
