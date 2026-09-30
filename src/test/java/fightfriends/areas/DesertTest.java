package fightfriends.areas;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DesertTest {
    @Test
    void preservesNameAndIdentifiesType() {
        Area subject = new Desert("Example");

        assertEquals("Example", subject.getName());
        assertEquals("Desert", subject.getBiome());
    }
}
