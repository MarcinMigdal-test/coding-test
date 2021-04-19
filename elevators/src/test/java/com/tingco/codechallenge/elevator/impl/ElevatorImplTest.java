package com.tingco.codechallenge.elevator.impl;

import com.tingco.codechallenge.elevator.api.Elevator;
import static org.junit.jupiter.api.Assertions.*;

import com.tingco.codechallenge.elevator.config.TestConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ElevatorImplTest {

    private Elevator testedObject;

    @BeforeEach
    void create(){
        testedObject = new ElevatorImpl(1);
    }

    @Test
    void requestElevatorMovement_one_by_one(){
        assertEquals(TestConfig.FLOOR_0, testedObject.currentFloor());
        testedObject.requestElevatorMovement(TestConfig.FLOOR_5);
        testedObject.run();
        assertEquals(TestConfig.FLOOR_5, testedObject.currentFloor());

        testedObject.requestElevatorMovement(TestConfig.FLOOR_3);
        testedObject.run();
        assertEquals(TestConfig.FLOOR_3, testedObject.currentFloor());

        testedObject.requestElevatorMovement(TestConfig.FLOOR_9);
        testedObject.run();
        assertEquals(TestConfig.FLOOR_9, testedObject.currentFloor());

        testedObject.requestElevatorMovement(TestConfig.FLOOR_0);
        testedObject.run();
        assertEquals(TestConfig.FLOOR_0, testedObject.currentFloor());
    }

    @Test
    void requestElevatorMovement_parallel_requests(){
        assertEquals(TestConfig.FLOOR_0, testedObject.currentFloor());
        testedObject.requestElevatorMovement(TestConfig.FLOOR_5);
        testedObject.run();
        assertEquals(TestConfig.FLOOR_5, testedObject.currentFloor());

        testedObject.requestElevatorMovement(TestConfig.FLOOR_3);
        testedObject.run();
        assertEquals(TestConfig.FLOOR_3, testedObject.currentFloor());

        testedObject.requestElevatorMovement(TestConfig.FLOOR_9);
        testedObject.requestElevatorMovement(TestConfig.FLOOR_5);
        testedObject.requestElevatorMovement(TestConfig.FLOOR_2);
        testedObject.requestElevatorMovement(TestConfig.FLOOR_1);
        testedObject.run();
        assertEquals(TestConfig.FLOOR_9, testedObject.currentFloor());
        assertEquals(0, testedObject.floorsCheck().size());

        testedObject.requestElevatorMovement(TestConfig.FLOOR_7);
        testedObject.run();
        assertEquals(TestConfig.FLOOR_7, testedObject.currentFloor());

        testedObject.requestElevatorMovement(TestConfig.FLOOR_8);
        testedObject.requestElevatorMovement(TestConfig.FLOOR_4);
        testedObject.requestElevatorMovement(TestConfig.FLOOR_2);
        testedObject.run();
        assertEquals(TestConfig.FLOOR_2, testedObject.currentFloor());
        assertEquals(0, testedObject.floorsCheck().size());
    }
}