Feature: Exploring the world
  Background:
    Given a forest next to a desert
    And a player named "Sam" starts at 0, 0

  Scenario: Starting in the world
    Then the player is at 0, 0
    And the player has visited "Forest"
    And the player has visited 1 area

  Scenario: Moving into a neighboring area
    When the player moves to 1, 0
    Then the move succeeds
    And the player is at 1, 0
    And the player has visited "Desert"
    And the player has visited 2 areas

  Scenario: Trying to leave the map
    When the player moves to -1, 0
    Then the move is blocked
    And the player is at 0, 0
    And the player has visited 1 area
