package fightfriends.artifacts;

import fightfriends.friends.Friend;

import fightfriends.Die;
import fightfriends.RandomDie;

public class Leash extends Artifact {
    private final double capturePower;
    private final Die die;

    public Leash(String name, double capturePower, Die die) {
        super(name);
        this.capturePower = capturePower;
        this.die = die;
    }

    public double getCapturePower() {
        return capturePower;
    }

    @Override
    public double getPower() {
        return capturePower;
    }


    public Boolean tryToCaptureFriend(Friend friendToCapture) {
        if (friendToCapture.getHealth() <= 0 || capturePower <= 0) {
            return false;
        }
        double chance = capturePower / (capturePower + friendToCapture.getPower());
        return die.roll() <= chance * 100;
    }
}
