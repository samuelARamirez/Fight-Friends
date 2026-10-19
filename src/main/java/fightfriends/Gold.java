package fightfriends;

public class Gold implements Collectible{
    private final double goldAmt;
    private final String name;

    private static final double GOLD_TO_POWER = .9/2.5;

    public Gold(String name, double goldAmt){
        this.name = name;
        this.goldAmt = goldAmt;
    }

    public String getName(){
        return name;
    }

    @Override
    public String toString(){
        return String.format("%s ($%.2f)", name, goldAmt);
    }

    public double getPower(){
        return goldAmt*GOLD_TO_POWER;
    }
}
