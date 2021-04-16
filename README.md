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
