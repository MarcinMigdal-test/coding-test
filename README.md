# coding-test 

## Changes
```
1 Upgraded libraries: Spring, Guava
2 Upgraded Maven complier plugin
3 Set Java Source and Target version to 14
4 Let awaitility version as it was (highest version). Newest one is moved to other GroupId and ArtifactId
----------------------------------------------------------------------------------------------------------------------------
Added package impl
In api package marked all interfaces as public. By desing API must be public (in JAVA modules [Java 9 feature] API classes may be hidden in mutli-module project).
Here we have one module project - due to this fact not executeds transition to Gradle.

```
### Testing
```
pass to CMD

java -jar JAR_NAME=  -Dcom.tingco.elevator.numberofelevators=20 -Dcom.tingco.elevator.numberoffloors=90

Mozilla FireFox postman

GET
http://localhost:8080/rest/v1/status

POST
http://localhost:8080/rest/v1/call/3/
http://localhost:8080/rest/v1/call/5/
http://localhost:8080/rest/v1/call/10
```
#### For furhter development/suggestions
```
put elevator into maintenance mode -> move to floor 0 and ignore all requests
when elevator stopped at a particular floor, request from that floor movement towards a chosen floor
I have implmented the easiest case: request is being sent to get an elevators at a particular floor
Use Lombok to get rid of Getters/Setters
Add integration tests
Add Spring profiles
Rename ElevatorControllerEndPoints (original one) to ElevatorControllerEndpoint
Introduce state machine to Elevator
use eventBus to send notifications about elevators state to controller/WEBUI via websocket
````
