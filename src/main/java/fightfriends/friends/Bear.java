package fightfriends.friends;

public class Bear extends Friend {
    public static final double BASE_HEALTH = 120.0;
    public static final double BASE_ATTACK = 12.0;
    public static final double BASE_SPEED = 5.0;

    public Bear(String name) {
        this(name, BASE_HEALTH, BASE_ATTACK, BASE_SPEED);
    }

    public Bear(String name, double health, double attack, double speed) {
        this(name, health, attack, speed, 1);
    }

    public Bear(String name, int level) {
        this(name, BASE_HEALTH, BASE_ATTACK, BASE_SPEED, level);
    }

    public Bear(String name, double health, double attack, double speed, int level) {
        super(name, health, attack, speed, level);
    }

    @Override
    public String getSpecies() {
        return "Bear";
    }
}
