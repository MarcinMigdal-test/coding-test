package com.tingco.codechallenge.elevator.impl;

import com.tingco.codechallenge.elevator.api.Elevator;
import com.tingco.codechallenge.elevator.config.TestConfig;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ElevatorFactoryTest {

    @Test
    void create() {
        //when
        List<Elevator> elevatorList = ElevatorFactory.getElevatorsAsList(TestConfig.ELEVATORS_AMOUNT_1);
        //then
        Assertions.assertEquals(TestConfig.ELEVATORS_AMOUNT_1, elevatorList.size());
    }
}