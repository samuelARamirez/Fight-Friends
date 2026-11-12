package fightfriends.battle;

import fightfriends.friends.Friend;
import fightfriends.friends.FriendFactory;
import fightfriends.strategy.BattleStrategy;
import fightfriends.strategy.NormalStrategy;
import fightfriends.strategy.DefensiveStrategy;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class LiveChoiceProviderTest {
    private final FriendFactory friendFactory = new FriendFactory();

    @Test
    void testLiveChoicesFollowInput() {
        BattleStrategy first = new NormalStrategy();
        BattleStrategy second = new DefensiveStrategy();
        LiveChoiceProvider choices = new LiveChoiceProvider(
                new FixedChoiceProvider(List.of(first, second)));
        Friend user = friendFactory.createBear("User");
        Friend enemy = friendFactory.createFox("Enemy");
        Battle battle = new Battle(user, enemy, choices, choices);
        assertSame(user, battle.getUserFriend());
        assertSame(enemy, battle.getEnemyFriend());
        assertSame(first, choices.chooseStrategy(battle, user));
        assertSame(second, choices.chooseStrategy(battle, user));
    }
}
