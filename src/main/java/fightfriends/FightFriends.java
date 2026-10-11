package fightfriends;

import fightfriends.areas.Forest;
import fightfriends.friends.Fox;
import fightfriends.characters.Trainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Entry point for Fight Friends. Add game setup and orchestration here. */
public class FightFriends {
    private static final Logger logger = LoggerFactory.getLogger(FightFriends.class);

    public static void main(String[] args) {
        World world = new World();
        Forest startingArea = new Forest("Starting Woods");
        world.addArea(startingArea);

        Trainer trainer = new Trainer("Player");
        trainer.addFriend(new Fox("Ember"));
        trainer.visitArea(startingArea);

        logger.info("The world has {} area(s).", world.getAreas().size());
        logger.info("{} has {} friend(s) and has visited {} area(s).",
                trainer.getName(), trainer.getFriends().size(), trainer.getVisitedAreas().size());
        // TODO: Add exploration and combat rules.
    }
}
