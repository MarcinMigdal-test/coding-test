package com.tingco.codechallenge.elevator.impl.validator;

import com.tingco.codechallenge.elevator.impl.exception.ElevatorRequestException;
import com.tingco.codechallenge.elevator.impl.request.ElevatorCallRequest;
import com.tingco.codechallenge.elevator.impl.request.ElevatorMoveFromFloorToFloorRequest;
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
        int targetFloor = elevatorCallRequest.getTargetFloor();
        validateFloor("Target floor", targetFloor);
    }

    public void validateMoveBetweenFloorsRequest(
        ElevatorMoveFromFloorToFloorRequest elevatorMoveFromFloorToFloorRequest) throws ElevatorRequestException {
        int currentFloor = elevatorMoveFromFloorToFloorRequest.getCurrentFloor();
        int targetFloor = elevatorMoveFromFloorToFloorRequest.getTargetFloor();
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