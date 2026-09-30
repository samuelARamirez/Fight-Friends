package fightfriends.characters;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BearTest {
    @Test
    void preservesNameAndIdentifiesType() {
        Friend subject = new Bear("Example");

        assertEquals("Example", subject.getName());
        assertEquals("Bear", subject.getSpecies());
    }
}
