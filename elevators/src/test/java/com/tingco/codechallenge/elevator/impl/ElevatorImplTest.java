package com.tingco.codechallenge.elevator.impl;

import com.tingco.codechallenge.elevator.api.Elevator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ElevatorImplTest {

    Elevator elevator = new ElevatorImpl(1);

    @Test
    public void create(){
        Assertions.assertNotNull(elevator);
    }
}
