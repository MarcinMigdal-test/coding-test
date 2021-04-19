package com.tingco.codechallenge.elevator.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.tingco.codechallenge.elevator.api.Elevator;
import com.tingco.codechallenge.elevator.config.TestConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ElevatorImplTest {

    private Elevator elevator;

    @BeforeEach
    void create() {
        elevator = new ElevatorImpl(1);
    }

    @Test
    void canElevatorMoveFromFloor0ToFloor5() {
        elevator.requestElevatorMovement(TestConfig.FLOOR_5);
        elevator.run();

        assertEquals(TestConfig.FLOOR_5, elevator.currentFloor());
    }


    //TODO rozdzielic na poszczegolne testy -> ustawiac current floor ()
    @Test
    void requestElevatorMovement_moveWithInterimFloors() {

        elevator.requestElevatorMovement(TestConfig.FLOOR_3);
        elevator.run();
        assertEquals(TestConfig.FLOOR_3, elevator.currentFloor());

        elevator.requestElevatorMovement(TestConfig.FLOOR_9);
        elevator.requestElevatorMovement(TestConfig.FLOOR_5);
        elevator.requestElevatorMovement(TestConfig.FLOOR_2);
        elevator.requestElevatorMovement(TestConfig.FLOOR_1);
        elevator.run();
        assertEquals(TestConfig.FLOOR_9, elevator.currentFloor());
        assertEquals(0, elevator.getFloorsToBeVisited().size());

        elevator.requestElevatorMovement(TestConfig.FLOOR_7);
        elevator.run();
        assertEquals(TestConfig.FLOOR_7, elevator.currentFloor());

        elevator.requestElevatorMovement(TestConfig.FLOOR_8);
        elevator.requestElevatorMovement(TestConfig.FLOOR_4);
        elevator.requestElevatorMovement(TestConfig.FLOOR_2);
        elevator.run();
        assertEquals(TestConfig.FLOOR_2, elevator.currentFloor());
        assertEquals(0, elevator.getFloorsToBeVisited().size());
    }
}