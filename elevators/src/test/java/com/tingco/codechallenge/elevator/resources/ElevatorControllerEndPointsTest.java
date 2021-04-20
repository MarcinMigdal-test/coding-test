package com.tingco.codechallenge.elevator.resources;

import static com.tingco.codechallenge.elevator.config.TestConfig.ELEVATORS_AMOUNT_2;
import static com.tingco.codechallenge.elevator.config.TestConfig.FLOORS_AMOUNT_EQUAL_6;
import static com.tingco.codechallenge.elevator.config.TestConfig.FLOOR_2;
import static com.tingco.codechallenge.elevator.config.TestConfig.FLOOR_3;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.atMostOnce;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.tingco.codechallenge.elevator.api.ElevatorController;
import com.tingco.codechallenge.elevator.config.ElevatorConfiguration;
import com.tingco.codechallenge.elevator.impl.UserDirectionRequest;
import com.tingco.codechallenge.elevator.impl.exception.ElevatorRequestException;
import com.tingco.codechallenge.elevator.impl.validator.ElevatorRequestValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
    @Mock
    private ElevatorController elevatorController;

    private ElevatorControllerEndPoints elevatorControllerEndPoints;

    @BeforeEach
    void setUpElevatorControllerEndpoints() {
        elevatorControllerEndPoints = new ElevatorControllerEndPoints(elevatorConfiguration,
            elevatorRequestValidator, elevatorController);
    }

    @Test
    public void canReadStatus200For6floorsAnd2Elevators() {
        when(elevatorConfiguration.getFloorsNumber()).thenReturn(FLOORS_AMOUNT_EQUAL_6);
        when(elevatorConfiguration.getElevatorsNumber()).thenReturn(ELEVATORS_AMOUNT_2);

        Assertions
            .assertEquals(STATUS_CODE_VALUE_200, elevatorControllerEndPoints.getStatus().getStatusCodeValue());
        Assertions.assertTrue(elevatorControllerEndPoints.getStatus().hasBody());
    }

    @Test
    void canCallElevatorToFloor3() throws ElevatorRequestException {
        elevatorControllerEndPoints.callElevatorToFloor(FLOOR_3);

        verify(elevatorRequestValidator, atMostOnce()).validateCallRequestNoDirection(any());
        verify(elevatorController, atMostOnce()).executeElevatorCallRequestWithNoDirection(any());
    }

    @Test
    void canCallElevatorToFloor3WithDirectionDown() throws ElevatorRequestException {
        elevatorControllerEndPoints.callElevatorToFloorWithDirection(FLOOR_3, UserDirectionRequest.DOWN);

        verify(elevatorRequestValidator, atMostOnce()).validateCallRequestWithDirection(any());
        verify(elevatorController, atMostOnce()).executeElevatorCallRequestWithDirection(any());
    }

    @Test
    void canRequestElevatorFromFloor2ToFloor3() throws ElevatorRequestException {
        elevatorControllerEndPoints.requestElevatorToFloor(FLOOR_2, FLOOR_3);

        verify(elevatorRequestValidator, never()).validateMoveBetweenFloorsRequest(any());
    }
}