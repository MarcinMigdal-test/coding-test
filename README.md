# coding-test 
## Changes
```
Infrastructure
1 Upgraded libraries: Spring, Guava
2 Upgraded Maven complier plugin
3 Set Java Source and Target version to 14
4 Let awaitility version as it was (highest version). Newest one is moved to other GroupId and ArtifactId
----------------------------------------------------------------------------------------------------------------------------
Code:
Added package impl 
In api package marked all interfaces as public. By desing API must be public (in JAVA modules [Java 9 feature] API classes may be hidden in mutli-module project).
Here we have one module project - due to this fact not executed transition to Gradle.
Added additional classes 
```

### Testing
```
pass to CMD

java -jar JAR_NAME=  -Dcom.tingco.elevator.elevatorsNumber=20 -Dcom.tingco.elevator.floorsNumber=90

Mozilla FireFox postman

GET
http://localhost:8080/rest/v1/status

POST
http://localhost:8080/rest/v1/call/30
http://localhost:8080/rest/v1/call/50
http://localhost:8080/rest/v1/call/20
http://localhost:8080/rest/v1/call/15
http://localhost:8080/rest/v1/call/4
http://localhost:8080/rest/v1/call/45
http://localhost:8080/rest/v1/call/21
http://localhost:8080/rest/v1/call/54

```
#### For further development/suggestions
```
Put elevator into maintenance mode -> move to floor 0 and ignore all requests
When elevator stopped at a particular floor, request from that floor movement towards a chosen floor (someone enters elevator i clicks floor at control panel)
Use Lombok to get rid of Getters/Setters
Add integration tests
Add Spring profiles
Rename ElevatorControllerEndPoints (original one) to ElevatorControllerEndpoint
Introduce state machine to Elevator
Use eventBus to send notifications about elevators state to controller/WEBUI via websocket - > I have never used eventBus from Google and not used it in imlementation. I will have to learn how to use it.
````
