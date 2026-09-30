package fightfriends.characters;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WolfTest {
    @Test
    void preservesNameAndIdentifiesType() {
        Friend subject = new Wolf("Example");

        assertEquals("Example", subject.getName());
        assertEquals("Wolf", subject.getSpecies());
    }
}
