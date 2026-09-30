package fightfriends.characters;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FoxTest {
    @Test
    void preservesNameAndIdentifiesType() {
        Friend subject = new Fox("Example");

        assertEquals("Example", subject.getName());
        assertEquals("Fox", subject.getSpecies());
    }
}
