package fightfriends.strategy;

import java.util.Random;

/** Attempts escape, taking double incoming damage if the attempt fails. */
public class RunStrategy implements BattleStrategy {

    private final Random random;

    public RunStrategy() {
        this(new Random());
    }

    public RunStrategy(Random random) {
        this.random = random;
    }

    @Override
    public double outgoingDamage(double baseAttack) {
        return 0;
    }

    @Override
    public boolean attemptEscape() {
        return random.nextDouble() < CHANCE_TO_SUCCESSFULLY_RUN;
    }

    @Override
    public double incomingDamage(double attackDamage) {
        return attackDamage * RUN_INCOMING_DAMAGE_MODIFIER;
    }
}
