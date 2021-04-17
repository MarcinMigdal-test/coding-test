package com.tingco.codechallenge.elevator.impl;

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
    int targetFloor = elevatorCallRequest.getTargetFloor();
    if (currentFloor > floorsNumber || targetFloor > floorsNumber) {
      String message = String
          .format("Improper values for current floor %d and target floor %d ", currentFloor,
              targetFloor);
      LOG.warn(message);
      throw new ElevatorCallRequestException(message);
    }
  }
}