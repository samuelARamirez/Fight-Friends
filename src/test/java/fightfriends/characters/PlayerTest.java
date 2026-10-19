package fightfriends.characters;

import fightfriends.Collectible;
import fightfriends.Gold;
import fightfriends.areas.Area;
import fightfriends.areas.Desert;
import fightfriends.areas.Forest;
import fightfriends.artifacts.Artifact;
import fightfriends.artifacts.ArtifactFactory;
import fightfriends.friends.Bear;
import fightfriends.friends.Fox;
import fightfriends.friends.Friend;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PlayerTest {

    private static final  CharacterFactory factory = new CharacterFactory();
    private static final ArtifactFactory artifactFactory = new ArtifactFactory();

    @Test
    void startsWithNameAndEmptyCollections() {
        Player player = (Player)factory.createPlayer("Sam");
        assertEquals("Sam", player.getName());
        assertTrue(player.getFriends().isEmpty());
        assertNull(new Trainer(null).getName());
    }

    @Test
    void carriesDistinctFriendsWithoutRepeatingAnInstance() {
        Player player = (Player)factory.createPlayer("Sam");
        Friend fox = new Fox("Ember", 100.0, 10.0, 5.0);
        Friend otherFox = new Fox("Ember", 100.0, 10.0, 5.0);
        player.addFriend(fox);
        player.addFriend(otherFox);
        player.addFriend(fox);
        assertEquals(List.of(fox, otherFox), player.getFriends());
    }

    @Test
    void removesOnlyFriendsThatArePresent() {
        Player player = (Player) factory.createPlayer("Sam");
        Friend fox = new Fox("Ember", 100.0, 10.0, 5.0);
        player.addFriend(fox);
        assertFalse(player.removeFriend(new Bear("Bear", 100.0, 10.0, 5.0)));
        assertTrue(player.removeFriend(fox));
        assertFalse(player.removeFriend(fox));
        assertTrue(player.getFriends().isEmpty());

    }

    @Test
    void testInstanceOf(){
        Player player = (Player)factory.createPlayer("Sam");

        assertInstanceOf(Player.class, player);
        assertInstanceOf(Trainer.class, player);
        assertInstanceOf(Character.class, player);

    }

    @Test
    void testBackpack(){
        Player player = (Player)factory.createPlayer("Sam");
        //TODO finish this test
    }

    @Test
    void testSpendingMoney(){
        Player player = (Player)factory.createPlayer("Sam");
        double currentGold = player.getGoldBalance();
        player.spendMoney(50);

        assertEquals(currentGold-50, player.getGoldBalance());
    }

    @Test
    void testPurchasingItems(){
        Player player = (Player)factory.createPlayer("Sam");
        player.pickup(new Gold("100 gold", 100));

        Artifact bread = artifactFactory.createFood("bread", 10);
        Map<Collectible, Double> itemsForSale = new HashMap<>();
        itemsForSale.put(bread, 10.0);
        itemsForSale.put(new Gold("pile of Gold", 10.0 ), 9.0);
        Merchant merchant = (Merchant)factory.createMerchant("Merchant", itemsForSale);

        double initialBalance = player.getGoldBalance();
        player.purchaseItem(merchant, "bread");

        assertEquals(initialBalance-10, player.getGoldBalance());
        assertFalse(merchant.viewItemsForSale().contains(bread));
        assertTrue(player.getBackpack().contains(bread));

        player.purchaseItem(merchant,"pile of Gold");
        assertTrue(merchant.viewItemsForSale().isEmpty());
        assertEquals(initialBalance-10-9+10, player.getGoldBalance());
    }


    @Test
    void remembersUniqueVisitsInOrder() {
        Player player = (Player)factory.createPlayer("Sam");
        Area forest = new Forest("Woods");
        Area desert = new Desert("Dunes");
        Area otherForest = new Forest("Woods");
        assertFalse(player.hasVisited(forest));
        player.visitArea(forest);
        player.visitArea(desert);
        player.visitArea(forest);
        assertTrue(player.hasVisited(forest));
        assertFalse(player.hasVisited(otherForest));
        player.visitArea(otherForest);
        assertEquals(List.of(forest, desert, otherForest), player.getVisitedAreas());
    }

    @Test
    void returnsImmutableSnapshots() {
        Player player = (Player)factory.createPlayer("Sam");
        Friend fox = new Fox("Ember", 100.0, 10.0, 5.0);
        Area forest = new Forest("Woods");
        player.addFriend(fox);
        player.visitArea(forest);
        List<Friend> friends = player.getFriends();
        List<Area> visits = player.getVisitedAreas();
        assertThrows(UnsupportedOperationException.class, friends::clear);
        assertThrows(UnsupportedOperationException.class, visits::clear);
        player.removeFriend(fox);
        player.visitArea(new Desert("Dunes"));
        assertEquals(List.of(fox), friends);
        assertEquals(List.of(forest), visits);
        assertTrue(player.getFriends().isEmpty());
        assertEquals(2, player.getVisitedAreas().size());
    }

}
