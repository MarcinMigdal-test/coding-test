package com.tingco.codechallenge.elevator.impl;

import com.tingco.codechallenge.elevator.api.Elevator;

public class ElevatorImpl implements Elevator{

    private final int elevatorId;
    private Direction direction = Direction.NONE;
    private int currentFloor;
    private int destinationFloor;

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
