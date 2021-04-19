package com.tingco.codechallenge.elevator.impl;

import com.tingco.codechallenge.elevator.api.Elevator;
import static org.junit.jupiter.api.Assertions.*;

import com.tingco.codechallenge.elevator.config.FloorsElevatorsConfig;
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
        assertEquals(FloorsElevatorsConfig.FLOOR_0, testedObject.currentFloor());
        testedObject.requestElevatorMovement(FloorsElevatorsConfig.FLOOR_5);
        testedObject.run();
        assertEquals(FloorsElevatorsConfig.FLOOR_5, testedObject.currentFloor());

        testedObject.requestElevatorMovement(FloorsElevatorsConfig.FLOOR_3);
        testedObject.run();
        assertEquals(FloorsElevatorsConfig.FLOOR_3, testedObject.currentFloor());

        testedObject.requestElevatorMovement(FloorsElevatorsConfig.FLOOR_9);
        testedObject.run();
        assertEquals(FloorsElevatorsConfig.FLOOR_9, testedObject.currentFloor());

        testedObject.requestElevatorMovement(FloorsElevatorsConfig.FLOOR_0);
        testedObject.run();
        assertEquals(FloorsElevatorsConfig.FLOOR_0, testedObject.currentFloor());
    }

    @Test
    void requestElevatorMovement_parallel_requests(){
        assertEquals(FloorsElevatorsConfig.FLOOR_0, testedObject.currentFloor());
        testedObject.requestElevatorMovement(FloorsElevatorsConfig.FLOOR_5);
        testedObject.run();
        assertEquals(FloorsElevatorsConfig.FLOOR_5, testedObject.currentFloor());

        testedObject.requestElevatorMovement(FloorsElevatorsConfig.FLOOR_3);
        testedObject.run();
        assertEquals(FloorsElevatorsConfig.FLOOR_3, testedObject.currentFloor());

        testedObject.requestElevatorMovement(FloorsElevatorsConfig.FLOOR_9);
        testedObject.requestElevatorMovement(FloorsElevatorsConfig.FLOOR_5);
        testedObject.requestElevatorMovement(FloorsElevatorsConfig.FLOOR_2);
        testedObject.requestElevatorMovement(FloorsElevatorsConfig.FLOOR_1);
        testedObject.run();
        assertEquals(FloorsElevatorsConfig.FLOOR_9, testedObject.currentFloor());
        assertEquals(0, testedObject.floorsCheck().size());

        testedObject.requestElevatorMovement(FloorsElevatorsConfig.FLOOR_7);
        testedObject.run();
        assertEquals(FloorsElevatorsConfig.FLOOR_7, testedObject.currentFloor());

        testedObject.requestElevatorMovement(FloorsElevatorsConfig.FLOOR_8);
        testedObject.requestElevatorMovement(FloorsElevatorsConfig.FLOOR_4);
        testedObject.requestElevatorMovement(FloorsElevatorsConfig.FLOOR_2);
        testedObject.run();
        assertEquals(FloorsElevatorsConfig.FLOOR_2, testedObject.currentFloor());
        assertEquals(0, testedObject.floorsCheck().size());
    }
}