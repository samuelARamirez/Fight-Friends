package fightfriends.strategy;

/** Takes less damage at the cost of a weaker attack. */
public class DefensiveStrategy implements BattleStrategy {
    @Override
    public double outgoingDamage(double baseAttack) {
        return baseAttack * DEFENSIVE_OUTGOING_DAMAGE_MODIFIER;
    }

    @Override
    public double incomingDamage(double attackDamage) {
        return attackDamage * DEFENSIVE_INCOMING_DAMAGE_MODIFIER;
    }
}
