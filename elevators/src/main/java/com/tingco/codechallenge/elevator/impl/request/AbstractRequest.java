package com.tingco.codechallenge.elevator.impl.request;

abstract class AbstractRequest {

    private final int targetFloor;

    public AbstractRequest(int targetFloor) {
        this.targetFloor = targetFloor;
    }

    public int getTargetFloor() {
        return targetFloor;
    }

}
