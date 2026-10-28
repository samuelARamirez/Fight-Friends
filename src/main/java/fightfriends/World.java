package fightfriends;

import fightfriends.areas.Area;
import fightfriends.characters.Character;
import fightfriends.characters.Player;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

public class World {
    private final List<List<Area>> areas;
    private final Map<Character, Position> charactersToPositions;

    public World() {
        this(new ArrayList<>(), new HashMap<>());
    }

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


    // Compatibility with the existing flat-area API while the grid builder is unfinished.
    public List<Area> getAreas() {
        return areas.stream().flatMap(List::stream).toList();
    }

    public void addArea(Area area) {
        if (!getAreas().contains(area)) {
            if (areas.isEmpty()) {
                areas.add(new ArrayList<>());
            }
            areas.get(0).add(area);
        }
    }

    public boolean removeArea(Area area) {
        for (List<Area> row : areas) {
            if (row.remove(area)) {
                return true;
            }
        }
        return false;
    }

    public static class Builder {
        private final List<List<Area>> areas;
        private final Map<Character, Position> charactersToPositions;

        public Builder(){
            areas = new ArrayList<>();
            charactersToPositions = new HashMap<>();
        }

        public Builder randomGridOfNxM(int n, int m){
            //TODO
            return this;
        }

        public Builder setHomeVillageAtXY(int x, int y){
            //ensure x and y are within the size of the grid
            //home village is where a couple of merchants are guaranteed
            //TODO
            return this;
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
            return new World(areas, charactersToPositions);
        }
    }

    public record Position(int xPos, int yPos){

    }
}
