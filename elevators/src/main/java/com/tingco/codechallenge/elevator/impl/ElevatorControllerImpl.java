package com.tingco.codechallenge.elevator.impl;

import com.tingco.codechallenge.elevator.api.Elevator;
import com.tingco.codechallenge.elevator.api.Elevator.Direction;
import com.tingco.codechallenge.elevator.api.ElevatorController;
import com.tingco.codechallenge.elevator.config.ElevatorConfiguration;
import com.tingco.codechallenge.elevator.impl.request.ElevatorCallRequestNoDirection;
import com.tingco.codechallenge.elevator.impl.request.ElevatorCallRequestWithDirection;
import com.tingco.codechallenge.elevator.impl.request.ElevatorMoveBetweenFloorsRequest;
import com.tingco.codechallenge.elevator.util.DistanceUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
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
    }

    @Override
    public void executeElevatorCallRequestWithNoDirection(
        ElevatorCallRequestNoDirection elevatorCallRequest) {
        int elevatorCallTargetFloor = elevatorCallRequest.getTargetFloor();
        ElevatorsFilter filter = new ElevatorsFilter();
        List<Elevator> elevatorsMovingTowardsRequestedFlooor = new ArrayList<>();
        elevatorsMovingTowardsRequestedFlooor.addAll(filter.getElevatorsGoingWithDirectionTowardsFloor(elevatorList,Direction.DOWN,elevatorCallTargetFloor));
        elevatorsMovingTowardsRequestedFlooor.addAll(filter.getElevatorsGoingWithDirectionTowardsFloor(elevatorList,Direction.UP,elevatorCallTargetFloor));


        if(elevatorsMovingTowardsRequestedFlooor.isEmpty())
        {
            //elevators stoppped -> find the one nearest to call floor
            Optional<Elevator> firstFoundStoppedElevator = filter.getStoppedElevators(elevatorList).stream().findFirst();

            if (firstFoundStoppedElevator.isPresent()) {
                Elevator candidateFree = firstFoundStoppedElevator.get();
                candidateFree.requestElevatorMovement(elevatorCallTargetFloor);
                executor.execute(candidateFree::run);
        }
        else {
            Map<Integer, Integer> distanceByElevatorId = new ConcurrentHashMap<>();
                elevatorsMovingTowardsRequestedFlooor.forEach(elevator -> {
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