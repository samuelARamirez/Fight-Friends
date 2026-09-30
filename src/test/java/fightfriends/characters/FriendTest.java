package fightfriends.characters;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FriendTest {
    private Friend create(String name) {
        return new Friend(name, 100.0, 10.0, 5.0) {
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
    void storesInitialStats() {
        Friend subject = create("Custom");
        assertEquals(100.0, subject.getHealth());
        assertEquals(10.0, subject.getAttack());
        assertEquals(5.0, subject.getSpeed());
    }

    @Test
    void updatesStatsIndependently() {
        Friend subject = create("Custom");
        subject.setHealth(0.0);
        assertEquals(0.0, subject.getHealth());
        assertEquals(10.0, subject.getAttack());
        assertEquals(5.0, subject.getSpeed());

        subject.setAttack(15.5);
        assertEquals(0.0, subject.getHealth());
        assertEquals(15.5, subject.getAttack());
        assertEquals(5.0, subject.getSpeed());

        subject.setSpeed(8.5);
        assertEquals(0.0, subject.getHealth());
        assertEquals(15.5, subject.getAttack());
        assertEquals(8.5, subject.getSpeed());
    }

    @Test
    void acceptsNamesWithoutValidation() {
        assertEquals("", create("").getName());
        assertNull(create(null).getName());
    }
}
