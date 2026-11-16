package fightfriends.battle;

import fightfriends.friends.Friend;
import fightfriends.strategy.*;
import java.util.Random;

public class RandomChoiceProvider implements ChoiceProvider {
    private final Random random;

    public RandomChoiceProvider(Random random) {
        this.random = random;
    }

    @Override
    public BattleStrategy chooseStrategy(Battle battle, Friend friend) {
        switch (random.nextInt(4)) {
            case 0: return new NormalStrategy();
            case 1: return new AggressiveStrategy();
            case 2: return new DefensiveStrategy();
            default: return new RunStrategy(random);
        }
    }
}
