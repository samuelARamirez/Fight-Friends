package fightfriends.characters;

import fightfriends.friends.Friend;

import java.util.List;

public class Trainer extends Character {
    private List<Friend> friends;
    private double healthPoints;
    private int maxNumFriends;
    private static final double DEFAULT_HP = 10.0;
    private static final int DEFAULT_MAX_NUM_FRIENDS = 3;

    public Trainer(String name, double healthPoints, List<Friend> friends, int maxNumFriends) {
        super(name);
        this.healthPoints = healthPoints;
        if(friends.size() > maxNumFriends){
            this.friends = friends.subList(0, maxNumFriends-1);
        }else {
            this.friends = friends;
        }
        this.maxNumFriends = maxNumFriends;
    }


    public void addFriend(Friend friend) {
        if (roomForMoreFriends() && !friends.contains(friend)) {
            friends.add(friend);
        }
    }

    private boolean roomForMoreFriends() {
        return friends.size() < maxNumFriends;
    }

    public boolean removeFriend(Friend friend) {
        return friends.remove(friend);
    }

    public List<Friend> getFriends() {
        return List.copyOf(friends);
    }

    public double getHealthPoints(){
        return healthPoints;
    }

    public double loseHealth(double amountLost){
        healthPoints -= amountLost;
        if(healthPoints <0){
            healthPoints = 0;
        }
        return healthPoints;
    }

    public boolean isAlive(){
        return healthPoints > 0;
    }


    @Override
    public boolean isTrainer(){
        return true;
    }

}
