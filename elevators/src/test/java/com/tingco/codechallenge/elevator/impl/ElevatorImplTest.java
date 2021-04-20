package com.tingco.codechallenge.elevator.impl;

import static com.tingco.codechallenge.elevator.config.TestConfig.FLOOR_1;
import static com.tingco.codechallenge.elevator.config.TestConfig.FLOOR_10;
import static com.tingco.codechallenge.elevator.config.TestConfig.FLOOR_2;
import static com.tingco.codechallenge.elevator.config.TestConfig.FLOOR_3;
import static com.tingco.codechallenge.elevator.config.TestConfig.FLOOR_5;
import static com.tingco.codechallenge.elevator.config.TestConfig.FLOOR_6;
import static com.tingco.codechallenge.elevator.config.TestConfig.FLOOR_7;
import static com.tingco.codechallenge.elevator.config.TestConfig.FLOOR_9;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.tingco.codechallenge.elevator.api.Elevator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ElevatorImplTest {

    private final int elevatorMovementInterval = 1;
    private final int elevatorStopInterval = 1;
    private final int elevatorId = 1;
    private Elevator elevator;

    @BeforeEach
    void create() {
        elevator = new ElevatorImpl(elevatorId,elevatorMovementInterval,elevatorStopInterval);
    }

    @Test
    void canElevatorMoveFromFloor0toFloor2() {
        //given
        elevator.requestElevatorMovement(FLOOR_2);
        //when
        elevator.run();
        //then
        assertEquals(FLOOR_2, elevator.currentFloor());
    }

    @Test
    void canElevatorMoveFromFloor0toFloor5() {
        //given
        elevator.requestElevatorMovement(FLOOR_5);
        //when
        elevator.run();
        //then
        assertEquals(FLOOR_5, elevator.currentFloor());
    }

    @Test
    void canElevatorMoveFormFloo3toFllor7() {
        //given
        elevator.setCurrentFloor(FLOOR_3);
        elevator.requestElevatorMovement(FLOOR_7);
        //when
        elevator.run();
        //then
        assertEquals(FLOOR_7, elevator.currentFloor());
    }

    @Test
    void canElevatorMoveFormFloor7toFllor3() {
        //given
        elevator.setCurrentFloor(FLOOR_7);
        elevator.requestElevatorMovement(FLOOR_3);
        //when
        elevator.run();
        //then
        assertEquals(FLOOR_3, elevator.currentFloor());
    }

    @Test
    void canElevatorMoveWithInterimFloors() {
        //given
        elevator.setCurrentFloor(FLOOR_7);
        elevator.requestElevatorMovement(FLOOR_5);
        elevator.requestElevatorMovement(FLOOR_3);
        elevator.requestElevatorMovement(FLOOR_1);
        //when
        elevator.run();
        //then
        assertEquals(FLOOR_1, elevator.currentFloor());
    }

    @Test
    void canElevatorMoveFromFloor5toFloor3AndThenToFloor10() {
        //given
        elevator.setCurrentFloor(FLOOR_5);
        elevator.requestElevatorMovement(FLOOR_3);
        elevator.requestElevatorMovement(FLOOR_10);
        //when
        elevator.run();
        //then
        assertEquals(FLOOR_10, elevator.currentFloor());
    }

    @Test
    void canElevatorMoveFromFloor7toFloor10AndThenToFloor5() {
        //given
        elevator.setCurrentFloor(FLOOR_7);
        elevator.requestElevatorMovement(FLOOR_10);
        elevator.requestElevatorMovement(FLOOR_5);
        //when
        elevator.run();
        //then
        assertEquals(FLOOR_10, elevator.currentFloor());
    }

    @Test
    void canElevatorMoveFromFloor7toFloor5AndThenToFloor9() {
        //given
        elevator.setCurrentFloor(FLOOR_7);
        elevator.requestElevatorMovement(FLOOR_9);
        elevator.requestElevatorMovement(FLOOR_5);
        //when
        elevator.run();
        //then
        assertEquals(FLOOR_9, elevator.currentFloor());
    }

    @Test
    void canElevatorMoveFromFloor6toFloor3AndThenToFloor9() {
        //given
        elevator.setCurrentFloor(FLOOR_6);
        elevator.requestElevatorMovement(FLOOR_9);
        elevator.requestElevatorMovement(FLOOR_3);
        //when
        elevator.run();
        //then
        assertEquals(FLOOR_9, elevator.currentFloor());
    }
}