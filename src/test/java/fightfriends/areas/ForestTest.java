package fightfriends.areas;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ForestTest {
    @Test
    void preservesNameAndIdentifiesType() {
        Area subject = new Forest("Example");

        assertEquals("Example", subject.getName());
        assertEquals("Forest", subject.getBiome());
    }
}
