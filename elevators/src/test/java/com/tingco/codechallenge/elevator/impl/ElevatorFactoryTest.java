package com.tingco.codechallenge.elevator.impl;

import static com.tingco.codechallenge.elevator.config.TestConfig.ELEVATORS_AMOUNT_1;
import static com.tingco.codechallenge.elevator.config.TestConfig.ELEVATOR_MOVEMEMENT_INTERVAL;
import static com.tingco.codechallenge.elevator.config.TestConfig.ELEVATOR_STOP_INTERVAL;

import com.tingco.codechallenge.elevator.api.Elevator;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ElevatorFactoryTest {

    @Test
    void create() {
        //when
        List<Elevator> elevatorList = ElevatorFactory.getElevatorsAsList(ELEVATORS_AMOUNT_1,ELEVATOR_MOVEMEMENT_INTERVAL,ELEVATOR_STOP_INTERVAL);
        //then
        Assertions.assertEquals(ELEVATORS_AMOUNT_1, elevatorList.size());
    }
}