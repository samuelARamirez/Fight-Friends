package fightfriends.strategy;

/** Changes offense and defense for a single turn without modifying base stats. */
public interface BattleStrategy {
    double AGGRESSIVE_OUTGOING_DAMAGE_MODIFIER = 1.5;
    double AGGRESSIVE_INCOMING_DAMAGE_MODIFIER = 1.25;

    double DEFENSIVE_OUTGOING_DAMAGE_MODIFIER = 0.75;
    double DEFENSIVE_INCOMING_DAMAGE_MODIFIER = 0.5;

    double RUN_INCOMING_DAMAGE_MODIFIER = 2.0;

    double CHANCE_TO_SUCCESSFULLY_RUN = 0.75;

    default boolean attemptEscape() {
        return false;
    }

    double outgoingDamage(double baseAttack);

    double incomingDamage(double attackDamage);

}
