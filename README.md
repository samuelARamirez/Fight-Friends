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
    │   │   ├── World.java              # Collection of explorable areas
    │   │   ├── characters/            # Trainer, Friend, and animal subclasses
    │   │   ├── areas/                 # Area and biome subclasses
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
empty packages in Git. Exploration and combat rules are left to implement.

The Cucumber runner allows an empty suite until the first scenario is written.
Add a scenario to `gamePlay.feature` and implement its steps in
`fightfriends.stepdefs` to begin testing. Cucumber reports go to
`build/reports/cucumber/report.html`; Gradle test reports go to
`build/reports/tests/test/index.html`.

## Domain skeleton

- `World` holds areas with `addArea`, `removeArea`, and `getAreas`.
- `Trainer` tracks carried friends with `addFriend`, `removeFriend`, and
  `getFriends`, and visited areas with `visitArea`, `hasVisited`, and
  `getVisitedAreas`.
- Abstract `Friend` holds a name and declares `getSpecies()`. `Wolf`, `Bear`,
  and `Fox` provide the first animal implementations.
- Abstract `Area` holds a name and declares `getBiome()`. `Forest`, `Desert`,
  and `Tundra` provide the first biome implementations.

Collections are read-only snapshots. Adding the same friend object or visiting
the same area object twice does not create duplicates. Separate instances remain
distinct even if their names match; reuse area instances when revisiting places.
The entry point demonstrates a trainer carrying a fox and visiting a forest.

## Test coverage

Each production class has a matching JUnit test class, including `Friend` and
`Area` through small test subclasses. Tests cover collection ordering, duplicate
instances, removal, visit history, immutable snapshots, animal and biome types,
and the starter application's logged output.

Run `./gradlew clean build` to run all tests and enforce 100% method coverage
for every production class with JaCoCo. `./gradlew test` also generates an HTML
coverage report at `build/reports/jacoco/test/html/index.html`.
