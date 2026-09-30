package fightfriends.areas;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TundraTest {
    @Test
    void preservesNameAndIdentifiesType() {
        Area subject = new Tundra("Example");

        assertEquals("Example", subject.getName());
        assertEquals("Tundra", subject.getBiome());
    }
}
