package com.tingco.codechallenge.elevator.impl;

import com.tingco.codechallenge.elevator.api.Elevator;
import java.util.NavigableSet;
import java.util.Objects;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ElevatorImpl implements Elevator {

    private final int MOVEMENT_TIME = 100;
    private final int STOP_AT_FLOOR_TIME = 300;
    private final int NOP_TIME = 100;
    private final Logger LOG = LoggerFactory
        .getLogger(ElevatorImpl.class.getCanonicalName());

    private final int elevatorId;
    private Direction direction = Direction.NONE;
    private int currentFloor;
    private AtomicInteger currentFlooratomic = new AtomicInteger();
    private int destinationFloor;
    private final NavigableSet<Integer> floorsToVisit = new ConcurrentSkipListSet<>();

    public ElevatorImpl(int elevatorId) {
        this.elevatorId = elevatorId;
        currentFloor = 0;
        destinationFloor = 0;
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

    @Deprecated
    @Override
    public void moveElevator(int toFloor) {
    }

    @Override
    public boolean isBusy() {
        return (!direction.equals(Direction.NONE) && !floorsToVisit.isEmpty());
    }

    @Override
    public int currentFloor() {
        return currentFloor;
    }

    //=====================================================================
    @Override
    public NavigableSet<Integer> getFloorsToBeVisited() {
        return floorsToVisit;
    }

    @Override
    public void requestElevatorMovement(int toFloor) {
        floorsToVisit.add(toFloor);
    }

    @Override
    public void run() {
        while (isMovementRequired()) {
            LOG.info(String.format("Elevator %d is moving", elevatorId));
            if (isElevatorDirectionChosen()) {
                moveElevator();
                if (isDestinationFloorAchieved()) {
                    direction = Direction.NONE;
                    stopElevatorAtFloor(this.destinationFloor,"Destination floor");
                }
                if (floorsToVisit.contains(this.currentFloor)) {
                    stopElevatorAtFloor(this.currentFloor,"Interim floor");
                }
            } else {
                LOG.info(String.format("Elevator %d has no direction set",elevatorId));
                int topFloorNumber = floorsToVisit.last();
                calculateDestinationFloor(topFloorNumber);
                setElavatorDirectionMovement();
            }
        }
    }

    private void calculateDestinationFloor(int topFloorNumber) {
        if (floorsToVisit.size() == 1) {
            LOG.info(String.format("Elevator %d has only one destination floor selected %d",elevatorId,destinationFloor));
            destinationFloor = topFloorNumber;
        }
        else{
            LOG.info(String.format("Elevator %d one has many floors to visit. It's current position is: %s ",elevatorId,currentFloor));
            int bottomFloorNumber = floorsToVisit.first();
            LOG.info(String.format("Elevator %d is calculating route ",elevatorId));
            destinationFloor = getNearestTargetFloorNumber(topFloorNumber, bottomFloorNumber);
            LOG.info(String.format("Elevator %d has chosen destination floor %d",elevatorId,destinationFloor));
        }
    }

    private void setElavatorDirectionMovement() {
        LOG.info(String.format("Elevator %d is calculating direction...",elevatorId));
        if (currentFloor < destinationFloor) {
            direction = Direction.UP;
        } else if (currentFloor > destinationFloor) {
            direction = Direction.DOWN;
        }
        LOG.info(String.format("Elevator %d has chosen direction %s",elevatorId, direction));
    }

    private boolean isMovementRequired() {
        return !floorsToVisit.isEmpty();
    }

    private boolean isElevatorDirectionChosen() {
        return !direction.equals(Direction.NONE);
    }

    private boolean isDestinationFloorAchieved() {
        return destinationFloor == currentFloor;
    }

    private int getNearestTargetFloorNumber(int topFloorNumber, int bottomFloorNumber) {
        int topFloorDistance = Math.abs(Math.subtractExact(currentFloor, topFloorNumber));
        int bottomFloorDistance = Math.abs(Math.subtractExact(currentFloor, bottomFloorNumber));
        return topFloorDistance<bottomFloorDistance?topFloorNumber:bottomFloorNumber;
    }

    private void moveElevator() {
        floorsToVisit.remove(this.currentFloor);
        LOG.info(String
            .format("Elevator id %d moves from floor %d towards floor %d", elevatorId, currentFloor,
                destinationFloor));
        switch (direction) {
            case UP -> currentFloor++;
            case DOWN -> currentFloor--;
            default -> {
            }
        }
        try {
            TimeUnit.MILLISECONDS.sleep(MOVEMENT_TIME);
        } catch (InterruptedException e) {
            LOG.warn(String.format("Elevator %d cannot wait for requests", this.elevatorId));
        }
        LOG.info(String
            .format("Elevator id %d moved from floor %d towards floor %d", elevatorId, currentFloor,
                destinationFloor));
    }

    private void stopElevatorAtFloor(int floor, String floorDescription) {
        try {
            TimeUnit.MILLISECONDS.sleep(STOP_AT_FLOOR_TIME);
            LOG.info(String.format("Elevator %d has cycle STOP at floor %d which is %s", this.elevatorId,
                floor,floorDescription));
        } catch (InterruptedException e) {
            LOG.warn(String
                .format("Elevator %d cannot execute STOP at floor %d due to %s", this.elevatorId,
                    floor, e.getMessage()));
        }
    }

    private void executeCycleNop() {
        direction = Direction.NONE;
        try {
            TimeUnit.MILLISECONDS.sleep(NOP_TIME);
            LOG.warn(String.format("Elevator %d has cycle NOP", this.elevatorId));
        } catch (InterruptedException e) {
            LOG.warn(String.format("Elevator %d execute cycle NOP due to: %s", this.elevatorId,
                e.getMessage()));
        }
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        ElevatorImpl that = (ElevatorImpl) obj;
        return this.getIdentifier().equals(that.getIdentifier()) ;
    }

    @Override
    public Integer getIdentifier(){
        return Integer.valueOf(this.elevatorId);
    }
}