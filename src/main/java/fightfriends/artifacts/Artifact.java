package fightfriends.artifacts;

import fightfriends.Collectible;

abstract public class Artifact implements Collectible {
    private final String name;

    public Artifact(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }
}
