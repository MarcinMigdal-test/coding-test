package com.tingco.codechallenge.elevator;

import static com.tingco.codechallenge.elevator.config.TestConfig.FLOOR_1;
import static com.tingco.codechallenge.elevator.config.TestConfig.FLOOR_10;
import static com.tingco.codechallenge.elevator.config.TestConfig.FLOOR_2;
import static com.tingco.codechallenge.elevator.config.TestConfig.FLOOR_4;

import com.tingco.codechallenge.elevator.endpoint.ElevatorControllerEndPoints;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


/**
 * Boiler plate test class to get up and running with a test faster.
 *
 * @author Sven Wesley
 */

@SpringBootTest
public class IntegrationTest {

    @Autowired
    private ElevatorControllerEndPoints elevatorControllerEndPoints;

    @Test
    public void simulateAnElevatorShaft() throws InterruptedException {
        elevatorControllerEndPoints.callElevatorToFloor(FLOOR_2);
        TimeUnit.SECONDS.sleep(4);
        elevatorControllerEndPoints.callElevatorToFloor(FLOOR_10);
        TimeUnit.SECONDS.sleep(4);
        elevatorControllerEndPoints.callElevatorToFloor(FLOOR_1);
        elevatorControllerEndPoints.callElevatorToFloor(FLOOR_4);
        TimeUnit.SECONDS.sleep(10);
    }
}