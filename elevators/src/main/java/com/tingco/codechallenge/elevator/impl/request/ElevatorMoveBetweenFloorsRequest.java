package com.tingco.codechallenge.elevator.impl.request;

public class ElevatorMoveBetweenFloorsRequest extends AbstractRequest {

    private final int currentFloor;

    public ElevatorMoveBetweenFloorsRequest(int currentFloor, int targetFloor) {
        super(targetFloor);
        this.currentFloor = currentFloor;
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

}
