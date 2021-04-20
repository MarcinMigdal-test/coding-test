package com.tingco.codechallenge.elevator.impl;

import com.tingco.codechallenge.elevator.api.Elevator;
import com.tingco.codechallenge.elevator.api.Elevator.Direction;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ElevatorsFilter {

    public List<Elevator> getElevatorsGoingWithDirectionTowardsFloor(List<Elevator> elevators, Direction direction, int floor){
        //List<Elevator> elevatorsInMove = elevators.parallelStream().filter(predElevatorGoingDown).filter(preElevatorGoingUp).collect(Collectors.toList());
       return  elevators.parallelStream().filter(getPredicate(direction,floor)).collect(Collectors.toList());
    }

    private Predicate<Elevator> getPredicate(Direction direction, int floor){
        Predicate<Elevator> predicate = null;
        switch (direction) {
            case UP -> predicate = (elevator) -> elevator.currentFloor() < floor && elevator.getDirection().equals(direction);
            case DOWN -> predicate = (elevator) -> elevator.currentFloor() > floor && elevator.getDirection().equals(direction);
            case NONE -> predicate = (elevator) -> elevator.getDirection().equals(direction);
            default -> { throw new IllegalArgumentException("Direction not selected for elevator predicate.");}
            }
            return predicate;
    }
}
