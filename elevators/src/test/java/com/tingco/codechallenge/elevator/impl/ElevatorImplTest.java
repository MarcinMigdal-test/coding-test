package com.tingco.codechallenge.elevator.impl;

import com.tingco.codechallenge.elevator.api.Elevator;
import static org.junit.jupiter.api.Assertions.*;

import com.tingco.codechallenge.elevator.config.FloorsElevatorsConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ElevatorImplTest {

    private Elevator testedObject;

    @BeforeEach
    public void create(){
        testedObject = new ElevatorImpl(1);
    }

    @Test
    public void requestElevatorMovement_one_by_one(){
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
    public void requestElevatorMovement_parallel_requests(){
        assertEquals(FloorsElevatorsConfig.FLOOR_0, testedObject.currentFloor());
        testedObject.requestElevatorMovement(FloorsElevatorsConfig.FLOOR_5);
        testedObject.run();
        assertEquals(FloorsElevatorsConfig.FLOOR_5, testedObject.currentFloor());

        testedObject.requestElevatorMovement(FloorsElevatorsConfig.FLOOR_3);
        testedObject.run();
        assertEquals(FloorsElevatorsConfig.FLOOR_3, testedObject.currentFloor());

        testedObject.requestElevatorMovement(FloorsElevatorsConfig.FLOOR_9);
        testedObject.requestElevatorMovement(FloorsElevatorsConfig.FLOOR_2);
        testedObject.run();
        assertEquals(FloorsElevatorsConfig.FLOOR_9, testedObject.currentFloor());
        assertEquals(0, testedObject.floorsCheck().size());
    }


}