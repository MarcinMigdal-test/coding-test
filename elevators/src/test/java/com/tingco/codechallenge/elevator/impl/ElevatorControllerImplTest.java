package com.tingco.codechallenge.elevator.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.jayway.awaitility.Awaitility;
import com.tingco.codechallenge.elevator.api.ElevatorController;
import com.tingco.codechallenge.elevator.config.ElevatorConfiguration;
import com.tingco.codechallenge.elevator.config.TestConfig;
import com.tingco.codechallenge.elevator.impl.request.ElevatorCallRequestWithDirection;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ElevatorControllerImplTest {

    @Mock
    private ElevatorConfiguration elevatorConfiguration;
    private ElevatorController elevatorController;

    @BeforeEach
    void setUpElevatorController() {
        Mockito.when(elevatorConfiguration.getElevatorsNumber())
            .thenReturn(TestConfig.ELEVATORS_AMOUNT_2);
        elevatorController = new ElevatorControllerImpl(elevatorConfiguration);
    }

    @Test
    void canInitTwoElevators() {
        assertEquals(TestConfig.ELEVATORS_AMOUNT_2, elevatorController.getElevators().size());
    }

    @Test
    void canExecuteElevatorCallRequestToFloor4AndDirectionUp() {
        //given
        ElevatorCallRequestWithDirection elevatorCallRequestWithDirection = new ElevatorCallRequestWithDirection(
            TestConfig.FLOOR_4, UserDirectionRequest.UP);
        //when
        elevatorController.executeElevatorCallRequestWithDirection(elevatorCallRequestWithDirection);
        //then
        Awaitility.await().atMost(5, TimeUnit.SECONDS);
        Assertions.assertFalse(elevatorController.getElevators().get(1).isBusy());
        Assertions.assertTrue(elevatorController.getElevators().get(0).isBusy());
    }




}