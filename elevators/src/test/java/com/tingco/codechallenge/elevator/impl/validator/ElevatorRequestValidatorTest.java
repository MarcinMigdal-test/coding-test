package com.tingco.codechallenge.elevator.impl.validator;

import static com.tingco.codechallenge.elevator.config.TestConfig.FLOORS_AMOUNT_EQUAL_6;
import static com.tingco.codechallenge.elevator.config.TestConfig.FLOOR_1;
import static com.tingco.codechallenge.elevator.config.TestConfig.FLOOR_10;
import static com.tingco.codechallenge.elevator.config.TestConfig.FLOOR_3;
import static com.tingco.codechallenge.elevator.config.TestConfig.FLOOR_5;
import static com.tingco.codechallenge.elevator.config.TestConfig.FLOOR_MINUS_1;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.tingco.codechallenge.elevator.config.ElevatorConfiguration;
import com.tingco.codechallenge.elevator.impl.UserDirectionRequest;
import com.tingco.codechallenge.elevator.impl.exception.ElevatorRequestException;
import com.tingco.codechallenge.elevator.impl.request.ElevatorCallRequestNoDirection;
import com.tingco.codechallenge.elevator.impl.request.ElevatorCallRequestWithDirection;
import com.tingco.codechallenge.elevator.impl.request.ElevatorMoveBetweenFloorsRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ElevatorRequestValidatorTest {

    @Mock
    private ElevatorConfiguration elevatorConfiguration;
    private ElevatorRequestValidator elevatorRequestValidator;

    @BeforeEach
    public void setupFloorsAmountAsSix() {
        Mockito.when(elevatorConfiguration.getFloorsNumber()).thenReturn(FLOORS_AMOUNT_EQUAL_6);
        elevatorRequestValidator = new ElevatorRequestValidator(elevatorConfiguration);
    }

    @Test
    void acceptProperRequest_targetFloor_3_whereFloors_6()
        throws ElevatorRequestException {
        ElevatorCallRequestNoDirection elevatorCallRequestNoDirection = new ElevatorCallRequestNoDirection(
            FLOOR_1);

        elevatorRequestValidator.validateCallRequestNoDirection(elevatorCallRequestNoDirection);
    }

    @Test
    void canRejectImproperRequest_targetFloor_10_whereFloors_6() {
        ElevatorCallRequestNoDirection elevatorCallRequestNoDirection = new ElevatorCallRequestNoDirection(
            FLOOR_10);

        assertThrows(ElevatorRequestException.class,
            () -> elevatorRequestValidator
                .validateCallRequestNoDirection(elevatorCallRequestNoDirection));
    }

    @Test
    void canRejectImproperRequest_targetFloor_minus_1_whereFloors_6() {
        ElevatorCallRequestNoDirection elevatorCallRequestNoDirection = new ElevatorCallRequestNoDirection(
            FLOOR_MINUS_1);

        assertThrows(ElevatorRequestException.class,
            () -> elevatorRequestValidator
                .validateCallRequestNoDirection(elevatorCallRequestNoDirection));
    }

    @Test
    void acceptProperRequest_targetFloor_1_whereFloors_6()
        throws ElevatorRequestException {
        ElevatorCallRequestWithDirection elevatorCallRequestWithDirection = new ElevatorCallRequestWithDirection(
            FLOOR_1,
            UserDirectionRequest.UP
        );

        elevatorRequestValidator.validateCallRequestWithDirection(elevatorCallRequestWithDirection);
    }

    @Test
    void canRejectImproperRequest_improper_currentFloor_minus_1_UP_whereFloors6() {
        ElevatorCallRequestWithDirection elevatorCallRequestWithDirection = new ElevatorCallRequestWithDirection(
            FLOOR_MINUS_1,
            UserDirectionRequest.UP);

        assertThrows(ElevatorRequestException.class,
            () -> elevatorRequestValidator
                .validateCallRequestWithDirection(elevatorCallRequestWithDirection));
    }

    @Test
    void canRejectImproperRequest_targetFloor_10_UP_whereFloors_6() {
        ElevatorCallRequestWithDirection elevatorCallRequestWithDirection = new ElevatorCallRequestWithDirection(
            FLOOR_10,
            UserDirectionRequest.UP);

        assertThrows(ElevatorRequestException.class,
            () -> elevatorRequestValidator
                .validateCallRequestWithDirection(elevatorCallRequestWithDirection));
    }

    @Test
    void canAcceptProperRequest_from_3_to_5_whereFloors_6()
        throws ElevatorRequestException {
        ElevatorMoveBetweenFloorsRequest elevatorMoveBetweenFloorsRequest = new ElevatorMoveBetweenFloorsRequest(
            FLOOR_3, FLOOR_5);

        elevatorRequestValidator.validateMoveBetweenFloorsRequest(elevatorMoveBetweenFloorsRequest);
    }

    @Test
    void canRejectImproperRequest_from_minus_1_to_5_whereFloors_6() {
        ElevatorMoveBetweenFloorsRequest elevatorMoveBetweenFloorsRequest = new ElevatorMoveBetweenFloorsRequest(
            FLOOR_MINUS_1, FLOOR_5);

        assertThrows(ElevatorRequestException.class,
            () -> elevatorRequestValidator
                .validateMoveBetweenFloorsRequest(elevatorMoveBetweenFloorsRequest));
    }
}