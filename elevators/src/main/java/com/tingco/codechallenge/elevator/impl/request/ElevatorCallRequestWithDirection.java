package com.tingco.codechallenge.elevator.impl.request;

import com.tingco.codechallenge.elevator.impl.UserDirectionRequest;

public class ElevatorCallRequestWithDirection extends AbstractRequest {

    private final UserDirectionRequest userDirectionRequest;

    public ElevatorCallRequestWithDirection(int currentFloor, UserDirectionRequest userDirectionRequest) {
        super(currentFloor);
        this.userDirectionRequest = userDirectionRequest;
    }

    public UserDirectionRequest getUserDirectionRequest() {
        return userDirectionRequest;
    }
}