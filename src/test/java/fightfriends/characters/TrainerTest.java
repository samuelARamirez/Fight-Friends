package fightfriends.characters;

import fightfriends.areas.Area;
import fightfriends.areas.Forest;
import fightfriends.areas.Desert;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TrainerTest {
    @Test
    void startsWithNameAndEmptyCollections() {
        Trainer trainer = new Trainer("Sam");
        assertEquals("Sam", trainer.getName());
        assertTrue(trainer.getFriends().isEmpty());
        assertTrue(trainer.getVisitedAreas().isEmpty());
        assertNull(new Trainer(null).getName());
    }

    @Test
    void carriesDistinctFriendsWithoutRepeatingAnInstance() {
        Trainer trainer = new Trainer("Sam");
        Friend fox = new Fox("Ember", 100.0, 10.0, 5.0);
        Friend otherFox = new Fox("Ember", 100.0, 10.0, 5.0);
        trainer.addFriend(fox);
        trainer.addFriend(otherFox);
        trainer.addFriend(fox);
        assertEquals(List.of(fox, otherFox), trainer.getFriends());
    }

    @Test
    void removesOnlyFriendsThatArePresent() {
        Trainer trainer = new Trainer("Sam");
        Friend fox = new Fox("Ember", 100.0, 10.0, 5.0);
        trainer.addFriend(fox);
        assertFalse(trainer.removeFriend(new Bear("Bear", 100.0, 10.0, 5.0)));
        assertTrue(trainer.removeFriend(fox));
        assertFalse(trainer.removeFriend(fox));
        assertTrue(trainer.getFriends().isEmpty());
    }

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
}
