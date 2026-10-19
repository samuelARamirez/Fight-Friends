package fightfriends.characters;

import fightfriends.friends.Bear;
import fightfriends.friends.Fox;
import fightfriends.friends.Friend;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TrainerTest {

    private static final  CharacterFactory factory = new CharacterFactory();

    @Test
    void startsWithNameAndEmptyCollections() {
        Trainer trainer = (Trainer)factory.createTrainer("Sam");
        assertEquals("Sam", trainer.getName());
        assertTrue(trainer.getFriends().isEmpty());
        assertNull(new Trainer(null).getName());
    }

    @Test
    void carriesDistinctFriendsWithoutRepeatingAnInstance() {
        Trainer trainer = (Trainer)factory.createTrainer("Sam");
        Friend fox = new Fox("Ember", 100.0, 10.0, 5.0);
        Friend otherFox = new Fox("Ember", 100.0, 10.0, 5.0);
        trainer.addFriend(fox);
        trainer.addFriend(otherFox);
        trainer.addFriend(fox);
        assertEquals(List.of(fox, otherFox), trainer.getFriends());
    }

    @Test
    void removesOnlyFriendsThatArePresent() {
        Trainer trainer = (Trainer)factory.createTrainer("Sam");
        Friend fox = new Fox("Ember", 100.0, 10.0, 5.0);
        trainer.addFriend(fox);
        assertFalse(trainer.removeFriend(new Bear("Bear", 100.0, 10.0, 5.0)));
        assertTrue(trainer.removeFriend(fox));
        assertFalse(trainer.removeFriend(fox));
        assertTrue(trainer.getFriends().isEmpty());
    }
}
