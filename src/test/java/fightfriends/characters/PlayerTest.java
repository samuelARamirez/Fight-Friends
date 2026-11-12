package fightfriends.characters;

import fightfriends.areas.AreaFactory;
import fightfriends.friends.FriendFactory;
import fightfriends.Collectible;
import fightfriends.Gold;
import fightfriends.areas.Area;
import fightfriends.artifacts.Artifact;
import fightfriends.artifacts.ArtifactFactory;
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
    private final AreaFactory areaFactory = new AreaFactory();

    private final FriendFactory friendFactory = new FriendFactory();


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
        Friend fox = friendFactory.createFox("Ember", 100.0, 10.0, 5.0);
        Friend otherFox = friendFactory.createFox("Ember", 100.0, 10.0, 5.0);
        player.addFriend(fox);
        player.addFriend(otherFox);
        player.addFriend(fox);
        assertEquals(List.of(fox, otherFox), player.getFriends());
    }

    @Test
    void removesOnlyFriendsThatArePresent() {
        Player player = (Player) factory.createPlayer("Sam");
        Friend fox = friendFactory.createFox("Ember", 100.0, 10.0, 5.0);
        player.addFriend(fox);
        assertFalse(player.removeFriend(friendFactory.createBear("Bear", 100.0, 10.0, 5.0)));
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
        for (int i = 0; i < 5; i++) {
            assertTrue(player.pickup(artifactFactory.createFood("Food " + i, 1)));
        }
        Artifact extra = artifactFactory.createFood("Extra", 1);
        assertFalse(player.pickup(extra));
        assertEquals(5, player.getBackpack().size());
        player.increaseBackpackSpace(1);
        assertTrue(player.pickup(extra));
        assertFalse(player.pickup(extra));
        assertEquals(6, player.getBackpack().size());
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
        assertTrue(player.pickup(new Gold("100 gold", 100)));

        Artifact bread = artifactFactory.createFood("bread", 10);
        Map<Collectible, Double> itemsForSale = new HashMap<>();
        itemsForSale.put(bread, 10.0);
        itemsForSale.put(new Gold("pile of Gold", 10.0 ), 9.0);
        Merchant merchant = (Merchant)factory.createMerchant("Merchant", itemsForSale);

        double initialBalance = player.getGoldBalance();
        assertTrue(player.purchaseItem(merchant, "bread"));

        assertEquals(initialBalance-10, player.getGoldBalance());
        assertFalse(merchant.viewItemsForSale().contains(bread));
        assertTrue(player.getBackpack().contains(bread));

        assertTrue(player.purchaseItem(merchant,"pile of Gold"));
        assertTrue(merchant.viewItemsForSale().isEmpty());
        assertEquals(initialBalance-10-9+10, player.getGoldBalance());
    }


    @Test
    void remembersUniqueVisitsInOrder() {
        Player player = (Player)factory.createPlayer("Sam");
        Area forest = areaFactory.createForest("Woods");
        Area desert = areaFactory.createDesert("Dunes");
        Area otherForest = areaFactory.createForest("Woods");
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
        Friend fox = friendFactory.createFox("Ember", 100.0, 10.0, 5.0);
        Area forest = areaFactory.createForest("Woods");
        player.addFriend(fox);
        player.visitArea(forest);
        List<Friend> friends = player.getFriends();
        List<Area> visits = player.getVisitedAreas();
        assertThrows(UnsupportedOperationException.class, friends::clear);
        assertThrows(UnsupportedOperationException.class, visits::clear);
        player.removeFriend(fox);
        player.visitArea(areaFactory.createDesert("Dunes"));
        assertEquals(List.of(fox), friends);
        assertEquals(List.of(forest), visits);
        assertTrue(player.getFriends().isEmpty());
        assertEquals(2, player.getVisitedAreas().size());
    }


    @Test
    void goldAddsItsAmountWithoutUsingBackpackSpace() {
        Player player = (Player) factory.createPlayer("Sam");
        double balance = player.getGoldBalance();
        for (int i = 0; i < 5; i++) {
            assertTrue(player.pickup(artifactFactory.createFood("Food " + i, 1)));
        }
        assertTrue(player.pickup(new Gold("Coins", 12.5)));
        assertEquals(balance + 12.5, player.getGoldBalance());
        assertEquals(5, player.getBackpack().size());
    }

    @Test
    void friendPickupRespectsCapacityAndDuplicates() {
        Player player = (Player) factory.createPlayer("Sam");
        Friend fox = friendFactory.createFox("Ember", 100, 10, 5);
        assertTrue(player.pickup(fox));
        assertFalse(player.pickup(fox));
        assertTrue(player.pickup(friendFactory.createFox("Second", 100, 10, 5)));
        assertTrue(player.pickup(friendFactory.createFox("Third", 100, 10, 5)));
        assertFalse(player.pickup(friendFactory.createFox("Fourth", 100, 10, 5)));
        assertEquals(3, player.getFriends().size());
        assertTrue(player.getBackpack().isEmpty());
    }

    @Test
    void failedPickupReturnsPurchaseWithoutChargingPlayer() {
        Player player = (Player) factory.createPlayer("Sam");
        for (int i = 0; i < 5; i++) {
            assertTrue(player.pickup(artifactFactory.createFood("Food " + i, 1)));
        }
        Artifact bread = artifactFactory.createFood("Bread", 2);
        Map<Collectible, Double> stock = new HashMap<>();
        stock.put(bread, 10.0);
        Merchant merchant = (Merchant) factory.createMerchant("Shop", stock);
        double balance = player.getGoldBalance();
        assertFalse(player.purchaseItem(merchant, "Bread"));
        assertEquals(balance, player.getGoldBalance());
        assertTrue(merchant.viewItemsForSale().contains(bread));
        assertEquals(10.0, merchant.askPrice("Bread"));
        assertFalse(player.getBackpack().contains(bread));
    }

    @Test
    void unaffordablePurchaseLeavesStockAndBalanceUnchanged() {
        Player player = (Player) factory.createPlayer("Sam");
        Artifact bread = artifactFactory.createFood("Bread", 2);
        Map<Collectible, Double> stock = new HashMap<>();
        stock.put(bread, player.getGoldBalance() + 1);
        Merchant merchant = (Merchant) factory.createMerchant("Shop", stock);
        double balance = player.getGoldBalance();
        assertFalse(player.purchaseItem(merchant, "Bread"));
        assertEquals(balance, player.getGoldBalance());
        assertTrue(merchant.viewItemsForSale().contains(bread));
        assertTrue(player.getBackpack().isEmpty());
    }
}
