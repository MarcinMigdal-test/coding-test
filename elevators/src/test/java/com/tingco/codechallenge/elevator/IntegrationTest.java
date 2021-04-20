package com.tingco.codechallenge.elevator;

import com.tingco.codechallenge.elevator.resources.ElevatorControllerEndPoints;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ProxySelector;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpClient.Version;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublisher;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.ByteBuffer;
import java.time.Duration;
import java.util.concurrent.Flow.Subscriber;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Boiler plate test class to get up and running with a test faster.
 *
 * @author Sven Wesley
 */

//@ExtendWith(SpringExtension.class)
//@ContextConfiguration(classes = {ElevatorApplication.class})
//@WebAppConfiguration
public class IntegrationTest {

    @Autowired
    private ElevatorControllerEndPoints elevatorControllerEndPoints;

    @Test
    public void simulateAnElevatorShaft() {
    }


    //@DisplayName("Created by Marcin and disabled for further development")
    //@Disabled
    @Test
    public void healthCheck() throws IOException, InterruptedException {
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

        request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8080/rest/v1/call/20"))
            .timeout(Duration.ofMinutes(2))
            .POST(bodyPublisher)
            .build();
         response = client.send(request, BodyHandlers.ofString());

        request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8080/rest/v1/call/10"))
            .timeout(Duration.ofMinutes(2))
            .POST(bodyPublisher).build();
        response = client.send(request, BodyHandlers.ofString());


        request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8080/rest/v1/call/5"))
            .timeout(Duration.ofMinutes(2))
            .POST(bodyPublisher)
            .build();
        response = client.send(request, BodyHandlers.ofString());

        request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8080/rest/v1/call/2"))
            .timeout(Duration.ofMinutes(2))
            .POST(bodyPublisher)
            .build();
        response = client.send(request, BodyHandlers.ofString());

    }


    /*
    @Test
    public void ping() {
        Assertions.assertEquals("pong", elevatorControllerEndPoints.ping());
    }


    @Test
    public void callElevatorToFloor_3(){
        ResponseEntity response = elevatorControllerEndPoints.callElevatorToFloorWithDirection(TestConfig.FLOOR_3, UserDirectionRequest.UP);
        Assertions.assertEquals(HttpStatus.OK,response.getStatusCode());
    }

    @Test
    public void callElevatorToFloor_10whichNotExists(){
        ResponseEntity response = elevatorControllerEndPoints.callElevatorToFloorWithDirection(TestConfig.FLOOR_10, UserDirectionRequest.UP);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST,response.getStatusCode());
    }

    @Test
    public void callElevatorToFloor_minus1whichNotExists(){
        ResponseEntity response = elevatorControllerEndPoints.callElevatorToFloorWithDirection(TestConfig.FLOOR_MINUS_1, UserDirectionRequest.UP);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST,response.getStatusCode());
    }
    */

    BodyPublisher bodyPublisher = new BodyPublisher() {
        @Override
        public long contentLength() {
            return 0;
        }

        @Override
        public void subscribe(Subscriber<? super ByteBuffer> subscriber) {

        }
    };


}
