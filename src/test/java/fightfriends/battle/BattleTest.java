package fightfriends.battle;

import fightfriends.friends.Bear;
import fightfriends.friends.Friend;
import fightfriends.strategy.AggressiveStrategy;
import fightfriends.strategy.DefensiveStrategy;
import fightfriends.strategy.NormalStrategy;
import fightfriends.strategy.RunStrategy;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

class BattleTest {
    @Test
    void successfulUserEscapeEndsTurnAndBattleAsDrawWithoutDamage() {
        Friend user = new Bear("User", 100, 10, 5);
        Friend enemy = new Bear("Enemy", 100, 10, 5);
        Battle battle = new FixedBattle(user, enemy,
                List.of(new RunStrategy(new FixedRandom(0.5))),
                List.of(new AggressiveStrategy()));

        assertFalse(battle.isBattleOver());
        assertFalse(battle.isDraw());
        battle.playTurn();

        assertEquals(100, user.getHealth());
        assertEquals(100, enemy.getHealth());
        assertTrue(battle.isBattleOver());
        assertTrue(battle.isDraw());
        IllegalStateException error = assertThrows(IllegalStateException.class, battle::playTurn);
        assertEquals("Battle is already over", error.getMessage());
    }

    @Test
    void successfulEnemyEscapeAlsoEndsBattleWithoutDamage() {
        Friend user = new Bear("User", 100, 10, 5);
        Friend enemy = new Bear("Enemy", 100, 10, 5);
        Battle battle = new FixedBattle(user, enemy,
                List.of(new AggressiveStrategy()),
                List.of(new RunStrategy(new FixedRandom(0.5))));

        battle.playTurn();

        assertEquals(100, user.getHealth());
        assertEquals(100, enemy.getHealth());
        assertTrue(battle.isBattleOver());
        assertTrue(battle.isDraw());
    }

    @Test
    void failedEscapeTakesDoubleModifiedAttackAndAllowsNextTurn() {
        Friend user = new Bear("User", 100, 10, 5);
        Friend enemy = new Bear("Enemy", 100, 10, 5);
        Battle battle = new FixedBattle(user, enemy,
                List.of(new RunStrategy(new FixedRandom(0.9)), new NormalStrategy()),
                List.of(new AggressiveStrategy(), new NormalStrategy()));

        battle.playTurn();

        assertEquals(70, user.getHealth());
        assertEquals(100, enemy.getHealth());
        assertFalse(battle.isBattleOver());
        assertFalse(battle.isDraw());

        battle.playTurn();
        assertEquals(60, user.getHealth());
        assertEquals(90, enemy.getHealth());
    }

    @Test
    void failedEnemyEscapeTakesDoubleDamageAndDealsNone() {
        Friend user = new Bear("User", 100, 10, 5);
        Friend enemy = new Bear("Enemy", 100, 10, 5);
        Battle battle = new FixedBattle(user, enemy,
                List.of(new NormalStrategy()),
                List.of(new RunStrategy(new FixedRandom(0.9))));

        battle.playTurn();

        assertEquals(100, user.getHealth());
        assertEquals(80, enemy.getHealth());
        assertFalse(battle.isBattleOver());
        assertFalse(battle.isDraw());
    }

    @Test
    void enemyCanEscapeEvenIfUserEscapeFails() {
        Friend user = new Bear("User", 100, 10, 5);
        Friend enemy = new Bear("Enemy", 100, 10, 5);
        Battle battle = new FixedBattle(user, enemy,
                List.of(new RunStrategy(new FixedRandom(0.9))),
                List.of(new RunStrategy(new FixedRandom(0.5))));

        battle.playTurn();

        assertEquals(100, user.getHealth());
        assertEquals(100, enemy.getHealth());
        assertTrue(battle.isBattleOver());
        assertTrue(battle.isDraw());
    }

    @Test
    void bothFailedEscapesLeaveHealthUnchangedAndBattleContinues() {
        Friend user = new Bear("User", 100, 10, 5);
        Friend enemy = new Bear("Enemy", 100, 10, 5);
        Battle battle = new FixedBattle(user, enemy,
                List.of(new RunStrategy(new FixedRandom(0.9))),
                List.of(new RunStrategy(new FixedRandom(0.9))));

        battle.playTurn();

        assertEquals(100, user.getHealth());
        assertEquals(100, enemy.getHealth());
        assertFalse(battle.isBattleOver());
        assertFalse(battle.isDraw());
    }

    @Test
    void strategiesAffectBothOffenseAndDefenseForOnlyTheirTurn() {
        Friend user = new Bear("User", 100, 10, 5);
        Friend enemy = new Bear("Enemy", 100, 10, 5);
        Battle battle = new FixedBattle(user, enemy,
                List.of(new AggressiveStrategy(), new NormalStrategy()),
                List.of(new DefensiveStrategy(), new NormalStrategy()));

        battle.playTurn();
        assertEquals(90.625, user.getHealth());
        assertEquals(92.5, enemy.getHealth());

        battle.playTurn();
        assertEquals(80.625, user.getHealth());
        assertEquals(82.5, enemy.getHealth());
        assertEquals(10, user.getAttack());
        assertEquals(10, enemy.getAttack());
    }

    @Test
    void lethalAttacksAreSimultaneousAndHealthStopsAtZero() {
        Friend user = new Bear("User", 5, 10, 5);
        Friend enemy = new Bear("Enemy", 5, 10, 5);
        Battle battle = new FixedBattle(user, enemy,
                List.of(new NormalStrategy()), List.of(new NormalStrategy()));

        battle.playTurn();

        assertEquals(0, user.getHealth());
        assertEquals(0, enemy.getHealth());
        assertThrows(IllegalStateException.class, battle::playTurn);
    }
}
