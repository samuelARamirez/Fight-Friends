package fightfriends.stepdefs;

import fightfriends.areas.AreaFactory;
import fightfriends.World;
import fightfriends.areas.Area;
import fightfriends.characters.Player;
import fightfriends.characters.CharacterFactory;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class WorldStepDefs {
    private final AreaFactory areaFactory = new AreaFactory();

    private final CharacterFactory characterFactory = new CharacterFactory();

    private World.Builder builder;
    private World world;
    private Player player;
    private boolean moveSucceeded;

    @Given("a forest next to a desert")
    public void createWorld() {
        builder = new World.Builder().createGridOfAreas(1, 2,
                List.of(areaFactory.createForest("Forest"), areaFactory.createDesert("Desert")));
    }

    @Given("a player named {string} starts at {int}, {int}")
    public void addPlayer(String name, int x, int y) {
        player = (Player) characterFactory.createPlayer(name);
        world = builder.addPlayerAtXY(x, y, player).build();
    }

    @When("the player moves to {int}, {int}")
    public void movePlayer(int x, int y) {
        moveSucceeded = world.movePlayer(x, y);
    }

    @Then("the player is at {int}, {int}")
    public void checkPosition(int x, int y) {
        assertEquals(x, world.findLocationOf(player).xPos());
        assertEquals(y, world.findLocationOf(player).yPos());
        assertTrue(world.getCharactersAt(x, y).contains(player));
    }

    @Then("the player has visited {string}")
    public void checkVisited(String name) {
        for (Area area : world.getAreas()) {
            if (area.getName().equals(name)) {
                assertTrue(player.hasVisited(area));
                return;
            }
        }
        fail("No area named " + name);
    }

    @Then("the player has visited {int} area(s)")
    public void checkVisitCount(int count) {
        assertEquals(count, player.getVisitedAreas().size());
    }

    @Then("the move succeeds")
    public void checkSuccessfulMove() {
        assertTrue(moveSucceeded);
    }

    @Then("the move is blocked")
    public void checkBlockedMove() {
        assertFalse(moveSucceeded);
    }
}
