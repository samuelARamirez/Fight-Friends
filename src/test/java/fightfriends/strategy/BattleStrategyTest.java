package fightfriends.strategy;

import fightfriends.battle.FixedRandom;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BattleStrategyTest {
    @Test
    void normalPreservesDamageAndDoesNotAttemptEscape() {
        BattleStrategy strategy = new NormalStrategy();
        assertEquals(20, strategy.outgoingDamage(20));
        assertEquals(20, strategy.incomingDamage(20));
        assertFalse(strategy.attemptEscape());
    }

    @Test
    void aggressiveBoostsAttackAndIncreasesExposure() {
        BattleStrategy strategy = new AggressiveStrategy();
        assertEquals(30, strategy.outgoingDamage(20));
        assertEquals(25, strategy.incomingDamage(20));
        assertFalse(strategy.attemptEscape());
    }

    @Test
    void defensiveTradesAttackForProtection() {
        BattleStrategy strategy = new DefensiveStrategy();
        assertEquals(15, strategy.outgoingDamage(20));
        assertEquals(10, strategy.incomingDamage(20));
        assertFalse(strategy.attemptEscape());
    }

    @Test
    void runGivesUpAttackAndDoublesIncomingDamageWithoutRollingAgain() {
        BattleStrategy strategy = new RunStrategy(new FixedRandom());
        assertEquals(0, strategy.outgoingDamage(20));
        assertEquals(40, strategy.incomingDamage(20));
        assertEquals(40, strategy.incomingDamage(20));
    }
}
