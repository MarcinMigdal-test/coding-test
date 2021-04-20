package com.tingco.codechallenge.elevator.impl.request;

abstract class Request {

    private final int targetFloor;

    public Request(int targetFloor) {
        this.targetFloor = targetFloor;
    }

    public int getTargetFloor() {
        return targetFloor;
    }
}
