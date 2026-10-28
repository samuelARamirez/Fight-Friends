package fightfriends.friends;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FoxTest {
    private final FriendFactory friendFactory = new FriendFactory();

    @Test
    void startsAtSpecifiedLevelWithBaseStats() {
        Friend subject = friendFactory.createFox("Leveled", 3);

        assertEquals("Leveled", subject.getName());
        assertEquals("Fox", subject.getSpecies());
        assertEquals(3, subject.getLevel());
        assertEquals(0, subject.getExperiencePoints());
        assertEquals(300, subject.getExperiencePointsNeededToLevelUp());
        assertEquals(70.0, subject.getHealth());
        assertEquals(8.0, subject.getAttack());
        assertEquals(10.0, subject.getSpeed());
    }

    @Test
    void startsAtSpecifiedLevelWithCustomStats() {
        Friend subject = friendFactory.createFox("Custom", 125.5, 17.5, 6.5, 4);

        assertEquals("Custom", subject.getName());
        assertEquals("Fox", subject.getSpecies());
        assertEquals(4, subject.getLevel());
        assertEquals(0, subject.getExperiencePoints());
        assertEquals(400, subject.getExperiencePointsNeededToLevelUp());
        assertEquals(125.5, subject.getHealth());
        assertEquals(17.5, subject.getAttack());
        assertEquals(6.5, subject.getSpeed());
    }

    @Test
    void preservesNameAndIdentifiesType() {
        Friend subject = friendFactory.createFox("Example", 100.0, 10.0, 5.0);

        assertEquals("Example", subject.getName());
        assertEquals("Fox", subject.getSpecies());
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
        Friend subject = friendFactory.createFox("Example");
        assertEquals("Example", subject.getName());
        assertEquals("Fox", subject.getSpecies());
        assertEquals(70.0, subject.getHealth());
        assertEquals(8.0, subject.getAttack());
        assertEquals(10.0, subject.getSpeed());
    }

    @Test
    void changingOneFriendDoesNotChangeAnotherOrItsDefaults() {
        Friend subject = friendFactory.createFox("First");
        Friend other = friendFactory.createFox("Second");
        subject.setHealth(1.0);
        subject.setAttack(1.0);
        subject.setSpeed(1.0);
        assertEquals(70.0, other.getHealth());
        assertEquals(8.0, other.getAttack());
        assertEquals(10.0, other.getSpeed());
        assertEquals(70.0, friendFactory.createFox("Third").getHealth());
    }
}
