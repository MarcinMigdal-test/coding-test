package com.tingco.codechallenge.elevator.impl.validator;

import com.tingco.codechallenge.elevator.impl.ElevatorCallRequest;
import com.tingco.codechallenge.elevator.impl.exception.ElevatorCallRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ElevatorCallRequestValidator {

  private final Logger LOG = LoggerFactory
      .getLogger(ElevatorCallRequestValidator.class.getCanonicalName());

  final int floorsNumber;

  public ElevatorCallRequestValidator(int floorsNumber) {
    this.floorsNumber = floorsNumber;
  }

  public void validateElevatorCallRequest(ElevatorCallRequest elevatorCallRequest)
      throws ElevatorCallRequestException {
    int currentFloor = elevatorCallRequest.getCurrentFloor();
    if (currentFloor < 0 || currentFloor > floorsNumber) {
      String message = String
          .format("Improper values for current floor %d and target floor %d ", currentFloor);
      LOG.warn(message);
      throw new ElevatorCallRequestException(message);
    }
  }
}