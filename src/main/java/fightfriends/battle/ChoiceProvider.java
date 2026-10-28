package fightfriends.battle;

import fightfriends.strategy.BattleStrategy;
import fightfriends.friends.Friend;

/** Supplies a friend's strategy for the next turn. */
public interface ChoiceProvider {
    BattleStrategy chooseStrategy(Battle battle, Friend friend);
}
