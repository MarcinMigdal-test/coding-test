package com.tingco.codechallenge.elevator.impl.validator;

import com.tingco.codechallenge.elevator.impl.ElevatorCallRequest;
import com.tingco.codechallenge.elevator.impl.UserDirectionRequest;
import com.tingco.codechallenge.elevator.impl.exception.ElevatorCallRequestException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ElevatorCallRequestValidatorTest {

  private final static int FLOORS_NUMBER = 4;
  private ElevatorCallRequestValidator testedObject;

  @BeforeEach
  public void setup() {
    testedObject = new ElevatorCallRequestValidator(FLOORS_NUMBER);
  }

  @Test
  void validateElevatorCallRequest_proper() throws ElevatorCallRequestException {
    ElevatorCallRequest elevatorCallRequest = new ElevatorCallRequest(0, UserDirectionRequest.UP,
        4);
    testedObject.validateElevatorCallRequest(elevatorCallRequest);
  }

  @Test
  void validateElevatorCallRequest_improper_currentFloor0_UP_targetFloor10() {
    ElevatorCallRequest elevatorCallRequest = new ElevatorCallRequest(0, UserDirectionRequest.UP,
        10);
    Assertions.assertThrows(ElevatorCallRequestException.class,
        () -> testedObject.validateElevatorCallRequest(elevatorCallRequest));
  }

  @Test
  void validateElevatorCallRequest_improper_currentFloorNegative_UP_targetFloor3() {
    ElevatorCallRequest elevatorCallRequest = new ElevatorCallRequest(-1, UserDirectionRequest.UP,
        3);
    Assertions.assertThrows(ElevatorCallRequestException.class,
        () -> testedObject.validateElevatorCallRequest(elevatorCallRequest));
  }
}