package com.tingco.codechallenge.elevator.impl;

public class ElevatorCallRequest {

  private final int currentFloor;
  private UserDirectionRequest userDirectionRequest;
  private final int targetFloor;

  public ElevatorCallRequest(int currentFloor, UserDirectionRequest userDirectionRequest, int targetFloor){
    this.currentFloor = currentFloor;
    this.userDirectionRequest = userDirectionRequest;
    this.targetFloor = targetFloor;
  }

  public int getCurrentFloor(){
    return currentFloor;
  }

  public int getTargetFloor(){
    return targetFloor;
  }

  public UserDirectionRequest getUserDirectionRequest() {return userDirectionRequest;}

}
