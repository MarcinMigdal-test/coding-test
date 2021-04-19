package com.tingco.codechallenge.elevator.impl;

import com.tingco.codechallenge.elevator.api.Elevator;
import java.util.NavigableSet;
import java.util.concurrent.ConcurrentSkipListSet;
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
    private int destinationFloor;
    private final NavigableSet<Integer> floorsToVisitRequests = new ConcurrentSkipListSet<>();

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
        return !direction.equals(Direction.NONE);
    }

    @Override
    public int currentFloor() {
        return currentFloor;
    }



    //=====================================================================
    @Override
    public NavigableSet<Integer> floorsCheck() {
        return floorsToVisitRequests;
    }

    @Override
    public void requestElevatorMovement(int toFloor) {
        floorsToVisitRequests.add(toFloor);
    }

    @Override
    public void run() {
        while (shouldExecuteMovement()) {
            LOG.info(String.format("Elevator %d is moving", elevatorId));
            if (isDirectionChosen()) {
                //zmien pietro
                executeCycleMove();
                if (isDestinationFloorAchieved()) {
                    floorsToVisitRequests.remove(this.currentFloor);
                    direction = Direction.NONE;
                    executeCycleStopNaPietrzePoDrodze(this.destinationFloor,"Destination floor");
                }
                if (floorsToVisitRequests.contains(this.currentFloor)) {
                    floorsToVisitRequests.remove(this.currentFloor);
                    //if direction
                    executeCycleStopNaPietrzePoDrodze(this.currentFloor,"Interim floor");
                }
            } else {
                LOG.info(String.format("Elevator %d has no direction set",elevatorId));
                int topFloorNumber = floorsToVisitRequests.last();
                if (floorsToVisitRequests.size() == 1) {
                    LOG.info(String.format("Elevator %d has only one destination floor selected %d",elevatorId,destinationFloor));
                    destinationFloor = topFloorNumber;
                }
                else{
                    LOG.info(String.format("Elevator %d one has many floors to visit. It's current position is: %s ",elevatorId,currentFloor));
                    int bottomFloorNumber = floorsToVisitRequests.first();
                    LOG.info(String.format("Elevator %d is calculating route ",elevatorId));
                    destinationFloor = findNearestTargetFloor(topFloorNumber, bottomFloorNumber);
                    LOG.info(String.format("Elevator %d has chosen destination floor %d",elevatorId,destinationFloor));
                }
                LOG.info(String.format("Elevator %d is calculating direction...",elevatorId));
                if (currentFloor < destinationFloor) {
                    direction = Direction.UP;
                } else if (currentFloor > destinationFloor) {
                    direction = Direction.DOWN;
                }
                LOG.info(String.format("Elevator %d has chosen direction %s",elevatorId, direction));
            }
        }
    }

    private boolean shouldExecuteMovement() {
        return !floorsToVisitRequests.isEmpty();
    }

    private boolean isDirectionChosen() {
        return !direction.equals(Direction.NONE);
    }

    private boolean isDestinationFloorAchieved() {
        return destinationFloor == currentFloor;
    }

    private int findNearestTargetFloor(int topFloorNumber, int bottomFloorNumber) {
        int topFloorDistance = Math.abs(Math.subtractExact(currentFloor, topFloorNumber));
        int bottomFloorDistance = Math.abs(Math.subtractExact(currentFloor, bottomFloorNumber));
        return topFloorDistance<bottomFloorDistance?topFloorNumber:bottomFloorNumber;
    }

    private void executeCycleMove() {
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
            Thread.sleep(MOVEMENT_TIME);
        } catch (InterruptedException e) {
            LOG.warn(String.format("Elevator %d cannot wait for requests", this.elevatorId));
        }
        LOG.info(String
            .format("Elevator id %d moved from floor %d towards floor %d", elevatorId, currentFloor,
                destinationFloor));
    }

    private void executeCycleStopNaPietrzePoDrodze(int floor, String floorDescription) {
        try {
            Thread.sleep(STOP_AT_FLOOR_TIME);
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
            Thread.sleep(NOP_TIME);
            LOG.warn(String.format("Elevator %d has cycle NOP", this.elevatorId));
        } catch (InterruptedException e) {
            LOG.warn(String.format("Elevator %d execute cycle NOP due to: %s", this.elevatorId,
                e.getMessage()));
        }
    }
}