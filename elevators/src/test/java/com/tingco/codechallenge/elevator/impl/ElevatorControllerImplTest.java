package com.tingco.codechallenge.elevator.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.jayway.awaitility.Awaitility;
import com.tingco.codechallenge.elevator.api.ElevatorController;
import com.tingco.codechallenge.elevator.config.FloorsElevatorsConfig;
import com.tingco.codechallenge.elevator.impl.request.ElevatorCallRequestWithDirection;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ElevatorControllerImplTest {

  private ElevatorController elevatorController;

  @BeforeEach
  void setUp() {
    elevatorController = new ElevatorControllerImpl(ElevatorFactory.getElevators(FloorsElevatorsConfig.ELEVATORS_AMOUNT_2),
        FloorsElevatorsConfig.FLOORS_AMOUNT_EQUAL_6);
  }

  @Test
  void getElevators() {
    assertEquals(FloorsElevatorsConfig.ELEVATORS_AMOUNT_2, elevatorController.getElevators().size());
  }

  @Test
  void releaseElevator() {
  }

  @Test
  void executeElevatorCallRequest() {
    ElevatorCallRequestWithDirection elevatorCallRequestWithDirection = new ElevatorCallRequestWithDirection(FloorsElevatorsConfig.FLOOR_4,UserDirectionRequest.UP);
    elevatorController.executeElevatorCallRequestWithDirection(elevatorCallRequestWithDirection);
    Awaitility.await().atMost(5, TimeUnit.SECONDS);
    Assertions.assertFalse(elevatorController.getElevators().get(1).isBusy());
    Assertions.assertTrue(elevatorController.getElevators().get(0).isBusy());
  }
}