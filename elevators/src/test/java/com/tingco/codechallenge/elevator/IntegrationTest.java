package com.tingco.codechallenge.elevator;

import com.tingco.codechallenge.elevator.impl.ElevatorControllerImplTest;
import com.tingco.codechallenge.elevator.impl.UserDirectionRequest;
import com.jayway.awaitility.Awaitility;
import com.tingco.codechallenge.elevator.config.FloorsElevatorsConfig;
import com.tingco.codechallenge.elevator.resources.ElevatorControllerEndPoints;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ProxySelector;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpClient.Redirect;
import java.net.http.HttpClient.Version;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
public class IntegrationTest {

    @Autowired
    private ElevatorControllerEndPoints elevatorControllerEndPoints;

    @Test
    public void simulateAnElevatorShaft() {
    }

    @DisplayName("Created by Marcin and disabled for further development")
    @Disabled
    @Test
    public void helathCheck() throws IOException, InterruptedException {
        Awaitility.await().atMost(10, TimeUnit.SECONDS);
        HttpClient client = HttpClient.newBuilder()
            .version(Version.HTTP_1_1)
            .connectTimeout(Duration.ofSeconds(20))
            .proxy(ProxySelector.of(new InetSocketAddress("localhost", 8080)))
            .build();


        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8080/rest/v1/status"))
            .timeout(Duration.ofMinutes(2))
            .GET()
            .build();

        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());

        System.out.println(response.statusCode());
        System.out.println(response.body());

    }
    @Test
    public void ping() {
        Assertions.assertEquals("pong", elevatorControllerEndPoints.ping());
    }

    @Test
    public void callElevatorToFloor_3(){
      //ResponseEntity response = elevatorControllerEndPoints.callElevatorToFloor(FloorsElevatorsConfig.FLOOR_3);
      //  Assertions.assertEquals(HttpStatus.OK,response.getStatusCode());
    }

    @Test
    public void callElevatorToFloor_1567whichNotExists(){
        //ResponseEntity response = elevatorControllerEndPoints.callElevatorToFloor(FloorsElevatorsConfig.FLOOR_152);
        //Assertions.assertEquals(HttpStatus.BAD_REQUEST,response.getStatusCode());
    }

    @Test
    public void callElevatorToFloor_minus1whichNotExists(){
        //ResponseEntity response = elevatorControllerEndPoints.callElevatorToFloor(FloorsElevatorsConfig.FLOOR_MINUS_1);
        //Assertions.assertEquals(HttpStatus.BAD_REQUEST,response.getStatusCode());
    }




}
