package fightfriends;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RandomDieTest {
    @Test
    void hundredSidedDieStaysInRange() {
        Die die = RandomDie.hundredSided();
        for (int i = 0; i < 100; i++) {
            int roll = die.roll();
            assertTrue(roll >= 1 && roll <= 100);
        }
    }

    @Test
    void rejectsInvalidSideCount() {
        assertThrows(IllegalArgumentException.class, () -> new RandomDie(0));
    }
}
