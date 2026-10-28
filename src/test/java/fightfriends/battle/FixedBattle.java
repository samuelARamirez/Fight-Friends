package fightfriends.battle;

import fightfriends.friends.Friend;
import fightfriends.strategy.BattleStrategy;

import java.util.List;

/** Convenience fixture using the same battle logic with predetermined choices. */
public class FixedBattle extends Battle {
    public FixedBattle(Friend userFriend, Friend enemyFriend,
                       List<BattleStrategy> userChoices, List<BattleStrategy> enemyChoices) {
        super(userFriend, enemyFriend,
                new FixedChoiceProvider(userChoices), new FixedChoiceProvider(enemyChoices));
    }
}
