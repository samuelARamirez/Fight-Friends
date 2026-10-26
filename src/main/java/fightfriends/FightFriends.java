package fightfriends;

import fightfriends.areas.Forest;
import fightfriends.friends.Fox;
import fightfriends.characters.Trainer;
import fightfriends.characters.Character;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


/** Entry point for Fight Friends. Add game setup and orchestration here. */
public class FightFriends {
    private static final Logger logger = LoggerFactory.getLogger(FightFriends.class);
    private World world;

    private FightFriends(World world){
        this.world = world;
    }

    public void play(){
        //TODO: game loop
    }

    private void handleUserInput(){
        //TODO: accept user input and use to choose action/move
    }

    public boolean playerIsAlive(){
        //TODO
        return false;
    }

    public static class Builder {
        private World world;

        public Builder(){
        }

        public Builder world(World world){
            this.world = world;
            return this;
        }

        public Builder addCharactersRandomly(List<Character> charactersToAdd){
            //TODO
            return this;
        }

        public Builder addCharacterToArea(String areaName, Character character){
            //TODO
            return this;
        }

        public FightFriends build(){
            return new FightFriends(world);
        }
    }

}
