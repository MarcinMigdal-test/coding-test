package com.tingco.codechallenge.elevator.impl;

import com.tingco.codechallenge.elevator.api.Elevator;
import com.tingco.codechallenge.elevator.api.Elevator.Direction;
import com.tingco.codechallenge.elevator.api.ElevatorController;
import com.tingco.codechallenge.elevator.impl.exception.ElevatorCallRequestException;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ElevatorControllerImpl implements ElevatorController {

  private final Logger LOG = LoggerFactory
      .getLogger(ElevatorControllerImpl.class.getCanonicalName());

  private ElevatorCallRequestValidator elevatorCallRequestValidator;
  private List<Elevator> elevatorList;
  private final int floorsNumber;


  public ElevatorControllerImpl(List<Elevator> elevatorList, int floorsNumber) {
    this.elevatorList = elevatorList;
    this.floorsNumber = floorsNumber;
    elevatorCallRequestValidator = new ElevatorCallRequestValidator(floorsNumber);
  }

  @Override
  public Elevator requestElevator(int toFloor) {
    Predicate<Elevator> one = (elevator) -> elevator.currentFloor() > toFloor && elevator
        .getDirection().equals(
            Direction.DOWN);
    Predicate<Elevator> two = (elevator) -> elevator.currentFloor() < toFloor && elevator
        .getDirection().equals(
            Direction.UP);
    Optional<Elevator> candidate = elevatorList.stream().filter(one).filter(two).findAny();
    if (candidate.isEmpty()) {
      Optional<Elevator> anyFree = elevatorList.stream()
          .filter(elevator -> elevator.getDirection().equals(Direction.NONE)).findAny();
      return anyFree.orElse(null);

    } else {
      return candidate.get();
    }

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
    Elevator elevator = requestElevator(elevatorCallRequest.getCurrentFloor());
    elevator.moveElevator(elevatorCallRequest.getTargetFloor());
  }
}