package com.tingco.codechallenge.elevator.impl;

import com.tingco.codechallenge.elevator.api.Elevator;
import com.tingco.codechallenge.elevator.api.ElevatorController;

import com.tingco.codechallenge.elevator.impl.exception.ElevatorCallRequestException;
import java.util.List;

public class ElevatorControllerImpl implements ElevatorController {

    private List<Elevator> elevatorList;
    private final int floorsNumber ;

    public ElevatorControllerImpl(List<Elevator> elevatorList,int floorsNumber){
        this.elevatorList = elevatorList;
        this.floorsNumber = floorsNumber;
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

    @Override
    public void validate(ElevatorCallRequest elevatorCallRequest) throws ElevatorCallRequestException
    {
        if( elevatorCallRequest.getTargetFloor() > floorsNumber || elevatorCallRequest.getCurrentFloor() > floorsNumber) {
            throw new ElevatorCallRequestException();
        }
    }

    @Override
    public void execute(ElevatorCallRequest elevatorCallRequest) {




    }
}