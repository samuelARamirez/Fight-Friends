package fightfriends.friends;

import fightfriends.Collectible;

/** Base class for animal friends. Add shared stats and behavior here. */
public abstract class Friend implements Collectible {
    private final String name;
    private double health;
    private double attack;
    private double speed;
    private int level;
    private int experiencePoints;
    static final int EXPERIENCE_POINTS_NEEDED_RATIO = 100;

    protected Friend(String name, double health, double attack, double speed) {
        this(name, health, attack, speed, 1);
    }

    protected Friend(String name, double health, double attack, double speed, int level) {
        if (level < 1) {
            throw new IllegalArgumentException("Friend level must be at least 1");
        }
        this.name = name;
        this.health = health;
        this.attack = attack;
        this.speed = speed;
        this.level = level;
        this.experiencePoints = 0;
    }

    public void gainExp(int experiencePointsGained) {
        if (experiencePointsGained < 0) {
            throw new IllegalArgumentException("Experience gained must be positive");
        }
        experiencePoints += experiencePointsGained;
    }

    public boolean canLevelUp() {
        return experiencePoints >= getExperiencePointsNeededToLevelUp();
    }

    public void levelUp() {
        if (!canLevelUp()) {
            throw new IllegalStateException("Not enough experience to level up");
        }

        experiencePoints -= getExperiencePointsNeededToLevelUp();
        level++;
    }

    public int getExperiencePointsNeededToLevelUp() {
        return level * EXPERIENCE_POINTS_NEEDED_RATIO;
    }

    public int getExperiencePoints() {
        return experiencePoints;
    }

    public String getName() {
        return name;
    }

    public int getLevel() { return level; }

    public double getHealth() {
        return health;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    public double getAttack() {
        return attack;
    }

    public void setAttack(double attack) {
        this.attack = attack;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public abstract String getSpecies();

    public double getPower(){
        return health+attack+speed;
    }
}
