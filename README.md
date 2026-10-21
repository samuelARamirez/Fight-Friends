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
    │   │   ├── FightFriends.java      # Planned game orchestration
    │   │   ├── World.java             # Collection of explorable areas
    │   │   ├── Collectible.java       # Shared interface for collectible objects
    │   │   ├── Gold.java
    │   │   ├── characters/            # Character, Trainer, Player, Merchant, CharacterFactory
    │   │   ├── friends/               # Friend, Bear, Elephant, Fox, Wolf
    │   │   ├── areas/                 # Area, Forest, Desert, Tundra
    │   │   └── artifacts/             # Artifact, Food, FriendPotion, ArtifactFactory
    │   └── resources/logback.xml
    └── test/
        ├── java/fightfriends/
        │   ├── RunCucumberTest.java
        │   ├── FightFriendsTest.java  # Placeholder
        │   ├── WorldTest.java
        │   ├── characters/            # MerchantTest, PlayerTest, TrainerTest
        │   ├── friends/               # Friend and animal tests
        │   ├── areas/                 # Area and biome tests
        │   ├── artifacts/             # ArtifactTest
        │   └── stepdefs/              # Package placeholder for Cucumber steps
        └── resources/
            ├── cucumber.properties
            └── features/gamePlay.feature
```
## Mid-project review

### Design patterns — 3 points

#### Factory Pattern

We use `CharacterFactory` and `ArtifactFactory` to create characters and artifacts.
These factories handle construction and randomized defaults so the rest of the
game can work with the `Character` and `Artifact` abstractions.

#### Strategy Pattern

We plan to use strategies to control how friends choose actions during combat.
Each friend will receive a strategy, such as aggressive or defensive, so its
behavior can change without changing its animal class.

#### Template Pattern

We plan to define a shared fighting sequence in the abstract `Friend` class,
with steps such as selecting an attack, calculating damage, and applying damage.
Animal subclasses will customize individual steps while following the same
overall sequence. Strategies will choose actions within that sequence.

#### Observer Pattern

We plan to publish game events to listeners that update the UI. Separate listeners
will handle display changes, such as health and inventory, and informational text,
such as battle messages. This keeps UI updates separate from the game logic.

### Foundational classes and real logic — 3 points

We have implemented the `Character`, `Friend`, `Area`, and `Artifact` abstractions
and the `Collectible` interface, along with concrete characters, animals, biomes,
and items. Real logic includes friend capacity and duplicate checks, health loss,
ordered visit history, randomized artifact creation, and merchant pricing and
stock management. Item pickup and the full game loop are still in progress.

### Object-oriented principles — 2 points

We code to abstractions: `World` stores `Area` objects, trainers store `Friend`
objects, and merchants handle `Collectible` items. Pricing uses polymorphism by
calling `Collectible.getPower()`, with different implementations for food,
potions, gold, and friends. Constructor injection lets callers supply a merchant's
inventory and a trainer's friend collection instead of creating them internally.

### Meaningful test cases — 2 points

Existing JUnit tests check at least these five behaviors:

1. `WorldTest.keepsDistinctAreasInInsertionOrderWithoutRepeatingAnInstance`:
   areas retain insertion order without repeating the same instance.
2. `WorldTest.returnsAnImmutableSnapshot`: returned area lists cannot change the
   world, and earlier snapshots remain unchanged.
3. `TrainerTest.carriesDistinctFriendsWithoutRepeatingAnInstance`: separate
   friends can be carried without adding the same friend twice.
4. `PlayerTest.remembersUniqueVisitsInOrder`: visited areas are tracked uniquely
   in the order first visited.
5. `FriendTest.updatesStatsIndependently`: changing health, attack, or speed does
   not change the other stats.

The purchase test also describes the intended trading behavior, but currently
fails because item pickup is unfinished.


## Getting started

Use JDK 21 or newer. Open this folder as a Gradle project in your IDE.
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
- Abstract `Friend` holds a name, health, attack, and speed and declares `getSpecies()`.
  Animal constructors accept a name for base stats or `(name, health, attack, speed)` for custom stats; each stat has a getter
  and setter inherited from `Friend`. `Wolf`, `Bear`,
  `Fox`, and `Elephant` provide the animal implementations.
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

## Animal base stats

| Animal | Health | Attack | Speed | Role |
| --- | ---: | ---: | ---: | --- |
| Elephant | 180 | 8 | 2 | Durable, slow, low damage |
| Fox | 70 | 8 | 10 | Fast, fragile, low damage |
| Bear | 120 | 12 | 5 | Moderate damage and speed, sturdy |
| Wolf | 90 | 14 | 8 | Fast attacker with less health |

Use `new Fox("Ember")` for the species defaults. Public `BASE_HEALTH`,
`BASE_ATTACK`, and `BASE_SPEED` constants live in each animal class.
Custom constructors and inherited setters still allow individual stats.
These are starting values for playtesting; combat rules will determine balance.
