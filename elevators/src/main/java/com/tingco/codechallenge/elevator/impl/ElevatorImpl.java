package com.tingco.codechallenge.elevator.impl;

import com.tingco.codechallenge.elevator.api.Elevator;
import java.util.NavigableSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentSkipListSet;


public class ElevatorImpl implements Elevator{

    private final int MOVEMENT_TIME = 2000;
    private final int stopAtFloorTime = 2000;
    private final int NOP_TIME = 500;
    private final Logger LOG = LoggerFactory
        .getLogger(ElevatorImpl.class.getCanonicalName());

    private final int elevatorId;
    private Direction direction = Direction.NONE;
    private int currentFloor;
    private NavigableSet<Integer> floorsToVisitRequests = new ConcurrentSkipListSet<Integer>();

    public ElevatorImpl(int elevatorId){
        this.elevatorId = elevatorId;
        currentFloor = 0;
    }

    @Override
    public Direction getDirection() {
        return direction;
    }

    @Override
    public int getAddressedFloor() {
        return 0;
    }

    @Override
    public int getId() {
        return elevatorId;
    }

    @Override
    public void requestElevatorMovement(int toFloor) {
        floorsToVisitRequests.add(toFloor);
    }

    @Override
    public boolean isBusy() {
        return !direction.equals(Direction.NONE);
    }

    @Override
    public int currentFloor() {
        return currentFloor;
    }


    public void run() {
        LOG.info(String.format("Running elevator %d invoked", elevatorId));

        while(shouldExecuteMovement()){

        }

    }

    private boolean shouldExecuteMovement(){
        return !floorsToVisitRequests.isEmpty();
    }

    private void calculateDirection(){
        int topFloorNumber = floorsToVisitRequests.first();
        int bottomFloorNumber = floorsToVisitRequests.last();

        //()


    }


    private void executeMovement(){

        if(floorsToVisitRequests.size() ==1) //only one
        {
            moveElevator(floorsToVisitRequests.first());
        }
        else{


            //two different floors - > determine distance to the nearest one
            //lowestFloorToVisit , highestFlootToVisit
        }

    }

    public void moveElevator(int toFloor) {
        executeCycleMove(toFloor);
        calculateDirectionAndChangeFloor(toFloor);
    }

    @Override
    public void moveElevatorToFloor(int toFloor) {

    }

    private void calculateDirectionAndChangeFloor( int floorToVisit){
        direction = currentFloor<floorToVisit?Direction.UP:Direction.DOWN;
        switch (direction){
            case UP -> currentFloor++;
            case DOWN -> currentFloor--;
            default -> {
            }
        }

        if(floorsToVisitRequests.contains(currentFloor)){
            floorsToVisitRequests.remove(currentFloor);
        }

    }

    private int findNearestTargetFloor(int topFloorNumber , int bottomFloorNumber){
        int result1 = Math.abs(Math.subtractExact(currentFloor,topFloorNumber) );
        int result2 = Math.abs(Math.subtractExact(currentFloor,bottomFloorNumber));
        return 0;
    }

    private void executeCycleNop(){
        direction = Direction.NONE;
        try {
            Thread.sleep(NOP_TIME);
        } catch (InterruptedException e) {
            LOG.warn(String.format("Elevator %d cannot wait for requests",this.elevatorId));
        }
    }

    private void executeCycleMove(int toFloor){
        try {
            Thread.sleep(MOVEMENT_TIME);

        } catch (InterruptedException e) {
            LOG.warn(String.format("Elevator %d cannot wait for requests",this.elevatorId));
        }
    }

}