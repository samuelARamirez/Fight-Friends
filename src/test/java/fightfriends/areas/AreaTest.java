package fightfriends.areas;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AreaTest {
    private Area create(String name) {
        return new Area(name) {
            @Override
            public String getBiome() {
                return "Custom";
            }
        };
    }

    @Test
    void storesNameForCustomSubclasses() {
        Area subject = create("Custom name");
        assertEquals("Custom name", subject.getName());
        assertEquals("Custom", subject.getBiome());
    }

    @Test
    void acceptsNamesWithoutValidation() {
        assertEquals("", create("").getName());
        assertNull(create(null).getName());
    }
}
