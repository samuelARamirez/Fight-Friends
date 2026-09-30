package fightfriends.characters;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FriendTest {
    private Friend create(String name) {
        return new Friend(name) {
            @Override
            public String getSpecies() {
                return "Custom";
            }
        };
    }

    @Test
    void storesNameForCustomSubclasses() {
        Friend subject = create("Custom name");
        assertEquals("Custom name", subject.getName());
        assertEquals("Custom", subject.getSpecies());
    }

    @Test
    void acceptsNamesWithoutValidation() {
        assertEquals("", create("").getName());
        assertNull(create(null).getName());
    }
}
