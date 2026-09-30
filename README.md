# Fight Friends

Java project skeleton following the structure of the CSCI 4448 homework assignments.
Uses the same Gradle wrapper, Logback, JUnit 5, and Cucumber dependencies as HW5.

```text
Fight-Friends/
├── build.gradle
├── settings.gradle
├── gradlew / gradlew.bat
├── gradle/wrapper/
└── src/
    ├── main/
    │   ├── java/fightfriends/
    │   │   ├── FightFriends.java       # Entry point and game orchestration
    │   │   ├── characters/            # Character types and factories
    │   │   └── artifacts/             # Items and factories
    │   └── resources/logback.xml
    └── test/
        ├── java/fightfriends/
        │   ├── RunCucumberTest.java
        │   ├── characters/
        │   ├── artifacts/
        │   └── stepdefs/              # Cucumber step definitions
        └── resources/
            ├── cucumber.properties
            └── features/gamePlay.feature
```

## Getting started

Use JDK 17 or newer. Open this folder as a Gradle project in your IDE.
From this folder (use `gradlew.bat` on Windows):

```sh
./gradlew run      # Run the starter entry point
./gradlew build    # Compile and run tests
./gradlew test     # Run JUnit and Cucumber tests
```

Add your game classes under `src/main/java/fightfriends` and matching JUnit tests
under `src/test/java/fightfriends`. The package documentation files keep the
empty packages in Git. No game rules or domain classes have been implemented.

The Cucumber runner allows an empty suite until the first scenario is written.
Add a scenario to `gamePlay.feature` and implement its steps in
`fightfriends.stepdefs` to begin testing. Cucumber reports go to
`build/reports/cucumber/report.html`; Gradle test reports go to
`build/reports/tests/test/index.html`.
