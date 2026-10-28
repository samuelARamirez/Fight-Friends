package fightfriends.battle;

import java.util.Random;

/** Supplies known rolls and fails if more rolls are requested than expected. */
public class FixedRandom extends Random {
    private final double[] rolls;
    private int index;

    public FixedRandom(double... rolls) {
        this.rolls = rolls.clone();
    }

    @Override
    public double nextDouble() {
        if (index >= rolls.length) {
            throw new IllegalStateException("No fixed random rolls remaining");
        }
        return rolls[index++];
    }
}
