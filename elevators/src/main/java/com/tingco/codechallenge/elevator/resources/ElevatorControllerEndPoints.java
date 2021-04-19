package com.tingco.codechallenge.elevator.resources;

import com.tingco.codechallenge.elevator.api.ElevatorController;
import com.tingco.codechallenge.elevator.config.ElevatorConfiguration;
import com.tingco.codechallenge.elevator.impl.ElevatorControllerImpl;
import com.tingco.codechallenge.elevator.impl.ElevatorFactory;
import com.tingco.codechallenge.elevator.impl.UserDirectionRequest;
import com.tingco.codechallenge.elevator.impl.exception.ElevatorRequestException;
import com.tingco.codechallenge.elevator.impl.request.ElevatorCallRequest;
import com.tingco.codechallenge.elevator.impl.validator.ElevatorRequestValidator;
import javax.validation.constraints.Min;
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
    private ElevatorRequestValidator elevatorRequestValidator;

    @Autowired
    public ElevatorControllerEndPoints(ElevatorConfiguration elevatorConfiguration) {
        this.elevatorConfiguration = elevatorConfiguration;
        elevatorRequestValidator = new ElevatorRequestValidator(
            elevatorConfiguration.getFloorsNumber());
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
            .format("Number of initialized elevators equals %d and floors equals %d",
                elevatorConfiguration.getElevatorsNumber(),
                elevatorConfiguration.getFloorsNumber()));
    }


    @PostMapping("/call/{floor}/{userDirectionRequest}")
    public ResponseEntity callElevatorToFloorWithDirection(
        @PathVariable("floor") @Min(0) int floor,
        @PathVariable("userDirectionRequest") UserDirectionRequest userDirectionRequest) {

        ElevatorCallRequest elevatorCallRequest = new ElevatorCallRequest(floor,
            userDirectionRequest);
        try {
            elevatorRequestValidator.validateElevatorCallRequest(elevatorCallRequest);
            elevatorController.executeElevatorCallRequest(elevatorCallRequest);
        } catch (ElevatorRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(elevatorCallRequest);
        }

        return ResponseEntity.status(HttpStatus.OK).body("Request accepted. Wait for execution");
    }

    @PostMapping("/move/{currentFloor}/{targetFloor}")
    public ResponseEntity requestElevatorToFloor(
        @PathVariable("currentFloor") @Min(0) int currentFloor,
        @PathVariable("targetFloor") @Min(0) int targetFloor
    ) {
        return ResponseEntity.status(HttpStatus.OK).body("Request accepted. Wait for execution");
    }


}