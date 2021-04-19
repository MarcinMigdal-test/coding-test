package com.tingco.codechallenge.elevator.impl.request;

public class ElevatorMoveFromFloorToFloorRequest extends AbstractRequest {

    private final int currentFloor;

    public ElevatorMoveFromFloorToFloorRequest(int currentFloor, int targetFloor) {
        super(targetFloor);
        this.currentFloor = currentFloor;
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

}
