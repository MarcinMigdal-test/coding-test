package com.tingco.codechallenge.elevator.impl;

import com.tingco.codechallenge.elevator.api.Elevator;
import com.tingco.codechallenge.elevator.api.ElevatorController;
import com.tingco.codechallenge.elevator.impl.request.ElevatorCallRequestNoDirection;
import com.tingco.codechallenge.elevator.impl.request.ElevatorCallRequestWithDirection;
import com.tingco.codechallenge.elevator.impl.request.ElevatorMoveBetweenFloorsRequest;
import com.tingco.codechallenge.elevator.impl.validator.ElevatorRequestValidator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.HttpServerErrorException.NotImplemented;

public class ElevatorControllerImpl implements ElevatorController {

    private final Logger LOG = LoggerFactory
        .getLogger(ElevatorControllerImpl.class.getCanonicalName());
    private final int floorsNumber;
    private final Executor executor;
    private final ElevatorRequestValidator elevatorRequestValidator;
    private final List<Elevator> elevatorList;

    public ElevatorControllerImpl(List<Elevator> elevatorList, int floorsNumber) {
        this.elevatorList = elevatorList;
        this.floorsNumber = floorsNumber;
        elevatorRequestValidator = new ElevatorRequestValidator(floorsNumber);
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
        int elevatorCallTargetFloor = elevatorCallRequestWithDirection.getTargetFloor();
        final Elevator candidateFree;
        Optional<Elevator> freeElevator = elevatorList.stream()
            .filter(elevator -> !elevator.isBusy()).findFirst();
        if (freeElevator.isPresent()) {
            candidateFree = freeElevator.get();
            candidateFree.requestElevatorMovement(elevatorCallTargetFloor);
            executor.execute(candidateFree::run);
        } else {
            // for now check always 1st elevator
            //TODO select random elevator
            Elevator busyElevator = elevatorList.get(0);
            busyElevator.requestElevatorMovement(elevatorCallTargetFloor);
        }
    }

    @Override
    public void executeElevatorCallRequestWithNoDirection(
        ElevatorCallRequestNoDirection elevatorCallRequest) {

    }

    @Override
    public void executeElevatorCallRequestBetweenFloors(
        ElevatorMoveBetweenFloorsRequest elevatorCallRequest) {
           throw new IllegalArgumentException("Method not supported when user gets on elevator board");
    }

}