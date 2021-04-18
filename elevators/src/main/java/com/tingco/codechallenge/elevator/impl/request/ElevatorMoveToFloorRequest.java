package com.tingco.codechallenge.elevator.impl.request;

public class ElevatorMoveToFloorRequest extends AbstractRequest {

    private final int targetFloor;

    public ElevatorMoveToFloorRequest(int currentFloor, int targetFloor) {
        super(currentFloor);
        this.targetFloor = targetFloor;
    }

    public int getTargetFloor() {
        return targetFloor;
    }

}
