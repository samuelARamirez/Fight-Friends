package fightfriends.battle;

import fightfriends.friends.Friend;
import fightfriends.strategy.BattleStrategy;

import java.util.Iterator;
import java.util.List;

/** Supplies a predetermined sequence, failing when the sequence runs out. */
public class FixedChoiceProvider implements ChoiceProvider {
    private final Iterator<BattleStrategy> choices;

    public FixedChoiceProvider(List<BattleStrategy> choices) {
        this.choices = List.copyOf(choices).iterator();
    }

    @Override
    public BattleStrategy chooseStrategy(Battle battle, Friend friend) {
        if (!choices.hasNext()) {
            throw new IllegalStateException("No fixed strategy choices remaining");
        }
        return choices.next();
    }
}
