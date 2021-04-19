package com.tingco.codechallenge.elevator.impl.validator;

import static com.tingco.codechallenge.elevator.config.FloorsElevatorsConfig.FLOORS_AMOUNT_EQUAL_6;
import static com.tingco.codechallenge.elevator.config.FloorsElevatorsConfig.FLOOR_1;
import static com.tingco.codechallenge.elevator.config.FloorsElevatorsConfig.FLOOR_10;
import static com.tingco.codechallenge.elevator.config.FloorsElevatorsConfig.FLOOR_3;
import static com.tingco.codechallenge.elevator.config.FloorsElevatorsConfig.FLOOR_5;
import static com.tingco.codechallenge.elevator.config.FloorsElevatorsConfig.FLOOR_MINUS_1;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.tingco.codechallenge.elevator.impl.UserDirectionRequest;
import com.tingco.codechallenge.elevator.impl.exception.ElevatorRequestException;
import com.tingco.codechallenge.elevator.impl.request.ElevatorCallRequestNoDirection;
import com.tingco.codechallenge.elevator.impl.request.ElevatorCallRequestWithDirection;
import com.tingco.codechallenge.elevator.impl.request.ElevatorMoveBetweenFloorsRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ElevatorRequestValidatorTest {

    private ElevatorRequestValidator testedObject;

    @BeforeEach
    public void setup() {
        testedObject = new ElevatorRequestValidator(FLOORS_AMOUNT_EQUAL_6);
    }

    @Test
    void validateCallRequestNoDirection_proper_targetFloor_3_whereFloors_6()
        throws ElevatorRequestException {
        ElevatorCallRequestNoDirection elevatorCallRequestNoDirection = new ElevatorCallRequestNoDirection(
            FLOOR_1);
        testedObject.validateCallRequestNoDirection(elevatorCallRequestNoDirection);
    }

    @Test
    void validateCallRequestNoDirection_improper_targetFloor_10_whereFloors_6() {
        ElevatorCallRequestNoDirection elevatorCallRequestNoDirection = new ElevatorCallRequestNoDirection(
            FLOOR_10);
        assertThrows(ElevatorRequestException.class,
            () -> testedObject.validateCallRequestNoDirection(elevatorCallRequestNoDirection));
    }

    @Test
    void validateCallRequestNoDirection_improper_targetFloor_minus_1_whereFloors_6() {
        ElevatorCallRequestNoDirection elevatorCallRequestNoDirection = new ElevatorCallRequestNoDirection(
            FLOOR_MINUS_1);
        assertThrows(ElevatorRequestException.class,
            () -> testedObject.validateCallRequestNoDirection(elevatorCallRequestNoDirection));
    }

    @Test
    void validateCallRequestWithDirection_proper_targetFloor_1_whereFloors_6()
        throws ElevatorRequestException {
        ElevatorCallRequestWithDirection elevatorCallRequestWithDirection = new ElevatorCallRequestWithDirection(
            FLOOR_1,
            UserDirectionRequest.UP
        );
        testedObject.validateCallRequestWithDirection(elevatorCallRequestWithDirection);
    }

    @Test
    void validateCallRequestWithDirection_improper_currentFloor_minus_1_UP_whereFloors6() {
        ElevatorCallRequestWithDirection elevatorCallRequestWithDirection = new ElevatorCallRequestWithDirection(
            FLOOR_MINUS_1,
            UserDirectionRequest.UP);
        assertThrows(ElevatorRequestException.class,
            () -> testedObject.validateCallRequestWithDirection(elevatorCallRequestWithDirection));
    }

    @Test
    void validateCallRequestWithDirection_improper_targetFloor_10_UP_whereFloors_6() {
        ElevatorCallRequestWithDirection elevatorCallRequestWithDirection = new ElevatorCallRequestWithDirection(
            FLOOR_10,
            UserDirectionRequest.UP);
        assertThrows(ElevatorRequestException.class,
            () -> testedObject.validateCallRequestWithDirection(elevatorCallRequestWithDirection));
    }


    @Test
    void validateMoveBetweenFloorsRequest_from_3_to_5_whereFloors_6()
        throws ElevatorRequestException {
        ElevatorMoveBetweenFloorsRequest elevatorMoveBetweenFloorsRequest = new ElevatorMoveBetweenFloorsRequest(
            FLOOR_3, FLOOR_5);
        testedObject.validateMoveBetweenFloorsRequest(elevatorMoveBetweenFloorsRequest);
    }

    @Test
    void validateMoveBetweenFloorsRequest_from_minus_1_to_5_whereFloors_6() {
        ElevatorMoveBetweenFloorsRequest elevatorMoveBetweenFloorsRequest = new ElevatorMoveBetweenFloorsRequest(
            FLOOR_MINUS_1, FLOOR_5);
        assertThrows(ElevatorRequestException.class,
            () -> testedObject.validateMoveBetweenFloorsRequest(elevatorMoveBetweenFloorsRequest));
    }

}