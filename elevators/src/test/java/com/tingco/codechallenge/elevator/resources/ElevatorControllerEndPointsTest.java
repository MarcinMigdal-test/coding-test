package com.tingco.codechallenge.elevator.resources;

import com.tingco.codechallenge.elevator.ElevatorApplication;
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
    public void giveliftFrom0to3(){
        ResponseEntity response = endPoints.liftFromFloorToFloor(0, UserDirectionRequest.UP,3);
        Assertions.assertEquals(HttpStatus.OK,response.getStatusCode());
    }

    @Test
    public void giveliftFrom0to10(){
        ResponseEntity response = endPoints.liftFromFloorToFloor(0, UserDirectionRequest.UP,10);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST,response.getStatusCode());
    }

    @Test
    public void giveliftFromMinus1to2(){
        ResponseEntity response = endPoints.liftFromFloorToFloor(-1, UserDirectionRequest.UP,10);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST,response.getStatusCode());
    }
}