package com.tingco.codechallenge.elevator.impl;

import com.tingco.codechallenge.elevator.api.Elevator;
import com.tingco.codechallenge.elevator.api.Elevator.Direction;
import com.tingco.codechallenge.elevator.util.DistanceCalculator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ElevatorsFilter {

    private final static Logger LOG = LoggerFactory
        .getLogger(ElevatorsFilter.class.getCanonicalName());

    public List<Elevator> getElevatorsGoingWithDirectionTowardsFloor(List<Elevator> elevators,
        Direction direction, int floor) {
        return elevators.parallelStream().filter(getPredicate(direction, floor))
            .collect(Collectors.toList());
    }

    public List<Elevator> getStoppedElevators(List<Elevator> elevators) {
        return elevators.parallelStream().filter(elevator -> !elevator.isBusy())
            .collect(Collectors.toList());
    }

    /**
     * This is support for a case when all elevetors are in maintenance mode
     * not implemented such situation yet
     * */
    public Optional<Elevator> getNearestElevatorToRequestedFloor(List<Elevator> elevators, int floor) {
        Map<Integer, Elevator> elevatorIdAndElevator = new HashMap<>();
        Map<Integer, Integer> distanceAndElevatorId = new HashMap<>();
        elevators.forEach(elevator -> {
            distanceAndElevatorId
                .put(Math.abs(elevator.currentFloor() - floor),
                    elevator.getIdentifier());
        });
        elevators.forEach(elevator -> {
            elevatorIdAndElevator.put(elevator.getId(), elevator);
        });
        Integer elevatorIdWithShortestDistance = DistanceCalculator
            .findElevatorIdWithShortestDistance(distanceAndElevatorId);
        return Optional.ofNullable(elevatorIdAndElevator.get(elevatorIdWithShortestDistance));
    }

    private Predicate<Elevator> getPredicate(Direction direction, int floor) {
        Predicate<Elevator> predicate;
        switch (direction) {
            case UP -> predicate = (elevator) -> elevator.currentFloor() < floor && elevator
                .getDirection().equals(direction);
            case DOWN -> predicate = (elevator) -> elevator.currentFloor() > floor && elevator
                .getDirection().equals(direction);
            default -> throw new IllegalArgumentException(
                "Direction not selected for elevator predicate.");
        }
        return predicate;
    }
}