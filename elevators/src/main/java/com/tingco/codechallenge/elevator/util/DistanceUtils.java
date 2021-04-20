package com.tingco.codechallenge.elevator.util;


import java.util.Map;

public final class DistanceUtils {

    private DistanceUtils(){

    }

    public static Integer findElevatorIdWithShortestDistance(Map<Integer,Integer> distanceById){
        Integer shortestDistance = distanceById.keySet().stream().reduce((integer, integer2) -> integer.intValue()<integer2.intValue() ? integer:integer2).get();
        return distanceById.get(shortestDistance);
    }

}
