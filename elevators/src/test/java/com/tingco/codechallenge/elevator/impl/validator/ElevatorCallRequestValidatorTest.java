package com.tingco.codechallenge.elevator.impl.validator;

import com.tingco.codechallenge.elevator.impl.ElevatorCallRequest;
import com.tingco.codechallenge.elevator.impl.UserDirectionRequest;
import com.tingco.codechallenge.elevator.impl.exception.ElevatorCallRequestException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ElevatorCallRequestValidatorTest {

  private final static int FLOORS_NUMBER = 4;
  private final static int FLOOR_NUMBER_MINUS_1 = -1;
  private final static int FLOOR_NUMBER_1 = 1;
  private final static int FLOOR_NUMBER_10 = 10;

  private ElevatorCallRequestValidator testedObject;

  @BeforeEach
  public void setup() {
    testedObject = new ElevatorCallRequestValidator(FLOORS_NUMBER);
  }

  @Test
  void validateElevatorCallRequest_proper() throws ElevatorCallRequestException {
    ElevatorCallRequest elevatorCallRequest = new ElevatorCallRequest(FLOOR_NUMBER_1, UserDirectionRequest.UP
        );
    testedObject.validateElevatorCallRequest(elevatorCallRequest);
  }

  @Test
  void validateElevatorCallRequest_improper_currentFloor_minus_1_UP() {
    ElevatorCallRequest elevatorCallRequest = new ElevatorCallRequest(FLOOR_NUMBER_MINUS_1, UserDirectionRequest.UP);
    Assertions.assertThrows(ElevatorCallRequestException.class,
        () -> testedObject.validateElevatorCallRequest(elevatorCallRequest));
  }

  @Test
  void validateElevatorCallRequest_improper_currentFloor10_targetFloor3() {
    ElevatorCallRequest elevatorCallRequest = new ElevatorCallRequest(-1, UserDirectionRequest.UP );
    Assertions.assertThrows(ElevatorCallRequestException.class,
        () -> testedObject.validateElevatorCallRequest(elevatorCallRequest));
  }
}