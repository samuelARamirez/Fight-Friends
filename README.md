# Fight Friends

Fight Friends is an object-oriented Java game where players explore different biomes, collect animal companions, and battle other trainers. Each animal has unique stats, while merchants offer items to support players along their journey.

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
    │   │   ├── characters/            # Character, Trainer, Player, Merchant, and factory
    │   │   ├── friends/               # Friend and animal subclasses
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

Use JDK 21 or newer. Open this folder as a Gradle project in your IDE.
From this folder (use `gradlew.bat` on Windows):

```sh
./gradlew build    # Compile and run tests
./gradlew test     # Run JUnit and Cucumber tests
```

Add your game classes under `src/main/java/fightfriends` and matching JUnit tests
under `src/test/java/fightfriends`. Exploration and combat rules are left to implement.

The Cucumber runner allows an empty suite until the first scenario is written.
Add a scenario to `gamePlay.feature` and implement its steps in
`fightfriends.stepdefs` to begin testing. Cucumber reports go to
`build/reports/cucumber/report.html`; Gradle test reports go to
`build/reports/tests/test/index.html`.

## Domain skeleton

- `World` holds areas with `addArea`, `removeArea`, and `getAreas`.
- `Trainer` tracks carried friends with `addFriend`, `removeFriend`, and
  `getFriends`. `Player` extends `Trainer` and tracks visited areas with
  `visitArea`, `hasVisited`, and `getVisitedAreas`, plus a backpack and gold balance.
- Abstract `Friend` holds a name, health, attack, and speed and declares `getSpecies()`.
  Animal constructors accept a name for base stats or `(name, health, attack, speed)` for custom stats; each stat has a getter
  and setter inherited from `Friend`. `Wolf`, `Bear`,
  `Fox`, and `Elephant` provide the animal implementations.
- Abstract `Area` holds a name and declares `getBiome()`. `Forest`, `Desert`,
  and `Tundra` provide the first biome implementations.

World areas, carried friends, and visited areas are returned as read-only snapshots. Adding the same friend object or visiting
the same area object twice does not create duplicates. Separate instances remain
distinct even if their names match; reuse area instances when revisiting places.
The game entry point is still to be implemented.

## Test coverage

JUnit tests cover collection ordering, duplicate instances, removal, visit
history, immutable snapshots, animal stats, and artifact creation.

Run `./gradlew clean build` to run all tests and enforce 100% method coverage
for every production class with JaCoCo. `./gradlew test` also generates an HTML
coverage report at `build/reports/jacoco/test/html/index.html`. The latest review
found one failing purchase test because pickup is unfinished; the 100% coverage
target is not currently met.

## Design patterns

- **Simple Factory (implemented):** `CharacterFactory` creates trainers, players,
  and merchants; `ArtifactFactory` creates food and potions. They centralize
  construction and randomized defaults while returning `Character` or `Artifact`
  abstractions to callers.
- **Strategy (planned):** A battle controller will receive a `CombatStrategy`
  interface through its constructor. Aggressive and defensive implementations
  will choose combat actions independently of the animal's stats, allowing tactics
  to change without adding animal subclasses.
- **Observer (planned):** The game controller will publish events for purchases,
  area visits, and battles to registered listeners. Logging and future UI updates
  can then react without putting display logic inside the game rules.

The current code uses abstractions such as `Area`, `Friend`, and `Collectible`.
Pricing calls `Collectible.getPower()` polymorphically, and `Merchant` accepts its
inventory through constructor injection.

## Animal base stats

| Animal | Health | Attack | Speed | Role |
| --- | ---: | ---: | ---: | --- |
| Elephant | 180 | 8 | 2 | Durable, slow, low damage |
| Fox | 70 | 8 | 10 | Fast, fragile, low damage |
| Bear | 120 | 12 | 5 | Moderate damage and speed, sturdy |
| Wolf | 90 | 14 | 8 | Fast attacker with less health |

