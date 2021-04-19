package com.tingco.codechallenge.elevator.resources;

import com.tingco.codechallenge.elevator.api.ElevatorController;
import com.tingco.codechallenge.elevator.config.ElevatorConfiguration;
import com.tingco.codechallenge.elevator.impl.ElevatorControllerImpl;
import com.tingco.codechallenge.elevator.impl.ElevatorFactory;
import com.tingco.codechallenge.elevator.impl.UserDirectionRequest;
import com.tingco.codechallenge.elevator.impl.exception.ElevatorRequestException;
import com.tingco.codechallenge.elevator.impl.request.ElevatorCallRequestNoDirection;
import com.tingco.codechallenge.elevator.impl.request.ElevatorCallRequestWithDirection;
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
    private ElevatorRequestValidator elevatorRequestValidator;

    private ElevatorController elevatorController;

    @Autowired
    public ElevatorControllerEndPoints(ElevatorConfiguration elevatorConfiguration, ElevatorRequestValidator elevatorRequestValidator) {
        this.elevatorConfiguration = elevatorConfiguration;
        this.elevatorRequestValidator = elevatorRequestValidator;
        elevatorController = new ElevatorControllerImpl(
            ElevatorFactory.getElevators(elevatorConfiguration.getElevatorsNumber()),
            elevatorConfiguration.getFloorsNumber());
    }

    /**
     * Ping service to test if we are alive.
     *
     * @return String pong
     */
    @Deprecated(forRemoval = false,since = "Returning simple String does not look nicely")
    @RequestMapping(value = "/ping", method = RequestMethod.GET)
    public String ping() {
        return "pong";
    }

    /**
     * Endpoint returning application status - number of elevators and floors
     *
     * */
    @GetMapping("/status")
    public ResponseEntity getStatus() {
        return ResponseEntity.status(HttpStatus.OK).body(String
            .format("Number of initialized elevators equals %d and floors equals %d",
                elevatorConfiguration.getElevatorsNumber(),
                elevatorConfiguration.getFloorsNumber()));
    }

    /**
     * Call elevator to floor with  nodirection suggestion (to be used by UI) and optimization algorithm (to be implemented)
     * */
    @PostMapping("/call/{floor}")
    public ResponseEntity callElevatorToFloor(
        @PathVariable("floor") @Min(0) int floor) {
        ElevatorCallRequestNoDirection elevatorCallRequestNoDirection = new ElevatorCallRequestNoDirection(floor
            );
        try {
            elevatorRequestValidator.validateCallRequestNoDirection(elevatorCallRequestNoDirection);
            elevatorController.executeElevatorCallRequestWithNoDirection(elevatorCallRequestNoDirection);
        } catch (ElevatorRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                e.getMessage());
        }

        return ResponseEntity.status(HttpStatus.OK).body("Request accepted. Wait for execution");
    }

    /**
     * Call elevator to floor with direction suggestion (to be used by UI) and optimization algorithm (to be implemented)
     * */
    @PostMapping("/call/{floor}/{userDirectionRequest}")
    public ResponseEntity callElevatorToFloorWithDirection(
        @PathVariable("floor") @Min(0) int floor,
        @PathVariable("userDirectionRequest") UserDirectionRequest userDirectionRequest) {
        ElevatorCallRequestWithDirection elevatorCallRequestWithDirection = new ElevatorCallRequestWithDirection(floor,
            userDirectionRequest);
        try {
            elevatorRequestValidator.validateCallRequestWithDirection(elevatorCallRequestWithDirection);
            elevatorController.executeElevatorCallRequestWithDirection(elevatorCallRequestWithDirection);
        } catch (ElevatorRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                e.getMessage());
        }

        return ResponseEntity.status(HttpStatus.OK).body("Request accepted. Wait for execution");
    }

    /**
     *  Send order to  elevator after user gets on the board. For further development
     * */
    @PostMapping("/move/{currentFloor}/{targetFloor}")
    public ResponseEntity requestElevatorToFloor(
        @PathVariable("currentFloor") @Min(0) int currentFloor,
        @PathVariable("targetFloor") @Min(0) int targetFloor
    ) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("For further development.Not implemented");
    }


}