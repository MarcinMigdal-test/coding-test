package com.tingco.codechallenge.elevator.resources;

import com.tingco.codechallenge.elevator.api.ElevatorController;
import com.tingco.codechallenge.elevator.config.ElevatorConfiguration;
import com.tingco.codechallenge.elevator.impl.ElevatorCallRequest;
import com.tingco.codechallenge.elevator.impl.ElevatorControllerImpl;
import com.tingco.codechallenge.elevator.impl.ElevatorFactory;
import com.tingco.codechallenge.elevator.impl.UserDirectionRequest;
import com.tingco.codechallenge.elevator.impl.exception.ElevatorCallRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.constraints.Min;
/**
 * Rest Resource.
 *
 * @author Sven Wesley
 */
@ComponentScan
@RestController
@RequestMapping("/rest/v1")
public final class ElevatorControllerEndPoints {

  private ElevatorConfiguration elevatorConfiguration;
  private ElevatorController elevatorController;

  @Autowired
  public ElevatorControllerEndPoints(ElevatorConfiguration elevatorConfiguration) {
    this.elevatorConfiguration = elevatorConfiguration;
    elevatorController = new ElevatorControllerImpl(
        ElevatorFactory.getElevators(elevatorConfiguration.getElevatorsNumber()),
        elevatorConfiguration.getFloorsNumber());
  }

  /**
   * Ping service to test if we are alive.
   *
   * @return String pong
   */
  @Deprecated(forRemoval = true, since = "Now")
  @RequestMapping(value = "/ping", method = RequestMethod.GET)
  public String ping() {
    return "pong";
  }

  @GetMapping("/status")
  public ResponseEntity getStatus() {
    return ResponseEntity.status(HttpStatus.OK).body(String
        .format("Number of initialized elevators equals %d and floors equals %d" ,
            elevatorConfiguration.getElevatorsNumber(), elevatorConfiguration.getFloorsNumber()));
  }


  @PostMapping("/{currentFloor}/{userDirectionRequest}/{targetFloor}")
  public ResponseEntity liftFromFloorToFloor(
      @PathVariable("currentFloor") @Min(0) int currentFloor,
      @PathVariable("userDirectionRequest") UserDirectionRequest userDirectionRequest,
      @PathVariable("targetFloor") @Min(0) int targetFloor) {

    ElevatorCallRequest elevatorCallRequest = new ElevatorCallRequest(currentFloor,userDirectionRequest,targetFloor);
    try {
      elevatorController.validate(elevatorCallRequest);
    } catch (ElevatorCallRequestException e) {
      return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(elevatorCallRequest);
    }

    return ResponseEntity.status(HttpStatus.OK).body("Request accepted. Wait for execution");
  }


}
