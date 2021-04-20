package com.tingco.codechallenge.elevator.impl;

import static com.tingco.codechallenge.elevator.config.TestConfig.FLOOR_10;
import static com.tingco.codechallenge.elevator.config.TestConfig.FLOOR_2;
import static com.tingco.codechallenge.elevator.config.TestConfig.FLOOR_3;
import static com.tingco.codechallenge.elevator.config.TestConfig.FLOOR_5;
import static com.tingco.codechallenge.elevator.config.TestConfig.FLOOR_6;
import static com.tingco.codechallenge.elevator.config.TestConfig.FLOOR_7;
import static com.tingco.codechallenge.elevator.config.TestConfig.FLOOR_8;
import static com.tingco.codechallenge.elevator.config.TestConfig.FLOOR_9;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.tingco.codechallenge.elevator.api.Elevator;
import com.tingco.codechallenge.elevator.api.Elevator.Direction;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ElevatorsFilterTest {

    private ElevatorsFilter elevatorsFilter = new ElevatorsFilter();
    private static List<Elevator> elevators = new ArrayList<>();
    private Elevator elevator1, elevator2, elevator3, elevator4, elevator5, elevator6, elevator7;

    @BeforeEach
    void initTest() {
        //given
        elevators.clear();
        elevator1 = getElevatorAtFloor(1, Direction.UP, FLOOR_2);
        elevator2 = getElevatorAtFloor(2, Direction.UP, FLOOR_7);
        elevator3 = getElevatorAtFloor(3, Direction.DOWN, FLOOR_5);
        elevator4 = getElevatorAtFloor(3, Direction.DOWN, FLOOR_10);
        elevator5 = getElevatorAtFloor(2, Direction.DOWN, FLOOR_8);
        elevator6 = getElevatorAtFloor(2, Direction.UP, FLOOR_8);
        elevator7 = getElevatorAtFloor(2, Direction.NONE, FLOOR_6);
        elevators.addAll(
            List.of(elevator1, elevator2, elevator3, elevator4, elevator5, elevator6, elevator7));
    }

    @Test
    void countElevatorsGoingUpTowardsFloor2() {
        //when
        List<Elevator> result = elevatorsFilter
            .getElevatorsGoingWithDirectionTowardsFloor(elevators, Direction.UP, FLOOR_2);
        //then
        assertEquals(0, result.size());
    }

    @Test
    void countElevatorsGoingUpTowardsFloor5() {
        //when
        List<Elevator> result = elevatorsFilter
            .getElevatorsGoingWithDirectionTowardsFloor(elevators, Direction.UP, FLOOR_5);
        //then
        assertEquals(1, result.size());
        assertTrue(result.contains(elevator1));
    }

    @Test
    void countElevatorsGoingUpTowardsFloor9() {
        //when
        List<Elevator> result = elevatorsFilter
            .getElevatorsGoingWithDirectionTowardsFloor(elevators, Direction.UP, FLOOR_9);
        //then
        assertEquals(3, result.size());
        assertTrue(result.contains(elevator1));
        assertTrue(result.contains(elevator2));
        assertTrue(result.contains(elevator6));
    }

    @Test
    void countElevatorsGoingDownTowardsFloor10() {
        //when
        List<Elevator> result = elevatorsFilter
            .getElevatorsGoingWithDirectionTowardsFloor(elevators, Direction.DOWN, FLOOR_10);
        //then
        assertEquals(0, result.size());
    }

    @Test
    void countElevatorsGoingDownTowardsFloor7() {
        //when
        List<Elevator> result = elevatorsFilter
            .getElevatorsGoingWithDirectionTowardsFloor(elevators, Direction.DOWN, FLOOR_7);
        //then
        assertEquals(2, result.size());
        assertTrue(result.contains(elevator4));
        assertTrue(result.contains(elevator5));
    }

    @Test
    void countElevatorsGoingDownTowardsFloor3() {
        //when
        List<Elevator> result = elevatorsFilter
            .getElevatorsGoingWithDirectionTowardsFloor(elevators, Direction.DOWN, FLOOR_3);
        //then
        assertEquals(3, result.size());
        assertTrue(result.contains(elevator3));
        assertTrue(result.contains(elevator4));
        assertTrue(result.contains(elevator5));
    }

    @Test
    void countElevatorsStopped() {
        //when
        List<Elevator> result = elevatorsFilter
            .getStoppedElevators(elevators);
        //then
        assertEquals(1, result.size());
        assertTrue(result.contains(elevator7));
    }

    private Elevator getElevatorAtFloor(int elevatorId, Direction direction, int elevatorFloor) {
        Elevator elevator = ElevatorFactory.getElevator(elevatorId);
        elevator.setCurrentFloor(elevatorFloor);
        if (!direction.equals(Direction.NONE)) {
            elevator.requestElevatorMovement(elevatorFloor + 1);
        }
        elevator.setDirection(direction);
        return elevator;
    }
}