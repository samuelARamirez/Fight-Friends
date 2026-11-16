package fightfriends.battle;

import fightfriends.strategy.BattleStrategy;
import fightfriends.friends.Friend;
import fightfriends.observers.Observer;
import fightfriends.observers.Subject;
import java.util.ArrayList;
import java.util.List;

public class Battle implements Subject {
    private final Friend userFriend;
    private final Friend enemyFriend;
    private final ChoiceProvider userChoices;
    private final ChoiceProvider enemyChoices;
    private boolean battleOver = false;
    private boolean draw = false;
    private int turnCount = 0;
    private final List<Observer> observers = new ArrayList<>();

    public Battle(Friend userFriend, Friend enemyFriend,
                  ChoiceProvider userChoices, ChoiceProvider enemyChoices) {
        this.userFriend = userFriend;
        this.enemyFriend = enemyFriend;
        this.userChoices = userChoices;
        this.enemyChoices = enemyChoices;
    }

    @Override
    public void attach(Observer observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
    }

    @Override
    public void detach(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(String event) {
        for (Observer observer : List.copyOf(observers)) {
            observer.update(event);
        }
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

        if (turnCount == 0) {
            notifyObservers("Battle started: " + userFriend.getName() + " vs " + enemyFriend.getName());
        }
        turnCount++;
        notifyObservers("Turn " + turnCount);
        notifyObservers(userFriend.getName() + " chose " + userStrategy);
        notifyObservers(enemyFriend.getName() + " chose " + enemyStrategy);

        boolean userEscaped = userStrategy.attemptEscape();
        boolean enemyEscaped = !userEscaped && enemyStrategy.attemptEscape();
        if (userEscaped || enemyEscaped) {
            battleOver = true;
            draw = true;
            String escapedName = userEscaped ? userFriend.getName() : enemyFriend.getName();
            notifyObservers(escapedName + " escaped. Battle ended in a draw.");
            return;
        }

        // Calculate both hits before applying either, so both friends act this turn.
        double damageToEnemy = Math.max(0,
                enemyStrategy.incomingDamage(userStrategy.outgoingDamage(userFriend.getAttack())));
        double damageToUser = Math.max(0,
                userStrategy.incomingDamage(enemyStrategy.outgoingDamage(enemyFriend.getAttack())));

        double userHealthBefore = userFriend.getHealth();
        double enemyHealthBefore = enemyFriend.getHealth();
        userFriend.setHealth(Math.max(0, userFriend.getHealth() - damageToUser));
        enemyFriend.setHealth(Math.max(0, enemyFriend.getHealth() - damageToEnemy));
        battleOver = userFriend.getHealth() == 0 || enemyFriend.getHealth() == 0;
        draw = userFriend.getHealth() == 0 && enemyFriend.getHealth() == 0;

        notifyObservers(userFriend.getName() + " lost " + (userHealthBefore - userFriend.getHealth())
                + " health; remaining: " + userFriend.getHealth());
        notifyObservers(enemyFriend.getName() + " lost " + (enemyHealthBefore - enemyFriend.getHealth())
                + " health; remaining: " + enemyFriend.getHealth());
        if (battleOver) {
            if (draw) {
                notifyObservers("Both friends were knocked out. Battle ended in a draw.");
            } else {
                String winner = userFriend.getHealth() > 0 ? userFriend.getName() : enemyFriend.getName();
                notifyObservers(winner + " won the battle.");
            }
        }
    }
}
