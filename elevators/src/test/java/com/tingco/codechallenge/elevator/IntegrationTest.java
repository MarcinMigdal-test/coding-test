package com.tingco.codechallenge.elevator;

import com.tingco.codechallenge.elevator.endpoint.ElevatorControllerEndPoints;
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
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @DisplayName("Test not working during Maven invocation")
    @Disabled
    @Test
    public void sendRequestsViaRestApi() throws IOException, InterruptedException {
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
        Assertions.assertEquals(HttpStatus.OK, response.statusCode());

        request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8080/rest/v1/call/10"))
            .timeout(Duration.ofMinutes(2))
            .POST(bodyPublisher).build();
        response = client.send(request, BodyHandlers.ofString());
        Assertions.assertEquals(HttpStatus.OK, response.statusCode());

        request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8080/rest/v1/call/5"))
            .timeout(Duration.ofMinutes(2))
            .POST(bodyPublisher)
            .build();
        response = client.send(request, BodyHandlers.ofString());
        Assertions.assertEquals(HttpStatus.OK, response.statusCode());

        request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8080/rest/v1/call/2"))
            .timeout(Duration.ofMinutes(2))
            .POST(bodyPublisher)
            .build();
        response = client.send(request, BodyHandlers.ofString());
        Assertions.assertEquals(HttpStatus.OK, response.statusCode());

    }

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