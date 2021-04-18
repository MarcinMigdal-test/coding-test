package com.tingco.codechallenge.elevator.impl;

import com.tingco.codechallenge.elevator.api.Elevator;
import com.tingco.codechallenge.elevator.config.FloorsElevatorsConfig;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ElevatorFactoryTest {

  @Test
  public void create(){

    List<Elevator> elevatorList =  ElevatorFactory.getElevators(FloorsElevatorsConfig.ELEVATORS_AMOUNT_1);
    Assertions.assertEquals(FloorsElevatorsConfig.ELEVATORS_AMOUNT_1,elevatorList.size());
  }
}
