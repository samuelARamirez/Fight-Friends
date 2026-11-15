package fightfriends.battle;

import fightfriends.friends.Friend;
import fightfriends.friends.FriendFactory;
import fightfriends.observers.ConsoleObserver;
import fightfriends.strategy.BattleStrategy;
import fightfriends.strategy.NormalStrategy;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BattleDemoTest {
    @Test
    void battleDemo() {
        FriendFactory factory = new FriendFactory();
        Friend fox = factory.createFox("Ember");
        Friend bear = factory.createBear("Bruno");
        ChoiceProvider choices = new ChoiceProvider() {
            @Override
            public BattleStrategy chooseStrategy(Battle battle, Friend friend) {
                return new NormalStrategy();
            }
        };
        Battle battle = new Battle(fox, bear, choices, choices);
        battle.attach(new ConsoleObserver());
        while (!battle.isBattleOver()) {
            battle.playTurn();
        }
        assertFalse(battle.isDraw());
        assertEquals(0, fox.getHealth());
        assertEquals(72, bear.getHealth());
    }
}
