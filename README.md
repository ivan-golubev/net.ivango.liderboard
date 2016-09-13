Leaderboard
-----------

Simple leaderboard service, supporting these calls:

1. get default leaderboard (first 50 players with largest for this day)
2. get ranged leaderboard (from place x to place y)
3. get timed leaderboard (from timestamp x to timestamp y)
4. ban/unban players by id

Tech stack
----------
h2 - embedded database
jetty - embedded app server
jersey - REST API
Guice - DI
genson - object-to-JSON mapping
spring-jdbc – object-relation mapping and base initialization
lombok - boilderplate codegen
testng - integration tests

Installation
-------------------------
1. Either download a zip or perform a git checkout from [github][1].
2. Install [JDK 8][2].
3. Install [Gradle][3].
4. [Set up][4] the environment variable: GRADLE\_HOME.

Usage
-----

Execute this in console (cd to the project root directory first) or in you favourite IDE:

`> gradle clean test`

This will model launch various test scenarios.

To get a verbose output switch from INFO to DEBUG in src/main/resources/log4j.properties

To launch the server execute:

`> gradle clean run`

Then you may issue requests from curl/browser/postman/etc., ex:

 `curl http://localhost:8080/leaderboard`

[1]: https://github.com/ivan-golubev/chat.server
[2]: http://www.oracle.com/technetwork/java/javase/downloads
[3]: https://gradle.org/gradle-download/
[4]: https://docs.gradle.org/current/userguide/installation.html