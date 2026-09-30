package fightfriends;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FightFriendsTest {
    @Test
    void canConstructEntryPoint() {
        assertNotNull(new FightFriends());
    }

    @Test
    void starterCreatesWorldAndTrainer() {
        Logger logger = (Logger) LoggerFactory.getLogger(FightFriends.class);
        Level previousLevel = logger.getLevel();
        ListAppender<ILoggingEvent> appender = new ListAppender<>();
        appender.start();
        logger.addAppender(appender);
        logger.setLevel(Level.INFO);
        try {
            FightFriends.main(new String[0]);
            assertEquals(List.of(
                    "The world has 1 area(s).",
                    "Player has 1 friend(s) and has visited 1 area(s)."),
                    appender.list.stream().map(ILoggingEvent::getFormattedMessage).toList());
        } finally {
            logger.detachAppender(appender);
            logger.setLevel(previousLevel);
            appender.stop();
        }
    }
}
