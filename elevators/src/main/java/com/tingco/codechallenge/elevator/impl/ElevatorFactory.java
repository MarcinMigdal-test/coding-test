package com.tingco.codechallenge.elevator.impl;

import com.tingco.codechallenge.elevator.api.Elevator;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public final class ElevatorFactory {

    private ElevatorFactory(){
    }

    public static List<Elevator> getElevators(int number){
        List<Elevator> elevatorList = new ArrayList<>();
        IntStream.of(number).forEach(value -> {
            elevatorList.add(new ElevatorImpl(value));
        });
        return elevatorList;
    }
}