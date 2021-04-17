package com.tingco.codechallenge.elevator.impl;

import com.tingco.codechallenge.elevator.api.Elevator;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class ElevatorImplTest {

    private final static int FLOOR_0 = 0;
    private final static int FLOOR_3 = 3;
    private final static int FLOOR_5 = 5;
    private Elevator elevator = new ElevatorImpl(1);

    @Test
    public void create(){
        assertNotNull(elevator);
    }

    @Test
    public void move(){
        assertEquals(FLOOR_0,elevator.currentFloor());
        elevator.requestElevatorMovement(FLOOR_5);
        elevator.run();
        assertEquals(FLOOR_5,elevator.currentFloor());
        elevator.requestElevatorMovement(FLOOR_3);
        elevator.run();
        assertEquals(FLOOR_3,elevator.currentFloor());
    }



}
