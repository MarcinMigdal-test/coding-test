package com.tingco.codechallenge.elevator.resources;

import com.tingco.codechallenge.elevator.ElevatorApplication;
import com.tingco.codechallenge.elevator.config.FloorsElevatorsConfig;
import com.tingco.codechallenge.elevator.impl.UserDirectionRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import org.springframework.http.HttpStatus;

/**
 * Boiler plate test class to get up and running with a test faster.
 *
 * @author Sven Wesley
 */

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ElevatorApplication.class})
@WebAppConfiguration
public class ElevatorControllerEndPointsTest {


    @Autowired
    private ElevatorControllerEndPoints endPoints;

    @Test
    public void ping() {
        Assertions.assertEquals("pong", endPoints.ping());
    }

    @Test
    public void callElevatorToFloor_3(){
        ResponseEntity response = endPoints.callElevatorToFloorWithDirection(FloorsElevatorsConfig.FLOOR_3, UserDirectionRequest.UP);
        Assertions.assertEquals(HttpStatus.OK,response.getStatusCode());
    }

    @Test
    public void callElevatorToFloor_10whichNotExists(){
        ResponseEntity response = endPoints.callElevatorToFloorWithDirection(FloorsElevatorsConfig.FLOOR_10, UserDirectionRequest.UP);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST,response.getStatusCode());
    }

    @Test
    public void callElevatorToFloor_minus1whichNotExists(){
        ResponseEntity response = endPoints.callElevatorToFloorWithDirection(FloorsElevatorsConfig.FLOOR_MINUS_1, UserDirectionRequest.UP);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST,response.getStatusCode());
    }
}