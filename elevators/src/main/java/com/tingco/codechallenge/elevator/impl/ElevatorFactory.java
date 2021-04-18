package com.tingco.codechallenge.elevator.impl;

import com.tingco.codechallenge.elevator.api.Elevator;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public final class ElevatorFactory {

    private final static int ELEVATOR_FIRST_INDEX = 1;

    private ElevatorFactory() {
    }

    public static List<Elevator> getElevators(int elevatorsNumber) {
        List<Elevator> elevatorList = new ArrayList<>();
        IntStream.rangeClosed(ELEVATOR_FIRST_INDEX, elevatorsNumber)
            .forEach(value -> elevatorList.add(new ElevatorImpl(value)));
        return elevatorList;
    }
}