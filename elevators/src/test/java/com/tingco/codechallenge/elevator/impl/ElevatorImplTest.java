package com.tingco.codechallenge.elevator.impl;

import com.tingco.codechallenge.elevator.api.Elevator;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class ElevatorImplTest {


    private Elevator elevator = new ElevatorImpl(1);

    @Test
    public void create(){
        assertNotNull(elevator);
    }

    @Test
    public void move(){
        assertEquals(FloorsElevatorsConfig.FLOOR_0,elevator.currentFloor());
        elevator.requestElevatorMovement(FloorsElevatorsConfig.FLOOR_5);
        elevator.run();
        assertEquals(FloorsElevatorsConfig.FLOOR_5,elevator.currentFloor());

        elevator.requestElevatorMovement(FloorsElevatorsConfig.FLOOR_3);
        elevator.run();
        assertEquals(FloorsElevatorsConfig.FLOOR_3,elevator.currentFloor());
    }



}
