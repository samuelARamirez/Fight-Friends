package fightfriends.observers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConsoleObserver implements Observer {
    private static final Logger logger = LoggerFactory.getLogger(ConsoleObserver.class);

    @Override
    public void update(String event) {
        logger.info(event);
    }
}
