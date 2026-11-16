package fightfriends;

import fightfriends.artifacts.Artifact;
import fightfriends.artifacts.ArtifactFactory;
import org.junit.jupiter.api.Test;

import java.util.List;

import static fightfriends.Backpack.INDENT;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BackpackTest {
    ArtifactFactory artifactFactory = new ArtifactFactory();

    @Test
    void testCreation() {
        Artifact carrot = artifactFactory.createFood("Carrot", 10);
        Backpack backpack = new Backpack(List.of(carrot,
                artifactFactory.createFood("Apple", 5),
                artifactFactory.createLeash("Mid Leash", 50),
                artifactFactory.createLeash("Goated Leash", 100),
                artifactFactory.createPotion("Decent Potion", 10, 10)
                ), 10);
        assertEquals(10, backpack.getCapacity());
        assertEquals(5, backpack.getNumItemsInBackpack());
        assertTrue(backpack.hasRoom());
        assertTrue(backpack.contains(carrot));
    }
    @Test
    void rejectsCapacitySmallerThanItems() {
        List<Artifact> items = List.of(artifactFactory.createFood("Carrot", 10),
                artifactFactory.createFood("Apple", 5));
        assertThrows(IllegalArgumentException.class, () -> new Backpack(items, 1));
    }

    @Test
    void acceptsCapacityLargerThanItems() {
        Backpack backpack = new Backpack(List.of(artifactFactory.createFood("Carrot", 10)), 2);
        assertEquals(2, backpack.getCapacity());
        assertEquals(1, backpack.getNumItemsInBackpack());
        assertTrue(backpack.hasRoom());
    }
    @Test
    void listsOnlyContainTheirTypeInOriginalOrder() {
        Artifact carrot = artifactFactory.createFood("Carrot", 10);
        Artifact apple = artifactFactory.createFood("Apple", 5);
        Artifact leash = artifactFactory.createLeash("Basic Leash", 50);
        Artifact strongLeash = artifactFactory.createLeash("Strong Leash", 100);
        Artifact potion = artifactFactory.createPotion("Small Potion", 20, 3);
        Artifact bigPotion = artifactFactory.createPotion("Big Potion", 40, 6);
        Backpack backpack = new Backpack(List.of(leash, carrot, potion, apple, bigPotion, strongLeash), 6);

        assertEquals(List.of(carrot, apple), backpack.getFoodList());
        assertEquals(List.of(leash, strongLeash), backpack.getLeashList());
        assertEquals(List.of(potion, bigPotion), backpack.getFriendPotionList());
        assertEquals(6, backpack.getNumItemsInBackpack());
    }

    @Test
    void emptyBackpackHasEmptyListsAndString() {
        Backpack backpack = new Backpack();
        assertTrue(backpack.getFoodList().isEmpty());
        assertTrue(backpack.getLeashList().isEmpty());
        assertTrue(backpack.getFriendPotionList().isEmpty());
        assertEquals("", backpack.toString());
    }

    @Test
    void formatsMixedBackpackByCategory() {
        Backpack backpack = new Backpack(List.of(
                artifactFactory.createLeash("Basic Leash", 50),
                artifactFactory.createFood("Carrot", 10),
                artifactFactory.createPotion("Small Potion", 20, 3),
                artifactFactory.createFood("Apple", 5)), 4);

        assertEquals("Food: \n" + INDENT + "Carrot, 10.0\n" + INDENT + "Apple, 5.0\n"
                + "Friend Potions: \n" + INDENT + "Small Potion, 3.0\n"
                + "Leashes: \n" + INDENT + "Basic Leash, 50.0\n", backpack.toString());
    }

    @Test
    void formatsOnlyFoodWhenOtherCategoriesAreEmpty() {
        Backpack backpack = new Backpack(List.of(artifactFactory.createFood("Carrot", 10)), 1);
        assertEquals("Food: \n" + INDENT + "Carrot, 10.0\n", backpack.toString());
        assertTrue(backpack.getLeashList().isEmpty());
        assertTrue(backpack.getFriendPotionList().isEmpty());
    }

    @Test
    void formatsOnlyPotionsWhenOtherCategoriesAreEmpty() {
        Backpack backpack = new Backpack(List.of(artifactFactory.createPotion("Small Potion", 20, 3)), 1);
        assertEquals("Friend Potions: \n" + INDENT + "Small Potion, 3.0\n", backpack.toString());
        assertTrue(backpack.getFoodList().isEmpty());
        assertTrue(backpack.getLeashList().isEmpty());
    }

    @Test
    void formatsOnlyLeashesWhenOtherCategoriesAreEmpty() {
        Backpack backpack = new Backpack(List.of(artifactFactory.createLeash("Basic Leash", 50)), 1);
        assertEquals("Leashes: \n" + INDENT + "Basic Leash, 50.0\n", backpack.toString());
        assertTrue(backpack.getFoodList().isEmpty());
        assertTrue(backpack.getFriendPotionList().isEmpty());
    }
}
