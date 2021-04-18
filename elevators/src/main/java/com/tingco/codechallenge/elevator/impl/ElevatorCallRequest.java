package com.tingco.codechallenge.elevator.impl;

public class ElevatorCallRequest {

  private final int currentFloor;
  private UserDirectionRequest userDirectionRequest;

  public ElevatorCallRequest(int currentFloor, UserDirectionRequest userDirectionRequest){
    this.currentFloor = currentFloor;
    this.userDirectionRequest = userDirectionRequest;
  }

  public int getCurrentFloor(){
    return currentFloor;
  }

  public UserDirectionRequest getUserDirectionRequest() {return userDirectionRequest;}
}
