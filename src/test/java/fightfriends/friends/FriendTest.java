package fightfriends.friends;

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

    @Test
    void testLevelingUp() {
        Friend bear = new Bear("Bear");
        assertEquals(0, bear.getExperiencePoints());
        assertEquals(1, bear.getLevel());
        //bear is level 1, so it needs 100 exp to level up
        assertEquals(100, bear.getExperiencePointsNeededToLevelUp());
        assertFalse(bear.canLevelUp());

        bear.gainExp(120);
        assertEquals(120, bear.getExperiencePoints());
        assertTrue(bear.canLevelUp());
        bear.levelUp();
        assertEquals(2, bear.getLevel());
        assertEquals(20, bear.getExperiencePoints());

        //bear is level 2, so it needs 200 exp to level up
        assertEquals(200, bear.getExperiencePointsNeededToLevelUp());
        assertFalse(bear.canLevelUp());
    }

    @Test
    void constructorRejectsLevelLessThan1() {
        assertThrows(IllegalArgumentException.class, () -> new Bear("bear",10.0, 10.0, 10.0, 0));
    }

    @Test
    void cannotLevelUpWithoutEnoughExperience() {
        Friend bear = new Bear("Bear");

        assertThrows(IllegalStateException.class, bear::levelUp);

        assertEquals(1, bear.getLevel());
        assertEquals(0, bear.getExperiencePoints());
    }

    @Test
    void rejectsNegativeExperience() {
        Friend bear = new Bear("Bear");

        assertThrows(IllegalArgumentException.class, () -> bear.gainExp(-1));

        assertEquals(0, bear.getExperiencePoints());
    }
}
