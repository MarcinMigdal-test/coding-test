package com.tingco.codechallenge.elevator.impl.validator;

import static com.tingco.codechallenge.elevator.config.FloorsElevatorsConfig.FLOOR_1;
import static com.tingco.codechallenge.elevator.config.FloorsElevatorsConfig.FLOOR_10;
import static com.tingco.codechallenge.elevator.config.FloorsElevatorsConfig.FLOOR_3;
import static com.tingco.codechallenge.elevator.config.FloorsElevatorsConfig.FLOOR_5;
import static com.tingco.codechallenge.elevator.config.FloorsElevatorsConfig.FLOOR_MINUS_1;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.tingco.codechallenge.elevator.config.FloorsElevatorsConfig;
import com.tingco.codechallenge.elevator.impl.UserDirectionRequest;
import com.tingco.codechallenge.elevator.impl.exception.ElevatorRequestException;
import com.tingco.codechallenge.elevator.impl.request.ElevatorCallRequest;
import com.tingco.codechallenge.elevator.impl.request.ElevatorMoveToFloorRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ElevatorRequestValidatorTest {

    private ElevatorRequestValidator testedObject;

    @BeforeEach
    public void setup() {
        testedObject = new ElevatorRequestValidator(FloorsElevatorsConfig.FLOORS_AMOUNT_EQUAL_6);
    }

    @Test
    void validateElevatorCallRequest_proper() throws ElevatorRequestException {
        ElevatorCallRequest elevatorCallRequest = new ElevatorCallRequest(FLOOR_1,
            UserDirectionRequest.UP
        );
        testedObject.validateElevatorCallRequest(elevatorCallRequest);
    }

    @Test
    void validateElevatorCallRequest_improper_currentFloor_minus_1_UP() {
        ElevatorCallRequest elevatorCallRequest = new ElevatorCallRequest(FLOOR_MINUS_1,
            UserDirectionRequest.UP);
        assertThrows(ElevatorRequestException.class,
            () -> testedObject.validateElevatorCallRequest(elevatorCallRequest));
    }

    @Test
    void validateElevatorCallRequest_improper_currentFloor10_targetFloor3() {
        ElevatorCallRequest elevatorCallRequest = new ElevatorCallRequest(FLOOR_10,
            UserDirectionRequest.UP);
        assertThrows(ElevatorRequestException.class,
            () -> testedObject.validateElevatorCallRequest(elevatorCallRequest));
    }

    @Test
    void validateMoveBetweenFloorsRequest_from_1_to_4() throws ElevatorRequestException {
        ElevatorMoveToFloorRequest elevatorMoveToFloorRequest = new ElevatorMoveToFloorRequest(
            FLOOR_3, FLOOR_5);
        testedObject.validateMoveBetweenFloorsRequest(elevatorMoveToFloorRequest);
    }

    @Test
    void validateMoveBetweenFloorsRequest_from_minus_1_to_4() {
        ElevatorMoveToFloorRequest elevatorMoveToFloorRequest = new ElevatorMoveToFloorRequest(
            FLOOR_MINUS_1, FLOOR_5);
        assertThrows(ElevatorRequestException.class,
            () -> testedObject.validateMoveBetweenFloorsRequest(elevatorMoveToFloorRequest));
    }

  @Test
  void validateMoveBetweenFloorsRequest_from_6_to_10() {
    ElevatorMoveToFloorRequest elevatorMoveToFloorRequest = new ElevatorMoveToFloorRequest(
        FLOOR_MINUS_1, FLOOR_5);
    assertThrows(ElevatorRequestException.class,
        () -> testedObject.validateMoveBetweenFloorsRequest(elevatorMoveToFloorRequest));
  }
}