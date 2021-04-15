package com.tingco.codechallenge.elevator.impl;

import com.tingco.codechallenge.elevator.api.Elevator;
import com.tingco.codechallenge.elevator.api.ElevatorController;

import java.util.List;

public class ElevatorControllerImpl implements ElevatorController {

    private List<Elevator> elevatorList;

    public ElevatorControllerImpl(List<Elevator> elevatorList){
        this.elevatorList = elevatorList;
    }

    @Override
    public Elevator requestElevator(int toFloor) {
        return null;
    }

    @Override
    public List<Elevator> getElevators() {
        return null;
    }

    @Override
    public void releaseElevator(Elevator elevator) {

    }
}