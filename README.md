Liderboard
------------------------------------

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

[1]: https://github.com/ivan-golubev/chat.server
[2]: http://www.oracle.com/technetwork/java/javase/downloads
[3]: https://gradle.org/gradle-download/
[4]: https://docs.gradle.org/current/userguide/installation.html