package fightfriends.artifacts;

public class FriendPotion extends Artifact{
    private final double healthPoints;
    private final double strengthPoints;

    public FriendPotion(String name, double healthPoints, double strengthPoints) {
        super(name);
        this.healthPoints = healthPoints;
        this.strengthPoints = strengthPoints;
    }

    public double getHealthPoints(){
        return this.healthPoints;
    }

    public double getStrengthPoints(){
        return this.strengthPoints;
    }

    @Override
    public String toString(){
        return String.format("%s (health:{%.2f}, strength: {%.2f)", this.getName(), healthPoints, strengthPoints);
    }

    public double getPower(){
        return getHealthPoints()+getStrengthPoints();
    }
}
