package fightfriends.characters;

public class Bear extends Friend {
    public Bear(String name) {
        super(name);
    }

    @Override
    public String getSpecies() {
        return "Bear";
    }
}
