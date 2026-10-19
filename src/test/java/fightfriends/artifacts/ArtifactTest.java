package fightfriends.artifacts;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ArtifactTest {
    private static final ArtifactFactory factory = new ArtifactFactory();

    @Test
    void testFoodConstruction(){
        final double HP = 5.1;
        final String NAME = "Bread";
        Food testFood = (Food) factory.createFood(NAME, HP);

        assertEquals(NAME, testFood.getName());
        assertEquals(HP, testFood.getNutrition());
    }

    @Test
    void testFoodListConstruction(){
        final int N_FOODS = 10;
        List<Artifact> foods = factory.createNFoods(N_FOODS);

        assertEquals(N_FOODS, foods.size());
    }

    @Test
    void testPotionConstruction(){
        final double HP = 5.1;
        final double STRENGTH = 6.2;
        final String NAME = "Silly Juice";
        FriendPotion testPotion = (FriendPotion) factory.createPotion(NAME, HP, STRENGTH);

        assertEquals(NAME, testPotion.getName());
        assertEquals(HP, testPotion.getHealthPoints());
        assertEquals(STRENGTH, testPotion.getStrengthPoints());
    }

    @Test
    void testPotionListConstruction(){
        final int N_POTIONS = 10;
        List<Artifact> potions = factory.createNPotions(N_POTIONS);

        assertEquals(N_POTIONS, potions.size());
    }
}
