package fightfriends;

public class Gold implements Collectible{
    private final double goldAmt;
    private final String name;

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
}
