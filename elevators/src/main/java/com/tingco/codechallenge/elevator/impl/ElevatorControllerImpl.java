package com.tingco.codechallenge.elevator.impl;

import com.tingco.codechallenge.elevator.api.Elevator;
import com.tingco.codechallenge.elevator.api.Elevator.Direction;
import com.tingco.codechallenge.elevator.api.ElevatorController;
import com.tingco.codechallenge.elevator.config.ElevatorConfiguration;
import com.tingco.codechallenge.elevator.impl.request.ElevatorCallRequestNoDirection;
import com.tingco.codechallenge.elevator.impl.request.ElevatorCallRequestWithDirection;
import com.tingco.codechallenge.elevator.impl.request.ElevatorMoveBetweenFloorsRequest;
import com.tingco.codechallenge.elevator.util.DistanceUtils;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Predicate;
import java.util.stream.Collectors;
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
    private final Map<Integer,Elevator> elevatorMap;

    @Autowired
    public ElevatorControllerImpl(ElevatorConfiguration elevatorConfiguration) {
        this.elevatorList = ElevatorFactory.getElevatorsAsList(elevatorConfiguration.getElevatorsNumber());
        this.elevatorMap = ElevatorFactory.getElevatorsAsMap(elevatorConfiguration.getElevatorsNumber());
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

    //TODO
    @Override
    public void executeElevatorCallRequestWithDirection(
        ElevatorCallRequestWithDirection elevatorCallRequestWithDirection) {

        this.executeElevatorCallRequestWithNoDirection(new ElevatorCallRequestNoDirection(elevatorCallRequestWithDirection.getTargetFloor()));

        /*
        int elevatorCallTargetFloor = elevatorCallRequestWithDirection.getTargetFloor();
        final Elevator candidateFree;
        Optional<Elevator> freeElevator = elevatorList.stream()
            .filter(elevator -> !elevator.isBusy()).findFirst();
        if (freeElevator.isPresent()) {
            candidateFree = freeElevator.get();
            candidateFree.requestElevatorMovement(elevatorCallTargetFloor);
            executor.execute(candidateFree::run);
        } else {
            Elevator busyElevator = elevatorList.get( ThreadLocalRandom.current().nextInt(elevatorList.size()-1 ));
            busyElevator.requestElevatorMovement(elevatorCallTargetFloor);
        }
        */

    }

    @Override
    public void executeElevatorCallRequestWithNoDirection(
        ElevatorCallRequestNoDirection elevatorCallRequest) {
        int elevatorCallTargetFloor = elevatorCallRequest.getTargetFloor();
        //znajdz windy ktore zmierzaja do pietra, ktore jest requestowane
        //wydzielic do funkcji
        Predicate<Elevator> predElevatorGoingDown = (elevator) -> elevator.currentFloor() > elevatorCallTargetFloor && elevator
            .getDirection().equals(
                Direction.DOWN);
        Predicate<Elevator> preElevatorGoingUp = (elevator) -> elevator.currentFloor() < elevatorCallTargetFloor && elevator
            .getDirection().equals(
                Direction.UP);
        List<Elevator> elevatorsInMove = elevatorList.parallelStream().filter(predElevatorGoingDown).filter(preElevatorGoingUp).collect(
            Collectors.toList());
        if(elevatorsInMove.isEmpty())
        {
            //elevators stoppped -> find the one nearest to call floor

            Optional<Elevator> firstFoundStoppedElevator = elevatorList.stream()
                .filter(elevator -> !elevator.isBusy()).findFirst();
            if (firstFoundStoppedElevator.isPresent()) {
                Elevator candidateFree = firstFoundStoppedElevator.get();
                candidateFree.requestElevatorMovement(elevatorCallTargetFloor);
                executor.execute(candidateFree::run);

        }
        else {
            Map<Integer, Integer> distanceByElevatorId = new ConcurrentHashMap<>();
            elevatorsInMove.forEach(elevator -> {
                    distanceByElevatorId
                        .put(Math.abs(elevator.currentFloor() - elevatorCallTargetFloor),
                            elevator.getIdentifier());
                }
            );
            Integer elevatorIdentifierWithShortestDistance = DistanceUtils
                .findElevatorIdWithShortestDistance(distanceByElevatorId);
            Elevator inMove = elevatorMap.get(elevatorIdentifierWithShortestDistance);
            inMove.requestElevatorMovement(elevatorCallTargetFloor);
        }
       }
    }

    @Override
    public void executeElevatorCallRequestBetweenFloors(
        ElevatorMoveBetweenFloorsRequest elevatorCallRequest) {
           throw new IllegalArgumentException("Method not supported when user gets on elevator board");
    }



}