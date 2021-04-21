# coding-test 
## Changes comparing to initial commit
```
Infrastructure
1 Upgraded libraries: Spring, Guava
2 Upgraded Maven complier plugin
3 Set Java Source and Target version to 14
4 Let awaitility version as it was (highest version). Newest one is moved to other GroupId and ArtifactId
----------------------------------------------------------------------------------------------------------------------------
Code:
Added own packages and renamed existing ones to be proper ones. 
In api package marked all interfaces as public. By desing API must be public (in JAVA modules [Java 9 feature] API classes may be hidden in mutli-module project).
Here we have one module project - due to this fact not executed transition to Gradle.
Added additional classes and logic.
```
### Testing
```
Example simulation is included in IntegrattionTest.

Default run (with 5 elevators and 60 floors):
java -jar elevator-1.0-SNAPSHOT.jar

Custom run (with defined number of elevators and floors)
java -jar  -Dcom.tingco.elevator.elevatorsNumber=10 -Dcom.tingco.elevator.floorsNumber=3 elevator-1.0-SNAPSHOT.jar

Mozilla FireFox postman

GET - verification endpoint showing number of elevators and floors initaited in application 
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
### Verification
```
You can observe elevators behavior in console in logs. 
Example entries attached in log.log file.
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
