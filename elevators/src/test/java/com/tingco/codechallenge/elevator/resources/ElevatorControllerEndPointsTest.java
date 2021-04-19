package com.tingco.codechallenge.elevator.resources;

import com.tingco.codechallenge.elevator.config.ElevatorConfiguration;
import com.tingco.codechallenge.elevator.impl.UserDirectionRequest;
import com.tingco.codechallenge.elevator.impl.exception.ElevatorRequestException;
import com.tingco.codechallenge.elevator.impl.validator.ElevatorRequestValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.tingco.codechallenge.elevator.config.TestConfig.*;

/**
 * Boiler plate test class to get up and running with a test faster.
 *
 * @author Sven Wesley
 */

@ExtendWith(MockitoExtension.class)
public class ElevatorControllerEndPointsTest {

    private final int STATUS_CODE_VALUE_200 = 200;

    @Mock
    private ElevatorConfiguration elevatorConfiguration;
    @Mock
    private ElevatorRequestValidator elevatorRequestValidator;

    private ElevatorControllerEndPoints testedObject;

    @BeforeEach
    void setUp(){
        Mockito.when(elevatorConfiguration.getFloorsNumber()).thenReturn(FLOORS_AMOUNT_EQUAL_6);
        Mockito.when(elevatorConfiguration.getElevatorsNumber()).thenReturn(ELEVATORS_AMOUNT_2);
        testedObject = new ElevatorControllerEndPoints(elevatorConfiguration,elevatorRequestValidator);
    }

    @Test
    void getStatus(){
        Assertions.assertEquals(STATUS_CODE_VALUE_200, testedObject.getStatus().getStatusCodeValue());
        Assertions.assertTrue(testedObject.getStatus().hasBody());
    }

    @Test
    void callElevatorToFloor() throws ElevatorRequestException {
        testedObject.callElevatorToFloor(FLOOR_3);
        Mockito.verify(elevatorRequestValidator,Mockito.atMostOnce()).validateCallRequestNoDirection(Mockito.any());
    }

    @Test
    void callElevatorToFloorWithDirection() throws ElevatorRequestException {
        testedObject.callElevatorToFloorWithDirection(FLOOR_3, UserDirectionRequest.DOWN);
        Mockito.verify(elevatorRequestValidator, Mockito.atMostOnce()).validateCallRequestWithDirection(Mockito.any());
    }

    @Test
    void requestElevatorToFloor() throws ElevatorRequestException {
        testedObject.requestElevatorToFloor(FLOOR_2,FLOOR_3);
        Mockito.verify(elevatorRequestValidator,Mockito.never()).validateMoveBetweenFloorsRequest(Mockito.any());
    }
}