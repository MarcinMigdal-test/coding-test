package com.tingco.codechallenge.elevator.impl;

import com.tingco.codechallenge.elevator.api.Elevator;
import java.util.ArrayList;
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
            .forEach(value -> elevatorList.add(new ElevatorImpl(value)));
        return elevatorList;
    }

    public static Map<Integer,Elevator> getElevatorsAsMap(int elevatorsNumber) {
        Map<Integer,Elevator> elevatorSet = new ConcurrentHashMap();
        IntStream.rangeClosed(ELEVATOR_FIRST_INDEX, elevatorsNumber)
            .forEach(value -> elevatorSet.putIfAbsent(  Integer.valueOf(value),  new ElevatorImpl(value)));
        return elevatorSet;
    }

}