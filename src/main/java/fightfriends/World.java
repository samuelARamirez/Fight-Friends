package fightfriends;

import fightfriends.areas.Area;
import fightfriends.characters.Character;
import fightfriends.characters.Player;

import java.util.List;
import java.util.Map;

public class World {
    private final List<List<Area>> areas;
    private final Map<Character, Position> charactersToPositions;

    private World (List<List<Area>> areas, Map<Character, Position> charactersToPositions){
        this.areas = areas;
        this.charactersToPositions = charactersToPositions;
    }

    public Position findLocationOf(Character character){
        //TODO
        return null;
    }

    public Area getAreaAt(Position position){
        //TODO
        return null;
    }


//    public List<List<Area>> getAreas() {
//        return List.copyOf(areas);
//    }

    public static class Builder {
        private final List<List<Area>> areas;
        private final Map<Character, Area> charactersToAreas;

        public Builder(){
        }

        public Builder randomGridOfNxM(int n, int m){
            //TODO
            return this;
        }

        public Builder setHomeVillageAtXY(int x, int y){
            //ensure x and y are within the size of the grid
            //home village is where a couple of merchants are guaranteed
            //TODO
            return this
        }

        public Builder addPlayer(Player player){
            //default to home village location, if none then 0,0
            //TODO
            return this;
        }

        public Builder addCharactersRandomly(List<Character> charactersToAdd){
            //TODO
            return this;
        }

        public Builder distributeCollectiblesRandomly(List<Collectible> collectibles){
            //TODO
            return this;
        }

        public Builder addCollectibleAtXY(int x, int y, Collectible thingToAdd){
            //TODO
            return this;
        }

        public Builder addCharacterToArea(String areaName, Character character){
            //TODO
            return this;
        }

        public World build(){
            //TODO check to make sure there is one player in the world somewhere before building
            //also probably check that there is at least one area
            return new World(areas, charactersToAreas);
        }
    }

    public record Position(int xPos, int yPos){

    }
}
