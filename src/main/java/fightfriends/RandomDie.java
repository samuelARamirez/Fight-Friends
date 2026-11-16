package fightfriends;

import java.util.Random;

public class RandomDie implements Die {
    private final Random random = new Random();
    private final int sides;

    public RandomDie(int sides) {
        if (sides < 1) {
            throw new IllegalArgumentException("A die needs at least one side");
        }
        this.sides = sides;
    }

    public static Die hundredSided() {
        return new RandomDie(100);
    }

    @Override
    public int roll() {
        return random.nextInt(sides) + 1;
    }
}
