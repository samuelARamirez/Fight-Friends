package fightfriends.areas;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TundraTest {
    public static AreaFactory areaFactory = new AreaFactory();

    @Test
    void preservesNameAndIdentifiesType() {
        Area subject = areaFactory.createTundra("Example");

        assertEquals("Example", subject.getName());
        assertEquals("Tundra", subject.getBiome());
    }
}
