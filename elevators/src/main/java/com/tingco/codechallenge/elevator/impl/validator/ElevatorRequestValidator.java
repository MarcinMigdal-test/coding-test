package com.tingco.codechallenge.elevator.impl.validator;

import com.tingco.codechallenge.elevator.config.ElevatorConfiguration;
import com.tingco.codechallenge.elevator.impl.exception.ElevatorRequestException;
import com.tingco.codechallenge.elevator.impl.request.ElevatorCallRequestNoDirection;
import com.tingco.codechallenge.elevator.impl.request.ElevatorCallRequestWithDirection;
import com.tingco.codechallenge.elevator.impl.request.ElevatorMoveBetweenFloorsRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ElevatorRequestValidator {

    private final Logger LOG = LoggerFactory.getLogger(ElevatorRequestValidator.class.getCanonicalName());
    private final static String TARGET_FLOOR = "Target floor";
    final int floorsNumber;

    //private ElevatorConfiguration elevatorConfiguration;

    @Deprecated
    public ElevatorRequestValidator(int floorsNumber) {
        this.floorsNumber = floorsNumber;
    }

    @Autowired
    public ElevatorRequestValidator (ElevatorConfiguration elevatorConfiguration){
        this.floorsNumber = elevatorConfiguration.getFloorsNumber();
    }

    public void validateCallRequestNoDirection(
        ElevatorCallRequestNoDirection elevatorCallRequestNoDirection)
        throws ElevatorRequestException {
        int targetFloor = elevatorCallRequestNoDirection.getTargetFloor();
        validateFloor(TARGET_FLOOR, targetFloor);
    }

    public void validateCallRequestWithDirection(
        ElevatorCallRequestWithDirection elevatorCallRequestWithDirection)
        throws ElevatorRequestException {
        int targetFloor = elevatorCallRequestWithDirection.getTargetFloor();
        validateFloor(TARGET_FLOOR, targetFloor);
    }

    public void validateMoveBetweenFloorsRequest(
        ElevatorMoveBetweenFloorsRequest elevatorMoveBetweenFloorsRequest)
        throws ElevatorRequestException {
        int currentFloor = elevatorMoveBetweenFloorsRequest.getCurrentFloor();
        int targetFloor = elevatorMoveBetweenFloorsRequest.getTargetFloor();
        validateFloor("Current floor", currentFloor);
        validateFloor(TARGET_FLOOR, targetFloor);
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