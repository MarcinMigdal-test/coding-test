package com.tingco.codechallenge.elevator.impl;

import com.tingco.codechallenge.elevator.api.Elevator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.IntStream;

public final class ElevatorFactory {

    private final static int ELEVATOR_FIRST_INDEX = 1;

    private ElevatorFactory() {
    }

    public static List<Elevator> getElevatorsAsList(int elevatorsNumber) {
        List<Elevator> elevatorList = new ArrayList<>();
        IntStream.rangeClosed(ELEVATOR_FIRST_INDEX, elevatorsNumber)
            .forEach(value -> elevatorList.add(getElevator(value)));
        return elevatorList;
    }

    public static Elevator getElevator(int elevatorId){
        return new ElevatorImpl(elevatorId);
    }

    public static Map<Integer,Elevator> getElevatorsAsMap(int elevatorsNumber) {
        Map<Integer,Elevator> elevatorSet = new HashMap<>();
        IntStream.rangeClosed(ELEVATOR_FIRST_INDEX, elevatorsNumber)
            .forEach(value -> elevatorSet.putIfAbsent(value,  new ElevatorImpl(value)));
        return elevatorSet;
    }

}