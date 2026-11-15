package fightfriends.battle;

import fightfriends.friends.Friend;
import fightfriends.friends.FriendFactory;
import fightfriends.observers.Observer;
import fightfriends.observers.Subject;
import fightfriends.strategy.NormalStrategy;
import fightfriends.strategy.AggressiveStrategy;
import fightfriends.strategy.DefensiveStrategy;
import fightfriends.strategy.RunStrategy;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class BattleObserverTest {
    private final FriendFactory factory = new FriendFactory();

    private static class RecordingObserver implements Observer {
        private final List<String> events = new ArrayList<>();

        @Override
        public void update(String event) {
            events.add(event);
        }
    }

    @Test
    void reportsTurnsAndWinnerOnlyOnce() {
        Friend user = factory.createFox("Ember", 30, 10, 5);
        Friend enemy = factory.createBear("Bruno", 15, 5, 5);
        Battle battle = new FixedBattle(user, enemy,
                List.of(new NormalStrategy(), new NormalStrategy()),
                List.of(new NormalStrategy(), new NormalStrategy()));
        RecordingObserver observer = new RecordingObserver();
        battle.attach(observer);
        battle.attach(observer);
        battle.playTurn();
        assertFalse(battle.isBattleOver());
        battle.playTurn();
        assertTrue(battle.isBattleOver());
        assertFalse(battle.isDraw());
        assertEquals(List.of("Battle started: Ember vs Bruno", "Turn 1",
                "Ember chose Normal", "Bruno chose Normal",
                "Ember lost 5.0 health; remaining: 25.0", "Bruno lost 10.0 health; remaining: 5.0",
                "Turn 2", "Ember chose Normal", "Bruno chose Normal", "Ember lost 5.0 health; remaining: 20.0",
                "Bruno lost 5.0 health; remaining: 0.0", "Ember won the battle."), observer.events);
        assertThrows(IllegalStateException.class, battle::playTurn);
        assertEquals(12, observer.events.size());
    }

    @Test
    void detachedObserversStopReceivingEvents() {
        Battle battle = new FixedBattle(factory.createFox("Ember"), factory.createBear("Bruno"),
                List.of(new NormalStrategy(), new NormalStrategy()),
                List.of(new NormalStrategy(), new NormalStrategy()));
        RecordingObserver first = new RecordingObserver();
        RecordingObserver second = new RecordingObserver();
        Subject subject = battle;
        subject.attach(first);
        subject.attach(second);
        battle.playTurn();
        assertEquals(first.events, second.events);
        subject.detach(first);
        battle.playTurn();
        assertEquals(6, first.events.size());
        assertEquals(11, second.events.size());
    }

    @Test
    void reportsEscapeWithoutDamage() {
        Battle battle = new FixedBattle(factory.createFox("Ember"), factory.createBear("Bruno"),
                List.of(new RunStrategy(new FixedRandom(0.5))), List.of(new NormalStrategy()));
        RecordingObserver observer = new RecordingObserver();
        battle.attach(observer);
        battle.playTurn();
        assertEquals(List.of("Battle started: Ember vs Bruno", "Turn 1",
                "Ember chose Run", "Bruno chose Normal",
                "Ember escaped. Battle ended in a draw."), observer.events);
        assertTrue(battle.isDraw());
    }

    @Test
    void reportsSimultaneousKnockoutAsDraw() {
        Battle battle = new FixedBattle(factory.createFox("Ember", 5, 10, 5),
                factory.createBear("Bruno", 5, 10, 5),
                List.of(new NormalStrategy()), List.of(new NormalStrategy()));
        RecordingObserver observer = new RecordingObserver();
        battle.attach(observer);
        battle.playTurn();
        assertTrue(battle.isBattleOver());
        assertTrue(battle.isDraw());
        assertEquals("Both friends were knocked out. Battle ended in a draw.", observer.events.getLast());
    }

    @Test
    void invalidTurnDoesNotSendEvents() {
        Battle battle = new FixedBattle(factory.createFox("Ember", 0, 10, 5),
                factory.createBear("Bruno"), List.of(), List.of());
        RecordingObserver observer = new RecordingObserver();
        battle.attach(observer);
        assertThrows(IllegalStateException.class, battle::playTurn);
        assertTrue(observer.events.isEmpty());
    }
    @Test
    void reportsDifferentStrategiesEachTurn() {
        Battle battle = new FixedBattle(factory.createFox("Ember"), factory.createBear("Bruno"),
                List.of(new AggressiveStrategy(), new DefensiveStrategy()),
                List.of(new DefensiveStrategy(), new AggressiveStrategy()));
        RecordingObserver observer = new RecordingObserver();
        battle.attach(observer);
        battle.playTurn();
        assertEquals("Ember chose Aggressive", observer.events.get(2));
        assertEquals("Bruno chose Defensive", observer.events.get(3));
        battle.playTurn();
        assertEquals("Ember chose Defensive", observer.events.get(7));
        assertEquals("Bruno chose Aggressive", observer.events.get(8));
    }
}
