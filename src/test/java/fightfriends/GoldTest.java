package fightfriends;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GoldTest {
    @Test
    void testGoldValues() {
        Gold gold = new Gold("Coins", 25);
        assertEquals("Coins", gold.getName());
        assertEquals(25, gold.getAmount());
        assertEquals(9, gold.getPower(), 0.0001);
    }

    @Test
    void testGoldDescription() {
        Gold gold = new Gold("Coins", 25);
        assertEquals(String.format("Coins ($%.2f)", 25.0), gold.toString());
    }
}
