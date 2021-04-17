package com.tingco.codechallenge.elevator.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.tingco.codechallenge.elevator.api.ElevatorController;
import com.tingco.codechallenge.elevator.impl.exception.ElevatorCallRequestException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ElevatorControllerImplTest {

  private ElevatorController elevatorController;
  private int floorsNumber = 5;
  private int elevatorsNumber = 1;

  @BeforeEach
  public void setUp() {
    elevatorController = new ElevatorControllerImpl(ElevatorFactory.getElevators(elevatorsNumber),
        floorsNumber);
  }

  @Test
  public void requestElevator() {

  }

  @Test
  public void getElevators() {
    assertEquals(1, elevatorController.getElevators().size());
  }


  @Test
  public void releaseElevator() {

  }

  @Test
  void validateElevatorCallRequest_proper() throws ElevatorCallRequestException {
    ElevatorCallRequest elevatorCallRequest = new ElevatorCallRequest(0, UserDirectionRequest.UP,
        4);
    elevatorController.validateElevatorCallRequest(elevatorCallRequest);

  }

  @Test
  void validateElevatorCallRequest_improper_currentFloor0_UP_targetFloor10()  {
    ElevatorCallRequest elevatorCallRequest = new ElevatorCallRequest(0, UserDirectionRequest.UP,
        10);
    Assertions.assertThrows(ElevatorCallRequestException.class, () -> {
      elevatorController.validateElevatorCallRequest(elevatorCallRequest);
    });
  }

    @Test
    void validateElevatorCallRequest_improper_currentFloorNegative_UP_targetFloor3()  {
        ElevatorCallRequest elevatorCallRequest = new ElevatorCallRequest(-1, UserDirectionRequest.UP,
            3);
        Assertions.assertThrows(ElevatorCallRequestException.class, () -> {
            elevatorController.validateElevatorCallRequest(elevatorCallRequest);
        });
    }


  @Test
  void executeElevatorCallRequest() {

  }


}