package com.tingco.codechallenge.elevator.util;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DistanceCalculatorTest {

    private static Map<Integer, Integer> distanceByElevatorId = new HashMap<>();

    @BeforeEach
    void init() {
        distanceByElevatorId.clear();
    }

    @Test
    void findElevatorIdWithLowestDistanceWhichIs2() {
        //given
        distanceByElevatorId.put(5, 1);
        distanceByElevatorId.put(2, 9);
        distanceByElevatorId.put(9, 3);
        distanceByElevatorId.put(7, 4);
        //when
        int lowestDistance = DistanceCalculator.findElevatorIdWithShortestDistance(
            distanceByElevatorId);
        //then
        Assertions
            .assertEquals(9, lowestDistance);
    }

    @Test
    void findElevatorIdWithLowestDistanceWhichIs3() {
        //given
        distanceByElevatorId.put(11, 1);
        distanceByElevatorId.put(4, 2);
        distanceByElevatorId.put(7, 3);
        distanceByElevatorId.put(3, 123);
        //when
        int lowestDistance = DistanceCalculator.findElevatorIdWithShortestDistance(
            distanceByElevatorId);
        //then
        Assertions
            .assertEquals(lowestDistance, DistanceCalculator.findElevatorIdWithShortestDistance(
                distanceByElevatorId));
    }
}