package fightfriends;

import fightfriends.areas.Area;
import fightfriends.areas.Forest;
import fightfriends.areas.Desert;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WorldTest {
    @Test
    void startsEmpty() {
        assertTrue(new World().getAreas().isEmpty());
    }

    @Test
    void keepsDistinctAreasInInsertionOrderWithoutRepeatingAnInstance() {
        World world = new World();
        Area forest = new Forest("Woods");
        Area otherForest = new Forest("Woods");
        world.addArea(forest);
        world.addArea(otherForest);
        world.addArea(forest);
        assertEquals(List.of(forest, otherForest), world.getAreas());
    }

    @Test
    void removesOnlyAreasThatArePresent() {
        World world = new World();
        Area forest = new Forest("Woods");
        world.addArea(forest);
        assertFalse(world.removeArea(new Desert("Dunes")));
        assertTrue(world.removeArea(forest));
        assertFalse(world.removeArea(forest));
        assertTrue(world.getAreas().isEmpty());
    }

    @Test
    void returnsAnImmutableSnapshot() {
        World world = new World();
        Area forest = new Forest("Woods");
        world.addArea(forest);
        List<Area> snapshot = world.getAreas();
        assertThrows(UnsupportedOperationException.class, snapshot::clear);
        world.removeArea(forest);
        assertEquals(List.of(forest), snapshot);
        assertTrue(world.getAreas().isEmpty());
    }
}
