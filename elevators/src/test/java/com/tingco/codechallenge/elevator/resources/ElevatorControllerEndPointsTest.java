package com.tingco.codechallenge.elevator.resources;

import com.tingco.codechallenge.elevator.config.ElevatorApplication;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

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

}
