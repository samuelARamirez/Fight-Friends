package fightfriends;

import fightfriends.areas.Area;
import fightfriends.areas.AreaFactory;
import fightfriends.characters.Player;
import fightfriends.characters.CharacterFactory;
import fightfriends.characters.Trainer;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class WorldBuilderTest {
    private final CharacterFactory characterFactory = new CharacterFactory();
    private final AreaFactory areaFactory = new AreaFactory();

    private World.Builder grid() {
        return new World.Builder().createGridOfAreas(2, 2,
                List.of(areaFactory.createForest("Home"),
                areaFactory.createDesert("East"),
                areaFactory.createForest("South"),
                areaFactory.createDesert("Southeast")));
    }

    @Test
    void placesCharactersAndFindsAreasByCoordinates() {
        Player player = (Player) characterFactory.createPlayer("Sam");
        Trainer trainer = (Trainer) characterFactory.createTrainer("Rival");
        World world = grid().addPlayerAtXY(1, 1, player)
                .addCharacterToArea("East", trainer).build();
        assertEquals(1, world.findLocationOf(player).xPos());
        assertEquals(1, world.findLocationOf(player).yPos());
        assertEquals("East", world.getAreaAt(1, 0).getName());
        assertEquals(List.of(trainer), world.getCharactersAt(1, 0));
        assertTrue(player.hasVisited(world.getAreaAt(1, 1)));
    }

    @Test
    void movesVerticallyAndBackWithoutRepeatingVisits() {
        Player player = (Player) characterFactory.createPlayer("Sam");
        World world = grid().addPlayer(player).build();
        assertTrue(world.movePlayer(0, 1));
        assertFalse(world.getCharactersAt(0, 0).contains(player));
        assertTrue(world.movePlayer(0, 0));
        assertEquals(2, player.getVisitedAreas().size());
    }

    @Test
    void rejectsDiagonalStationaryAndOutOfBoundsMoves() {
        Player player = (Player) characterFactory.createPlayer("Sam");
        World world = grid().addPlayer(player).build();
        assertFalse(world.movePlayer(1, 1));
        assertFalse(world.movePlayer(0, 0));
        assertFalse(world.movePlayer(2, 0));
        assertFalse(world.movePlayer(0, -1));
        assertFalse(world.movePlayer(0, 2));
        assertEquals(0, world.findLocationOf(player).xPos());
        assertEquals(0, world.findLocationOf(player).yPos());
        assertEquals(1, player.getVisitedAreas().size());
    }

    @Test
    void validatesGridAndCharacterPlacement() {
        assertThrows(IllegalArgumentException.class,
                () -> new World.Builder().createGridOfAreas(0, 1, List.of()));
        assertThrows(IllegalArgumentException.class,
                () -> new World.Builder().createGridOfAreas(2, 2, List.of(areaFactory.createForest("Home"))));
        assertThrows(IllegalArgumentException.class,
                () -> grid().addPlayerAtXY(-1, 0, (Player) characterFactory.createPlayer("Sam")));
        assertThrows(IllegalArgumentException.class,
                () -> grid().addCharacterToArea("Missing", (Trainer) characterFactory.createTrainer("Rival")));
    }

    @Test
    void requiresAreasAndExactlyOnePlayer() {
        assertThrows(IllegalStateException.class, () -> new World.Builder().build());
        assertThrows(IllegalStateException.class, () -> grid().build());
        assertThrows(IllegalStateException.class, () -> grid()
                .addPlayer((Player) characterFactory.createPlayer("Sam")).addPlayer((Player) characterFactory.createPlayer("Other")).build());
    }

    @Test
    void builderChangesDoNotChangeBuiltWorld() {
        Player player = (Player) characterFactory.createPlayer("Sam");
        World.Builder builder = grid().addPlayer(player);
        World world = builder.build();
        builder.addPlayerAtXY(1, 1, player);
        builder.createGridOfAreas(1, 1, List.of(areaFactory.createForest("Replacement")));
        assertEquals(4, world.getAreas().size());
        assertEquals(0, world.findLocationOf(player).xPos());
        assertEquals(0, world.findLocationOf(player).yPos());
        assertEquals("Home", world.getAreaAt(0, 0).getName());
    }

    @Test
    void removingAnAreaPreservesCharacterLocations() {
        Player player = (Player) characterFactory.createPlayer("Sam");
        World world = grid().addPlayerAtXY(1, 0, player).build();
        Area home = world.getAreaAt(0, 0);
        Area occupied = world.getAreaAt(1, 0);
        assertFalse(world.removeArea(occupied));
        assertTrue(world.removeArea(home));
        assertEquals(0, world.findLocationOf(player).xPos());
        assertEquals(0, world.findLocationOf(player).yPos());
        assertSame(occupied, world.getAreaAt(world.findLocationOf(player)));
    }
}
