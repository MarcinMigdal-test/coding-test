package com.tingco.codechallenge.elevator.impl;

import static com.tingco.codechallenge.elevator.config.TestConfig.FLOOR_1;
import static com.tingco.codechallenge.elevator.config.TestConfig.FLOOR_10;
import static com.tingco.codechallenge.elevator.config.TestConfig.FLOOR_2;
import static com.tingco.codechallenge.elevator.config.TestConfig.FLOOR_3;
import static com.tingco.codechallenge.elevator.config.TestConfig.FLOOR_4;
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
import java.util.Optional;
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
        elevator1 = getElevatorAtFloorAndGoingToFloor(1, Direction.UP, FLOOR_2, FLOOR_2);
        elevator2 = getElevatorAtFloorAndGoingToFloor(2, Direction.UP, FLOOR_7, FLOOR_10);
        elevator3 = getElevatorAtFloorAndGoingToFloor(3, Direction.DOWN, FLOOR_5, FLOOR_1);
        elevator4 = getElevatorAtFloorAndGoingToFloor(4, Direction.DOWN, FLOOR_10, FLOOR_3);
        elevator5 = getElevatorAtFloorAndGoingToFloor(5, Direction.DOWN, FLOOR_8, FLOOR_4);
        elevator6 = getElevatorAtFloorAndGoingToFloor(6, Direction.UP, FLOOR_8, FLOOR_5);
        elevator7 = getElevatorAtFloorAndGoingToFloor(7, Direction.NONE, FLOOR_6, FLOOR_6);
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

    @Test
    void findNearestElevatorGoingDownAtFloor3() {
        //given
        List<Elevator> elevatorsTowardsFloor3Down = elevatorsFilter
            .getElevatorsGoingWithDirectionTowardsFloor(elevators, Direction.DOWN, FLOOR_3);
        //when
        Optional<Elevator> elevator = elevatorsFilter
            .getNearestElevatorToRequestedFloor(elevatorsTowardsFloor3Down, FLOOR_3);
        //then
        assertTrue(elevator.isPresent());
        assertEquals(elevator3.getId(), elevator.get().getId());
    }

    @Test
    void findNearestElevatorGoingDownAtFloor7() {
        //given
        List<Elevator> elevatorsTowardsFloor7Down = elevatorsFilter
            .getElevatorsGoingWithDirectionTowardsFloor(elevators, Direction.DOWN, FLOOR_7);
        //when
        Optional<Elevator> elevator = elevatorsFilter
            .getNearestElevatorToRequestedFloor(elevatorsTowardsFloor7Down, FLOOR_7);
        //then
        assertTrue(elevator.isPresent());
        assertEquals(elevator5.getId(), elevator.get().getId());
    }


    @Test
    void findNearestElevatorGoingDownAtFloor9() {
        //given
        List<Elevator> elevatorsTowardsFloor9Down = elevatorsFilter
            .getElevatorsGoingWithDirectionTowardsFloor(elevators, Direction.DOWN, FLOOR_9);
        //when
        Optional<Elevator> elevator = elevatorsFilter
            .getNearestElevatorToRequestedFloor(elevatorsTowardsFloor9Down, FLOOR_9);
        //then
        assertTrue(elevator.isPresent());
        assertEquals(elevator4.getId(), elevator.get().getId());
    }

    @Test
    void findNearestElevatorGoingUpAtFloor9() {
        //given
        List<Elevator> elevatorsTowardsFloor9Up = elevatorsFilter
            .getElevatorsGoingWithDirectionTowardsFloor(elevators, Direction.UP, FLOOR_9);
        //when
        Optional<Elevator> elevator = elevatorsFilter
            .getNearestElevatorToRequestedFloor(elevatorsTowardsFloor9Up, FLOOR_9);
        //then
        assertTrue(elevator.isPresent());
        assertEquals(elevator6.getId(), elevator.get().getId());
    }

    @Test
    void findNearestElevatorGoingUpAtFloor6() {
        //given
        List<Elevator> elevatorsTowardsFloor6Up = elevatorsFilter
            .getElevatorsGoingWithDirectionTowardsFloor(elevators, Direction.UP, FLOOR_6);
        //when
        Optional<Elevator> elevator = elevatorsFilter
            .getNearestElevatorToRequestedFloor(elevatorsTowardsFloor6Up, FLOOR_6);
        //then
        assertTrue(elevator.isPresent());
        assertEquals(elevator1.getId(), elevator.get().getId());
    }

    private Elevator getElevatorAtFloorAndGoingToFloor(int elevatorId, Direction direction,
        int elevatorFloor, int destinationFloor) {
        Elevator elevator = ElevatorFactory.getElevator(elevatorId);
        elevator.setCurrentFloor(elevatorFloor);
        if (!direction.equals(Direction.NONE)) {
            elevator.requestElevatorMovement(destinationFloor);
        }
        elevator.setDirection(direction);
        return elevator;
    }
}