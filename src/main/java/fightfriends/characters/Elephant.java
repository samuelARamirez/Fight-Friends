package fightfriends.characters;

public class Elephant extends Friend {
    public static final double BASE_HEALTH = 180.0;
    public static final double BASE_ATTACK = 8.0;
    public static final double BASE_SPEED = 2.0;

    public Elephant(String name) {
        this(name, BASE_HEALTH, BASE_ATTACK, BASE_SPEED);
    }

    public Elephant(String name, double health, double attack, double speed) {
        super(name, health, attack, speed);
    }

    @Override
    public String getSpecies() {
        return "Elephant";
    }
}
