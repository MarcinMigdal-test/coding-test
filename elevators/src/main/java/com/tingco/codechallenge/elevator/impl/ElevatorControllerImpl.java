package com.tingco.codechallenge.elevator.impl;

import com.tingco.codechallenge.elevator.api.Elevator;
import com.tingco.codechallenge.elevator.api.ElevatorController;
import com.tingco.codechallenge.elevator.impl.exception.ElevatorCallRequestException;
import com.tingco.codechallenge.elevator.impl.validator.ElevatorCallRequestValidator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ElevatorControllerImpl implements ElevatorController {

  private Executor executor;
  private final Logger LOG = LoggerFactory
      .getLogger(ElevatorControllerImpl.class.getCanonicalName());

  private ElevatorCallRequestValidator elevatorCallRequestValidator;
  private List<Elevator> elevatorList;
  private final int floorsNumber;


  public ElevatorControllerImpl(List<Elevator> elevatorList, int floorsNumber) {
    this.elevatorList = elevatorList;
    this.floorsNumber = floorsNumber;
    elevatorCallRequestValidator = new ElevatorCallRequestValidator(floorsNumber);
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
  public void executeElevatorCallRequest(ElevatorCallRequest elevatorCallRequest){
    int elevatorCallRequestFloor = elevatorCallRequest.getCurrentFloor();
    final Elevator candidateFree;
    Optional<Elevator> freeElevator = elevatorList.stream().filter(elevator -> !elevator.isBusy()).findAny();
    if( freeElevator.isPresent()){
      candidateFree = freeElevator.get();
      candidateFree.requestElevatorMovement(elevatorCallRequestFloor);
      executor.execute(()->{candidateFree.run();});
    }else{
      Elevator busyElevator = elevatorList.get(1);
      busyElevator.requestElevatorMovement(elevatorCallRequestFloor);
    }
  }
}