package com.tingco.codechallenge.elevator.impl;

import com.tingco.codechallenge.elevator.api.Elevator;
import java.util.NavigableSet;
import java.util.concurrent.ConcurrentSkipListSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ElevatorImpl implements Elevator {

    private final int MOVEMENT_TIME = 2;
    private final int STOP_AT_FLOOR_TIME = 3;
    private final int NOP_TIME = 5;
    private final Logger LOG = LoggerFactory
        .getLogger(ElevatorImpl.class.getCanonicalName());

    private final int elevatorId;
    private Direction direction = Direction.NONE;
    private int currentFloor;
    private int destinationFloor;
    private final NavigableSet<Integer> floorsToVisitRequests = new ConcurrentSkipListSet<Integer>();

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
        while (shouldExecuteMovement()) {

            if (isDirectionChosen()) {
                //zmien pietro
                executeCycleMove();

                if (isDestinationFloorAchieved()) {
                    direction = Direction.NONE;
                    executeCycleStopNaPietrzePoDrodze();
                }

                if (floorsToVisitRequests.contains(this.currentFloor)) {
                    floorsToVisitRequests.remove(this.currentFloor);
                    executeCycleStopNaPietrzePoDrodze();
                }

            } else {
                int topFloorNumber = floorsToVisitRequests.last();
                int bottomFloorNumber = floorsToVisitRequests.first();
                if (floorsToVisitRequests.size() == 1) {
                    destinationFloor = topFloorNumber;
                    if (currentFloor < destinationFloor) {
                        direction = Direction.UP;
                    } else if (currentFloor > destinationFloor) {
                        direction = Direction.DOWN;
                    }
                }
            }

            LOG.info(String.format("Running elevator %d invoked", elevatorId));
            if (direction.equals(Direction.NONE)) {
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
        int result1 = Math.abs(Math.subtractExact(currentFloor, topFloorNumber));
        int result2 = Math.abs(Math.subtractExact(currentFloor, bottomFloorNumber));
        return 0;
    }

    private void executeCycleMove() {
        LOG.info(String
            .format("Elevator id %d moves from %d towards floor %d", elevatorId, currentFloor,
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
            .format("Elevator id %d moved from %d towards floor %d", elevatorId, currentFloor,
                destinationFloor));
    }

    private void executeCycleStopNaPietrzePoDrodze() {
        try {
            Thread.sleep(STOP_AT_FLOOR_TIME);
            LOG.warn(String.format("Elevator %d has cycle STOP at floor %d", this.elevatorId,
                this.currentFloor));
        } catch (InterruptedException e) {
            LOG.warn(String
                .format("Elevator %d cannot execute STOP at floor %d due to %s", this.elevatorId,
                    this.currentFloor, e.getMessage()));
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