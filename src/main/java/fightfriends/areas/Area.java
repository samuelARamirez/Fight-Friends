package fightfriends.areas;

/** A named place in the world. Add exploration behavior here. */
public abstract class Area {
    private final String name;

    protected Area(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract String getBiome();
}
