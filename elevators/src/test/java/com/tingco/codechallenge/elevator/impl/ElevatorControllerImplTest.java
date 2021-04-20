package com.tingco.codechallenge.elevator.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.tingco.codechallenge.elevator.api.ElevatorController;
import com.tingco.codechallenge.elevator.config.ElevatorConfiguration;
import com.tingco.codechallenge.elevator.config.TestConfig;
import com.tingco.codechallenge.elevator.impl.request.ElevatorCallRequestNoDirection;
import com.tingco.codechallenge.elevator.impl.request.ElevatorCallRequestWithDirection;
import java.util.concurrent.Executor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ElevatorControllerImplTest {

    @Mock
    private ElevatorConfiguration elevatorConfiguration;
    @Mock
    Executor executor;
    private ElevatorController elevatorController;

    @BeforeEach
    void setUpElevatorController() {
        when(elevatorConfiguration.getElevatorsNumber())
            .thenReturn(TestConfig.ELEVATORS_AMOUNT_2);
        elevatorController = new ElevatorControllerImpl(elevatorConfiguration, executor);
    }

    @Test
    void canInitTwoElevators() {
        assertEquals(TestConfig.ELEVATORS_AMOUNT_2, elevatorController.getElevators().size());
    }

    @Test
    void canExecuteElevatorCallRequestToFloor4AndDirectionUp() {
        //given
        ElevatorCallRequestWithDirection elevatorCallRequestWithDirection = new ElevatorCallRequestWithDirection(
            TestConfig.FLOOR_10, UserDirectionRequest.UP);
        //when
        elevatorController
            .executeElevatorCallRequestWithDirection(elevatorCallRequestWithDirection);
        //then
        verify(executor).execute(any());
    }

    @Test
    void canExecuteElevatorCallRequestToFloor4AndNoDirection() {
        //given
        ElevatorCallRequestNoDirection elevatorCallRequestWithNoDirection = new ElevatorCallRequestNoDirection(
            TestConfig.FLOOR_10);
        //when
        elevatorController
            .executeElevatorCallRequestWithNoDirection(elevatorCallRequestWithNoDirection);
        //then
        verify(executor).execute(any());
    }
}