package fightfriends.characters;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

public class PlayerTest {

    @Test
    public void testInstanceOf(){
        Character trainer = new Player("test");

        assertInstanceOf(Player.class, trainer);
        assertInstanceOf(Trainer.class, trainer);
        assertInstanceOf(Character.class, trainer);

    }

    /*
    @Test
    void remembersUniqueVisitsInOrder() {
        Trainer trainer = new Trainer("Sam");
        Area forest = new Forest("Woods");
        Area desert = new Desert("Dunes");
        Area otherForest = new Forest("Woods");
        assertFalse(trainer.hasVisited(forest));
        trainer.visitArea(forest);
        trainer.visitArea(desert);
        trainer.visitArea(forest);
        assertTrue(trainer.hasVisited(forest));
        assertFalse(trainer.hasVisited(otherForest));
        trainer.visitArea(otherForest);
        assertEquals(List.of(forest, desert, otherForest), trainer.getVisitedAreas());
    }

    @Test
    void returnsImmutableSnapshots() {
        Trainer trainer = new Trainer("Sam");
        Friend fox = new Fox("Ember", 100.0, 10.0, 5.0);
        Area forest = new Forest("Woods");
        trainer.addFriend(fox);
        trainer.visitArea(forest);
        List<Friend> friends = trainer.getFriends();
        List<Area> visits = trainer.getVisitedAreas();
        assertThrows(UnsupportedOperationException.class, friends::clear);
        assertThrows(UnsupportedOperationException.class, visits::clear);
        trainer.removeFriend(fox);
        trainer.visitArea(new Desert("Dunes"));
        assertEquals(List.of(fox), friends);
        assertEquals(List.of(forest), visits);
        assertTrue(trainer.getFriends().isEmpty());
        assertEquals(2, trainer.getVisitedAreas().size());
    }
     */
}
