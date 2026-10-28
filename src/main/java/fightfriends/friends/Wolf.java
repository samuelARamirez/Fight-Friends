package fightfriends.friends;

public class Wolf extends Friend {
    public static final double BASE_HEALTH = 90.0;
    public static final double BASE_ATTACK = 14.0;
    public static final double BASE_SPEED = 8.0;

    public Wolf(String name) {
        this(name, BASE_HEALTH, BASE_ATTACK, BASE_SPEED);
    }

    public Wolf(String name, double health, double attack, double speed) {
        this(name, health, attack, speed, 1);
    }

    public Wolf(String name, int level) {
        this(name, BASE_HEALTH, BASE_ATTACK, BASE_SPEED, level);
    }

    public Wolf(String name, double health, double attack, double speed, int level) {
        super(name, health, attack, speed, level);
    }

    @Override
    public String getSpecies() {
        return "Wolf";
    }
}
