package com.tingco.codechallenge.elevator.impl;

import com.tingco.codechallenge.elevator.api.Elevator;
import java.util.HashSet;
import java.util.Set;

public class ElevatorImpl implements Elevator{

    private final int movementTime = 1000;
    private final int stopAtFloorTime = 2000;
    private final int doNothingTime = 500;

    private final int elevatorId;
    private Direction direction = Direction.NONE;
    private int currentFloor;
    private int destinationFloor;
    private Set<Integer> floorsToVisit = new HashSet<>();

    public ElevatorImpl(int elevatorId){
        this.elevatorId = elevatorId;
        currentFloor = 0;
        destinationFloor = 0;
    }

    @Override
    public Direction getDirection() {
        return direction;
    }

    @Override
    public int getAddressedFloor() {
        return 0;
    }

    @Override
    public int getId() {
        return elevatorId;
    }

    @Override
    public void moveElevator(int toFloor) {
        floorsToVisit.add(toFloor);
    }

    @Override
    public boolean isBusy() {
        return !direction.equals(Direction.NONE) && currentFloor!=destinationFloor;
    }

    @Override
    public int currentFloor() {
        return currentFloor;
    }




}
