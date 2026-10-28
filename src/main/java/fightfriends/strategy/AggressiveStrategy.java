package fightfriends.strategy;

/** Deals more damage but leaves the fighter more exposed. */
public class AggressiveStrategy implements BattleStrategy {
    @Override
    public double outgoingDamage(double baseAttack) {
        return baseAttack * AGGRESSIVE_OUTGOING_DAMAGE_MODIFIER;
    }

    @Override
    public double incomingDamage(double attackDamage) {
        return attackDamage * AGGRESSIVE_INCOMING_DAMAGE_MODIFIER;
    }
}
