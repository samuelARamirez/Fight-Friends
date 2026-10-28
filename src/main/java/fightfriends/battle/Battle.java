package fightfriends.battle;

import fightfriends.strategy.BattleStrategy;
import fightfriends.friends.Friend;

public class Battle {
    private final Friend userFriend;
    private final Friend enemyFriend;
    private final ChoiceProvider userChoices;
    private final ChoiceProvider enemyChoices;
    private boolean battleOver = false;
    private boolean draw = false;

    public Battle(Friend userFriend, Friend enemyFriend,
                  ChoiceProvider userChoices, ChoiceProvider enemyChoices) {
        this.userFriend = userFriend;
        this.enemyFriend = enemyFriend;
        this.userChoices = userChoices;
        this.enemyChoices = enemyChoices;
    }

    public Friend getUserFriend() {
        return userFriend;
    }

    public Friend getEnemyFriend() {
        return enemyFriend;
    }

    public boolean isBattleOver() {
        return battleOver;
    }

    public boolean isDraw() {
        return draw;
    }

    public void playTurn() {
        if (battleOver) {
            throw new IllegalStateException("Battle is already over");
        }

        if (userFriend.getHealth() <= 0 || enemyFriend.getHealth() <= 0) {
            throw new IllegalStateException("Both friends must be alive to play a turn");
        }

        BattleStrategy userStrategy = userChoices.chooseStrategy(this, userFriend);
        BattleStrategy enemyStrategy = enemyChoices.chooseStrategy(this, enemyFriend);

        if (userStrategy.attemptEscape() || enemyStrategy.attemptEscape()) {
            battleOver = true;
            draw = true;
            return;
        }

        // Calculate both hits before applying either, so both friends act this turn.
        double damageToEnemy = Math.max(0,
                enemyStrategy.incomingDamage(userStrategy.outgoingDamage(userFriend.getAttack())));
        double damageToUser = Math.max(0,
                userStrategy.incomingDamage(enemyStrategy.outgoingDamage(enemyFriend.getAttack())));

        userFriend.setHealth(Math.max(0, userFriend.getHealth() - damageToUser));
        enemyFriend.setHealth(Math.max(0, enemyFriend.getHealth() - damageToEnemy));
    }
}
