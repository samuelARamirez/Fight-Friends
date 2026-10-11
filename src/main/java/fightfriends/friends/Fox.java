package fightfriends.friends;

public class Fox extends Friend {
    public static final double BASE_HEALTH = 70.0;
    public static final double BASE_ATTACK = 8.0;
    public static final double BASE_SPEED = 10.0;

    public Fox(String name) {
        this(name, BASE_HEALTH, BASE_ATTACK, BASE_SPEED);
    }

    public Fox(String name, double health, double attack, double speed) {
        super(name, health, attack, speed);
    }

    @Override
    public String getSpecies() {
        return "Fox";
    }
}
