package com.tingco.codechallenge.elevator.impl;

import com.tingco.codechallenge.elevator.api.Elevator;
import com.tingco.codechallenge.elevator.api.ElevatorController;
import com.tingco.codechallenge.elevator.impl.exception.ElevatorCallRequestException;
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
    /*
    Predicate<Elevator> predicateGoind = (elevator) -> elevator.currentFloor() > toFloor && elevator
        .getDirection().equals(
            Direction.DOWN);
    Predicate<Elevator> two = (elevator) -> elevator.currentFloor() < toFloor && elevator
        .getDirection().equals(
            Direction.UP);
    Optional<Elevator> candidate = elevatorList.stream().filter(predicateGoind).filter(two).findAny();
    if (candidate.isEmpty()) {
      Optional<Elevator> anyFree = elevatorList.stream()
          .filter(elevator -> elevator.getDirection().equals(Direction.NONE)).findAny();
      return anyFree.orElse(null);

    } else {
      return candidate.get();
    }


     */

    LOG.info(String.format("Number of elevators available %d", elevatorList.size()));
    for (Elevator exxx : elevatorList)
    {
      LOG.info(String.format("ElevatorId %d busy %s", exxx.getId(), exxx.isBusy()));
    }


    return elevatorList.stream().filter(elevator -> !elevator.isBusy()).findAny().get();
  }

  @Override
  public List<Elevator> getElevators() {
    return elevatorList;
  }

  @Override
  public void releaseElevator(Elevator elevator) {
  }

  @Override
  public void validateElevatorCallRequest(ElevatorCallRequest elevatorCallRequest)
      throws ElevatorCallRequestException {
    elevatorCallRequestValidator.validateElevatorCallRequest(elevatorCallRequest);
  }

  @Override
  public void executeElevatorCallRequest(ElevatorCallRequest elevatorCallRequest) {
    Optional<Elevator> optionalElevator = requestElevator(elevatorCallRequest);
    if(optionalElevator.isPresent()){
      executor.execute(()->{
        Elevator elevator = optionalElevator.get();
        elevator.requestElevatorMovement(elevatorCallRequest.getTargetFloor());
        elevator.run();
      });
    }

  }

  @Override
  public Optional<Elevator> requestElevator(ElevatorCallRequest elevatorCallRequest){
    return elevatorList.stream().filter(elevator -> !elevator.isBusy()).findFirst();
  }
}