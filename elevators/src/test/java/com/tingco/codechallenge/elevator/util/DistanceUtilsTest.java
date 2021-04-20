package com.tingco.codechallenge.elevator.util;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DistanceUtilsTest {

    private static Map<Integer,Integer> distanceByID = new HashMap<>();

    @BeforeEach
    void setUp(){
        distanceByID.clear();
    }

    @Test
    void findSmallestDistance2() {
        distanceByID.put(5,1);
        distanceByID.put(2,9);
        distanceByID.put(9,3);
        distanceByID.put(7,4);
        Assertions.assertEquals(9, DistanceUtils.findElevatorIdWithShortestDistance(distanceByID));
    }

    @Test
    void findSmallestDistance3(){
        distanceByID.put(11,1);
        distanceByID.put(4,2);
        distanceByID.put(7,3);
        distanceByID.put(3,4);
        Assertions.assertEquals(4, DistanceUtils.findElevatorIdWithShortestDistance(distanceByID));
    }

}
