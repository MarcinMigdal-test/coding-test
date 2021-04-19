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
http://localhost:8080/rest/v1/call/3/UP
http://localhost:8080/rest/v1/call/5/UP
http://localhost:8080/rest/v1/call/5/UP
```
#### For furhter development/suggestions
```
1) put elevator into maintenance mode -> move to floor 0 and ignore all requests
2) when elevator stopped at a particular floor, request from that floor movement towards a chosen floor
3) add shutdown hook action wit proper message
4) I have implmented the easiest case: request is being sent to get an elevators at a particular floor
```
