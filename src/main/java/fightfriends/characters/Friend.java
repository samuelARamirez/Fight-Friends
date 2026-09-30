package fightfriends.characters;

/** Base class for animal friends. Add shared stats and behavior here. */
public abstract class Friend {
    private final String name;

    protected Friend(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract String getSpecies();
}
