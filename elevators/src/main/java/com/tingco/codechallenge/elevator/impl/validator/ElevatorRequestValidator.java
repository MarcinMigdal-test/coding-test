package com.tingco.codechallenge.elevator.impl.validator;

import com.tingco.codechallenge.elevator.impl.exception.ElevatorRequestException;
import com.tingco.codechallenge.elevator.impl.request.ElevatorCallRequest;
import com.tingco.codechallenge.elevator.impl.request.ElevatorMoveToFloorRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ElevatorRequestValidator {

    final int floorsNumber;
    private final Logger LOG = LoggerFactory
        .getLogger(ElevatorRequestValidator.class.getCanonicalName());

    public ElevatorRequestValidator(int floorsNumber) {
        this.floorsNumber = floorsNumber;
    }

    public void validateElevatorCallRequest(ElevatorCallRequest elevatorCallRequest)
        throws ElevatorRequestException {
        int currentFloor = elevatorCallRequest.getCurrentFloor();
        validateFloor("Current floor", currentFloor);
    }

    public void validateMoveBetweenFloorsRequest(
        ElevatorMoveToFloorRequest elevatorMoveToFloorRequest) throws ElevatorRequestException {
        int currentFloor = elevatorMoveToFloorRequest.getCurrentFloor();
        int targetFloor = elevatorMoveToFloorRequest.getTargetFloor();
        validateFloor("Current floor", currentFloor);
        validateFloor("Target floor", targetFloor);
    }

    private void validateFloor(String floorName, int value) throws ElevatorRequestException {
        if (value < 0 || value > floorsNumber) {
            String message = String
                .format("Improper value for %d for floor %s", value, floorName);
            LOG.warn(message);
            throw new ElevatorRequestException(message);
        }
    }
}