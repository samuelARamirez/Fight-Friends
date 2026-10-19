package fightfriends.friends;

import fightfriends.Collectible;

/** Base class for animal friends. Add shared stats and behavior here. */
public abstract class Friend implements Collectible {
    private final String name;
    private double health;
    private double attack;
    private double speed;

    protected Friend(String name, double health, double attack, double speed) {
        this.name = name;
        this.health = health;
        this.attack = attack;
        this.speed = speed;
    }

    public String getName() {
        return name;
    }

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
