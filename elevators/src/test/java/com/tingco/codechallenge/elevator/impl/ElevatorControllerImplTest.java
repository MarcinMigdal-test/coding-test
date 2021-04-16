package com.tingco.codechallenge.elevator.impl;

import com.tingco.codechallenge.elevator.api.ElevatorController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;

public class ElevatorControllerImplTest {

    private ElevatorController elevatorController;

    @BeforeEach
    public void setUp(){
        elevatorController  = new ElevatorControllerImpl(Collections.emptyList(),5);
    }

    @Test
    public void create(){
        Assertions.assertNotNull(elevatorController);
    }
}