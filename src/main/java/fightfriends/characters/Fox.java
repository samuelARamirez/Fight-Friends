package fightfriends.characters;

public class Fox extends Friend {
    public Fox(String name) {
        super(name);
    }

    @Override
    public String getSpecies() {
        return "Fox";
    }
}
