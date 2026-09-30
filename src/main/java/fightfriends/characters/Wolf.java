package fightfriends.characters;

public class Wolf extends Friend {
    public Wolf(String name) {
        super(name);
    }

    @Override
    public String getSpecies() {
        return "Wolf";
    }
}
