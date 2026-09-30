package fightfriends.characters;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BearTest {
    @Test
    void preservesNameAndIdentifiesType() {
        Friend subject = new Bear("Example", 100.0, 10.0, 5.0);

        assertEquals("Example", subject.getName());
        assertEquals("Bear", subject.getSpecies());
        assertEquals(100.0, subject.getHealth());
        assertEquals(10.0, subject.getAttack());
        assertEquals(5.0, subject.getSpeed());

        subject.setHealth(75.5);
        subject.setAttack(12.5);
        subject.setSpeed(8.5);
        assertEquals(75.5, subject.getHealth());
        assertEquals(12.5, subject.getAttack());
        assertEquals(8.5, subject.getSpeed());
    }
    @Test
    void startsWithAnimalBaseStats() {
        Bear subject = new Bear("Example");
        assertEquals("Example", subject.getName());
        assertEquals("Bear", subject.getSpecies());
        assertEquals(120.0, subject.getHealth());
        assertEquals(12.0, subject.getAttack());
        assertEquals(5.0, subject.getSpeed());
    }

    @Test
    void changingOneFriendDoesNotChangeAnotherOrItsDefaults() {
        Bear subject = new Bear("First");
        Bear other = new Bear("Second");
        subject.setHealth(1.0);
        subject.setAttack(1.0);
        subject.setSpeed(1.0);
        assertEquals(120.0, other.getHealth());
        assertEquals(12.0, other.getAttack());
        assertEquals(5.0, other.getSpeed());
        assertEquals(120.0, new Bear("Third").getHealth());
    }
}
