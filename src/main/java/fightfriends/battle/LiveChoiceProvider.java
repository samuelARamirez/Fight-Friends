package fightfriends.battle;

import fightfriends.strategy.BattleStrategy;
import fightfriends.friends.Friend;

/** Delegates each choice to the caller, such as the gameplay UI. */
public class LiveChoiceProvider implements ChoiceProvider {
    private final ChoiceProvider input;

    public LiveChoiceProvider(ChoiceProvider input) {
        this.input = input;
    }

    @Override
    public BattleStrategy chooseStrategy(Battle battle, Friend friend) {
        return input.chooseStrategy(battle, friend);
    }
}
