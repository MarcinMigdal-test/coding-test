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
  void executeElevatorCallRequest() {

  }

}