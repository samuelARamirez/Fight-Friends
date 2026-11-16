package fightfriends.battle;

import fightfriends.friends.Friend;
import fightfriends.friends.FriendFactory;
import fightfriends.observers.Observer;
import fightfriends.strategy.NormalStrategy;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.NoSuchElementException;
import static org.junit.jupiter.api.Assertions.*;

class TerminalBattleTest {
    private final FriendFactory factory = new FriendFactory();

    @Test
    void readsAllChoicesAndRetriesInvalidInput() {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        ChoiceProvider choices = new TerminalChoiceProvider(new Scanner("bad\n0\n5\n 1 \n2\n3\n4\n"),
                new PrintStream(output), new Random(1));
        Friend friend = factory.createFox("Ember");
        assertEquals("Normal", choices.chooseStrategy(null, friend).toString());
        assertEquals("Aggressive", choices.chooseStrategy(null, friend).toString());
        assertEquals("Defensive", choices.chooseStrategy(null, friend).toString());
        assertEquals("Run", choices.chooseStrategy(null, friend).toString());
        assertTrue(output.toString().contains("Please enter 1, 2, 3, or 4."));
        assertThrows(NoSuchElementException.class, () -> choices.chooseStrategy(null, friend));
    }

    @Test
    void opponentCanSelectAllStrategies() {
        String[] names = {"Normal", "Aggressive", "Defensive", "Run"};
        for (int choice = 0; choice < 4; choice++) {
            final int roll = choice;
            Random fixed = new Random() {
                @Override
                public int nextInt(int bound) {
                    assertEquals(4, bound);
                    return roll;
                }
            };
            ChoiceProvider choices = new RandomChoiceProvider(fixed);
            assertEquals(names[choice], choices.chooseStrategy(null, factory.createWolf("Shadow")).toString());
        }
    }

    @Test
    void battleResultsGoThroughObserver() {
        ByteArrayOutputStream prompts = new ByteArrayOutputStream();
        ChoiceProvider input = new TerminalChoiceProvider(new Scanner("1\n"), new PrintStream(prompts), new Random(1));
        Battle battle = new Battle(factory.createFox("Ember", 30, 20, 10),
                factory.createWolf("Shadow", 10, 5, 10), new LiveChoiceProvider(input),
                new FixedChoiceProvider(List.of(new NormalStrategy())));
        List<String> events = new ArrayList<>();
        battle.attach(new Observer() {
            @Override
            public void update(String event) {
                events.add(event);
            }
        });
        battle.playTurn();
        assertTrue(battle.isBattleOver());
        assertFalse(battle.isDraw());
        assertEquals(List.of("Battle started: Ember vs Shadow", "Turn 1",
                "Ember chose Normal", "Shadow chose Normal",
                "Ember lost 5.0 health; remaining: 25.0",
                "Shadow lost 10.0 health; remaining: 0.0", "Ember won the battle."), events);
        assertFalse(prompts.toString().contains("won the battle"));
    }
}
