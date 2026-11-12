package fightfriends.characters;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class CharacterFactoryTest {
    private final CharacterFactory factory = new CharacterFactory();
    private final List<String> trainerNames = List.of("Steve", "Anthony", "Bartholomew",
            "Anita", "Lestra", "Daphne", "Violet", "Tegan");

    @Test
    void testRandomTrainer() {
        Character trainer = factory.createTrainer();
        assertTrue(trainer.isTrainer());
        assertFalse(trainer.isPlayer());
        assertFalse(trainer.isMerchant());
        assertTrue(trainerNames.contains(trainer.getName()));
    }

    @Test
    void testRandomPlayer() {
        Character player = factory.createPlayer();
        assertTrue(player.isPlayer());
        assertTrue(player.isTrainer());
        assertFalse(player.isMerchant());
        assertTrue(trainerNames.contains(player.getName()));
    }

    @Test
    void testCharacterDescription() {
        Character trainer = factory.createTrainer("Sam");
        assertEquals("Sam", trainer.toString());
    }

    @Test
    void testMerchantRoles() {
        Character merchant = factory.createMerchant();
        assertTrue(merchant.isMerchant());
        assertFalse(merchant.isTrainer());
        assertFalse(merchant.isPlayer());
    }
}
