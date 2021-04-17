package com.tingco.codechallenge.elevator.impl;

import com.tingco.codechallenge.elevator.api.Elevator;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ElevatorFactoryTest {

  @Test
  public void create(){
    int elevatorsNumber = 4;
    List<Elevator> elevatorList =  ElevatorFactory.getElevators(elevatorsNumber);
    Assertions.assertEquals(elevatorsNumber,elevatorList.size());
  }
}
