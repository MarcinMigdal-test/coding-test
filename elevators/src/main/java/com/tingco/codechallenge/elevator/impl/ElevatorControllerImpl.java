package com.tingco.codechallenge.elevator.impl;

import com.tingco.codechallenge.elevator.api.Elevator;
import com.tingco.codechallenge.elevator.api.Elevator.Direction;
import com.tingco.codechallenge.elevator.api.ElevatorController;
import com.tingco.codechallenge.elevator.config.ElevatorConfiguration;
import com.tingco.codechallenge.elevator.impl.request.ElevatorCallRequestNoDirection;
import com.tingco.codechallenge.elevator.impl.request.ElevatorCallRequestWithDirection;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ElevatorControllerImpl implements ElevatorController {

    private final Logger LOG = LoggerFactory
        .getLogger(ElevatorControllerImpl.class.getCanonicalName());
    private final Executor executor;
    private final List<Elevator> elevatorList;

    @Autowired
    public ElevatorControllerImpl(ElevatorConfiguration elevatorConfiguration) {
        this.elevatorList = ElevatorFactory
            .getElevatorsAsList(elevatorConfiguration.getElevatorsNumber(),
                elevatorConfiguration.getElevatorMovemenentInterval(),
                elevatorConfiguration.getElevatorStopInterval());
        executor = Executors.newFixedThreadPool(elevatorList.size());
    }

    @Deprecated(forRemoval = true)
    @Override
    public Elevator requestElevator(int toFloor) {
        return elevatorList.get(0);
    }

    @Override
    public List<Elevator> getElevators() {
        return elevatorList;
    }

    @Override
    public void releaseElevator(Elevator elevator) {
    }

    @Override
    public void executeElevatorCallRequestWithDirection(
        ElevatorCallRequestWithDirection elevatorCallRequestWithDirection) {
        this.executeElevatorCallRequestWithNoDirection(
            new ElevatorCallRequestNoDirection(elevatorCallRequestWithDirection.getTargetFloor()));
    }

    @Override
    public void executeElevatorCallRequestWithNoDirection(
        ElevatorCallRequestNoDirection elevatorCallRequest) {
        int requestedFloor = elevatorCallRequest.getTargetFloor();
        LOG.info("Received request to move elevator to floor {}", requestedFloor);
        ElevatorsFilter filter = new ElevatorsFilter();
        List<Elevator> elevatorsMovingTowardsRequestedFloor = new ArrayList<>();
        elevatorsMovingTowardsRequestedFloor.addAll(filter
            .getElevatorsGoingWithDirectionTowardsFloor(elevatorList, Direction.DOWN,
                requestedFloor));
        elevatorsMovingTowardsRequestedFloor.addAll(filter
            .getElevatorsGoingWithDirectionTowardsFloor(elevatorList, Direction.UP,
                requestedFloor));
        if (elevatorsMovingTowardsRequestedFloor.isEmpty()) {
            assignRequestToStoppedElevator(requestedFloor, filter);
        } else {
            assignRequestToElevatorInMotion(requestedFloor, filter,
                elevatorsMovingTowardsRequestedFloor);
        }
    }

    private void assignRequestToStoppedElevator(int requestedFloor, ElevatorsFilter filter) {
        LOG.info(
            String.format("Assign request to send stopped elevator to floor %d ", requestedFloor));
        List<Elevator> stoppedElevators = filter.getStoppedElevators(elevatorList);
        Optional<Elevator> elevator = filter
            .getNearestElevatorToRequestedFloor(stoppedElevators, requestedFloor);
        elevator.ifPresent(elevatorToRun -> {
            elevatorToRun.requestElevatorMovement(requestedFloor);
            LOG.info(String.format("Sent request to stopped elevator %d to move to floor %d",
                elevatorToRun.getId(), requestedFloor));
            executor.execute(elevatorToRun::run);
        });
    }

    private void assignRequestToElevatorInMotion(int requestedFloor, ElevatorsFilter filter,
        List<Elevator> elevatorsMovingTowardsRequestedFloor) {
        Optional<Elevator> optionalElevator = filter
            .getNearestElevatorToRequestedFloor(elevatorsMovingTowardsRequestedFloor,
                requestedFloor);
        optionalElevator.ifPresent(elevator -> elevator.requestElevatorMovement(requestedFloor));
    }

}