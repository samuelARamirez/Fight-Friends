package fightfriends.strategy;

public class NormalStrategy implements BattleStrategy {
    @Override
    public double outgoingDamage(double baseAttack) {
        return baseAttack;
    }

    @Override
    public double incomingDamage(double attackDamage) {
        return attackDamage;
    }
}
