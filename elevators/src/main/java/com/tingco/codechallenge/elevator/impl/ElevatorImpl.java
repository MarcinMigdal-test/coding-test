package com.tingco.codechallenge.elevator.impl;

import com.tingco.codechallenge.elevator.api.Elevator;
import java.util.NavigableSet;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ElevatorImpl implements Elevator {

    private final Logger LOG = LoggerFactory
        .getLogger(ElevatorImpl.class.getCanonicalName());
    private final int movementInterval;
    private final int stopInterval;
    private final int elevatorId;
    private Direction direction = Direction.NONE;
    private AtomicInteger currentFloor;
    private int destinationFloor;
    private final NavigableSet<Integer> floorsToVisit = new ConcurrentSkipListSet<>();

    public ElevatorImpl(int elevatorId, int movementInterval, int stopInterval) {
        this.elevatorId = elevatorId;
        this.movementInterval = movementInterval;
        this.stopInterval = stopInterval;
        currentFloor = new AtomicInteger();
        destinationFloor = 0;
    }

    @Override
    public Direction getDirection() {
        return direction;
    }

    @Deprecated(forRemoval = true)
    @Override
    public int getAddressedFloor() {
        throw new  UnsupportedOperationException();
    }

    @Override
    public int getId() {
        return elevatorId;
    }

    @Deprecated(forRemoval = true)
    @Override
    public void moveElevator(int toFloor) {
        throw new  UnsupportedOperationException();
    }

    @Override
    public boolean isBusy() {
        return (!direction.equals(Direction.NONE) && !floorsToVisit.isEmpty());
    }

    @Override
    public int currentFloor() {
        return currentFloor.get();
    }

    @Override
    public void requestElevatorMovement(int toFloor) {
        floorsToVisit.add(toFloor);
    }

    @Override
    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    @Override
    public void setCurrentFloor(int floor) {
        this.currentFloor.set(floor);
    }

    @Override
    public void run() {
        while (isMovementRequired()) {
            LOG.trace(String.format("Elevator %d is moving", elevatorId));
            if (isElevatorDirectionChosen()) {
                moveElevator();
                if (isDestinationFloorAchieved()) {
                    direction = Direction.NONE;
                    stopElevatorAtFloor(this.destinationFloor, "destination floor");
                }
                if (floorsToVisit.contains(this.currentFloor.get())) {
                    stopElevatorAtFloor(this.currentFloor.get(), "interim floor");
                }
            } else {
                LOG.trace(String.format("Elevator %d has no direction set", elevatorId));
                int topFloorNumber = floorsToVisit.last();
                calculateDestinationFloor(topFloorNumber);
                setElevatorDirectionMovement();
            }
        }
    }

    private void calculateDestinationFloor(int topFloorNumber) {
        if (floorsToVisit.size() == 1) {
            LOG.trace(String
                .format("Elevator %d has only one destination floor selected %d", elevatorId,
                    destinationFloor));
            if (destinationFloor == topFloorNumber) {
                floorsToVisit.remove(destinationFloor);
            }
            destinationFloor = topFloorNumber;
        } else {
            LOG.info(String
                .format("Elevator %d one has many floors to visit. It's current position is: %s ",
                    elevatorId, currentFloor));
            int bottomFloorNumber = floorsToVisit.first();
            LOG.trace(String.format("Elevator %d is calculating route ", elevatorId));
            destinationFloor = getNearestTargetFloorNumber(topFloorNumber, bottomFloorNumber);
            LOG.info(String.format("Elevator %d has chosen destination floor %d", elevatorId,
                destinationFloor));
        }
    }

    private void setElevatorDirectionMovement() {
        LOG.info(String.format("Elevator %d is calculating direction...", elevatorId));
        if (currentFloor.get() < destinationFloor) {
            direction = Direction.UP;
        } else if (currentFloor.get() > destinationFloor) {
            direction = Direction.DOWN;
        } else {
            direction = Direction.NONE;
        }
        LOG.info(String.format("Elevator %d has chosen direction %s", elevatorId, direction));
    }

    private boolean isMovementRequired() {
        return (!floorsToVisit.isEmpty());
    }

    private boolean isElevatorDirectionChosen() {
        return !direction.equals(Direction.NONE);
    }

    private boolean isDestinationFloorAchieved() {
        return destinationFloor == currentFloor.get();
    }

    private int getNearestTargetFloorNumber(int topFloorNumber, int bottomFloorNumber) {
        int topFloorDistance = Math.abs(Math.subtractExact(currentFloor.get(), topFloorNumber));
        int bottomFloorDistance = Math.abs(Math.subtractExact(currentFloor.get(), bottomFloorNumber));
        return topFloorDistance < bottomFloorDistance ? topFloorNumber : bottomFloorNumber;
    }

    private void moveElevator() {
        LOG.info(String
            .format("Elevator %d moves from floor %d towards floor %d", elevatorId, currentFloor.get(),
                destinationFloor));
        switch (direction) {
            case UP -> currentFloor.getAndIncrement();
            case DOWN -> currentFloor.getAndDecrement();
            default -> {
            }
        }
        try {
            TimeUnit.MILLISECONDS.sleep(movementInterval);
        } catch (InterruptedException e) {
            LOG.warn(String.format("Elevator %d cannot wait for requests", this.elevatorId));
        }
        floorsToVisit.remove(this.currentFloor.get());
        LOG.trace(String
            .format("Elevator id %d moved from floor %d towards floor %d", elevatorId, currentFloor.get(),
                destinationFloor));
    }

    private void stopElevatorAtFloor(int floor, String floorDescription) {
        try {
            TimeUnit.MILLISECONDS.sleep(stopInterval);
            LOG.info(String.format("Elevator %d stops at floor %d which is %s", this.elevatorId,
                floor, floorDescription));
        } catch (InterruptedException e) {
            LOG.warn(String
                .format("Elevator %d cannot execute STOP at floor %d due to %s", this.elevatorId,
                    floor, e.getMessage()));
        }
    }
}