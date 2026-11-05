package fightfriends.areas;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DesertTest {
    public static AreaFactory areaFactory = new AreaFactory();

    @Test
    void preservesNameAndIdentifiesType() {
        Area subject = areaFactory.createDesert("Example");

        assertEquals("Example", subject.getName());
        assertEquals("Desert", subject.getBiome());
    }
}
