package fightfriends.characters;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ElephantTest {
    @Test
    void preservesNameAndIdentifiesType() {
        Friend subject = new Elephant("Example", 100.0, 10.0, 5.0);

        assertEquals("Example", subject.getName());
        assertEquals("Elephant", subject.getSpecies());
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
        Elephant subject = new Elephant("Example");
        assertEquals("Example", subject.getName());
        assertEquals("Elephant", subject.getSpecies());
        assertEquals(180.0, subject.getHealth());
        assertEquals(8.0, subject.getAttack());
        assertEquals(2.0, subject.getSpeed());
    }

    @Test
    void changingOneFriendDoesNotChangeAnotherOrItsDefaults() {
        Elephant subject = new Elephant("First");
        Elephant other = new Elephant("Second");
        subject.setHealth(1.0);
        subject.setAttack(1.0);
        subject.setSpeed(1.0);
        assertEquals(180.0, other.getHealth());
        assertEquals(8.0, other.getAttack());
        assertEquals(2.0, other.getSpeed());
        assertEquals(180.0, new Elephant("Third").getHealth());
    }
}
