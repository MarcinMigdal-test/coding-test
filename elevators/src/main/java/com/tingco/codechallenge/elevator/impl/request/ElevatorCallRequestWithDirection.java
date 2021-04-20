package com.tingco.codechallenge.elevator.impl.request;

import com.tingco.codechallenge.elevator.impl.UserDirectionRequest;

/**
 * This class is base for further development where calling elevator with direction is supported
 */
public class ElevatorCallRequestWithDirection extends Request {

    private final UserDirectionRequest userDirectionRequest;

    public ElevatorCallRequestWithDirection(int currentFloor,
        UserDirectionRequest userDirectionRequest) {
        super(currentFloor);
        this.userDirectionRequest = userDirectionRequest;
    }

    public UserDirectionRequest getUserDirectionRequest() {
        return userDirectionRequest;
    }
}