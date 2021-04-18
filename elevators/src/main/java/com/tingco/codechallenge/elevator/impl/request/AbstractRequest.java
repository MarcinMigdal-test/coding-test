package com.tingco.codechallenge.elevator.impl.request;

abstract class AbstractRequest {

    private final int currentFloor;

    public AbstractRequest(int currentFloor) {
        this.currentFloor = currentFloor;
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

}
