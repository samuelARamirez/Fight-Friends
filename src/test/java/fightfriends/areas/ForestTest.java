package fightfriends.areas;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ForestTest {
    public static AreaFactory areaFactory = new AreaFactory();

    @Test
    void preservesNameAndIdentifiesType() {
        Area subject = areaFactory.createForest("Example");

        assertEquals("Example", subject.getName());
        assertEquals("Forest", subject.getBiome());
    }
}
