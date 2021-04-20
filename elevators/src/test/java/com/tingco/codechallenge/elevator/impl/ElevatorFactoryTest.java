package com.tingco.codechallenge.elevator.impl;

import com.tingco.codechallenge.elevator.api.Elevator;
import static com.tingco.codechallenge.elevator.config.TestConfig.*;
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