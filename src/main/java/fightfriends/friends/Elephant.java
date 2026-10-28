package fightfriends.friends;

public class Elephant extends Friend {
    public static final double BASE_HEALTH = 180.0;
    public static final double BASE_ATTACK = 8.0;
    public static final double BASE_SPEED = 2.0;

    public Elephant(String name) {
        this(name, BASE_HEALTH, BASE_ATTACK, BASE_SPEED);
    }

    public Elephant(String name, double health, double attack, double speed) {
        this(name, health, attack, speed, 1);
    }

    public Elephant(String name, int level) {
        this(name, BASE_HEALTH, BASE_ATTACK, BASE_SPEED, level);
    }

    public Elephant(String name, double health, double attack, double speed, int level) {
        super(name, health, attack, speed, level);
    }

    @Override
    public String getSpecies() {
        return "Elephant";
    }
}
