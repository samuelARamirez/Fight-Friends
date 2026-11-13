package fightfriends;

import fightfriends.areas.Area;
import fightfriends.areas.AreaFactory;
import fightfriends.artifacts.ArtifactFactory;
import fightfriends.characters.CharacterFactory;
import fightfriends.characters.Player;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class WorldInventoryTest {
    private final AreaFactory areaFactory = new AreaFactory();
    private final ArtifactFactory artifactFactory = new ArtifactFactory();
    private final CharacterFactory characterFactory = new CharacterFactory();

    private World.Builder builder(Player player) {
        return new World.Builder().createGridOfAreas(1, 2, List.of(
                areaFactory.createForest("Woods"), areaFactory.createDesert("Dunes")))
                .addPlayer(player);
    }

    @Test
    void areaOwnsUniqueCollectiblesAndReturnsSnapshots() {
        Area area = areaFactory.createForest("Woods");
        Collectible food = artifactFactory.createFood("Bread", 2);
        area.addCollectible(food);
        area.addCollectible(food);
        List<Collectible> snapshot = area.getCollectibles();
        assertEquals(List.of(food), snapshot);
        assertThrows(UnsupportedOperationException.class, snapshot::clear);
        assertTrue(area.removeCollectible(food));
        assertFalse(area.removeCollectible(food));
        assertTrue(area.getCollectibles().isEmpty());
        assertEquals(List.of(food), snapshot);
    }

    @Test
    void pickupUsesCurrentAreaAndCannotCollectTheSameGoldTwice() {
        Player player = (Player) characterFactory.createPlayer("Sam");
        Collectible gold = artifactFactory.createGold("Coins", 10);
        World world = builder(player).addCollectibleAtXY(1, 0, gold).build();
        double balance = player.getGoldBalance();
        assertSame(player, world.getPlayer());
        assertSame(world.getAreaAt(0, 0), world.getPlayerArea());
        assertFalse(world.pickup(gold));
        assertEquals(balance, player.getGoldBalance());
        assertTrue(world.movePlayer(1, 0));
        assertSame(world.getAreaAt(1, 0), world.getPlayerArea());
        assertTrue(world.pickup(gold));
        assertEquals(balance + 10, player.getGoldBalance());
        assertTrue(world.getPlayerArea().getCollectibles().isEmpty());
        assertFalse(world.pickup(gold));
        assertEquals(balance + 10, player.getGoldBalance());
    }

    @Test
    void fullBackpackLeavesItemInAreaUntilSpaceIsAvailable() {
        Player player = (Player) characterFactory.createPlayer("Sam");
        for (int i = 0; i < 5; i++) {
            assertTrue(player.pickup(artifactFactory.createFood("Food " + i, 1)));
        }
        Collectible extra = artifactFactory.createFood("Extra", 2);
        World world = builder(player).addCollectibleAtXY(0, 0, extra).build();
        assertFalse(world.pickup(extra));
        assertEquals(List.of(extra), world.getPlayerArea().getCollectibles());
        player.increaseBackpackSpace(1);
        assertTrue(world.pickup(extra));
        assertTrue(player.getBackpack().contains(extra));
        assertTrue(world.getPlayerArea().getCollectibles().isEmpty());
    }

    @Test
    void randomDistributionPlacesEachItemInOneArea() {
        Player player = (Player) characterFactory.createPlayer("Sam");
        List<Collectible> items = List.of(artifactFactory.createGold("Coins", 10),
                artifactFactory.createFood("Bread", 2), artifactFactory.createPotion("Potion", 1, 1));
        World world = builder(player).distributeCollectiblesRandomly(items).build();
        for (Collectible item : items) {
            int count = 0;
            for (Area area : world.getAreas()) {
                if (area.getCollectibles().contains(item)) {
                    count++;
                }
            }
            assertEquals(1, count);
        }
    }

    @Test
    void invalidPlacementIsRejected() {
        Player player = (Player) characterFactory.createPlayer("Sam");
        Collectible gold = artifactFactory.createGold("Coins", 10);
        assertThrows(IllegalArgumentException.class,
                () -> builder(player).addCollectibleAtXY(2, 0, gold));
        assertThrows(IllegalArgumentException.class,
                () -> builder(player).addCollectibleAtXY(0, -1, gold));
        assertThrows(IllegalStateException.class,
                () -> new World.Builder().distributeCollectiblesRandomly(List.of(gold)));
    }

    @Test
    void gameReadsPlayerHealthFromWorld() {
        Player player = (Player) characterFactory.createPlayer("Sam");
        World world = builder(player).build();
        FightFriends game = new FightFriends(world);
        assertTrue(game.playerIsAlive());
        world.getPlayer().loseHealth(player.getHealthPoints());
        assertFalse(game.playerIsAlive());
    }
}
