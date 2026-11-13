package fightfriends;

import fightfriends.areas.Area;
import fightfriends.characters.Character;
import fightfriends.characters.Player;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

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
        return charactersToPositions.get(character);
    }

    public Area getAreaAt(int x, int y) {
        return getAreaAt(new Position(x, y));
    }

    public Area getAreaAt(Position position){
        if (position == null || position.yPos() < 0 || position.yPos() >= areas.size()) {
            return null;
        }
        List<Area> row = areas.get(position.yPos());
        if (position.xPos() < 0 || position.xPos() >= row.size()) {
            return null;
        }
        return row.get(position.xPos());
    }

    public List<Character> getCharactersAt(int x, int y) {
        return getCharactersAt(new Position(x, y));
    }

    public List<Character> getCharactersAt(Position position) {
        List<Character> characters = new ArrayList<>();
        for (Character character : charactersToPositions.keySet()) {
            if (charactersToPositions.get(character).equals(position)) {
                characters.add(character);
            }
        }
        return List.copyOf(characters);
    }

    // The map is the only stored source for both the player and character locations.
    public Player getPlayer() {
        for (Character character : charactersToPositions.keySet()) {
            if (Player.class.isInstance(character)) {
                return Player.class.cast(character);
            }
        }
        return null;
    }

    public Area getPlayerArea() {
        return getAreaAt(findLocationOf(getPlayer()));
    }

    public boolean movePlayer(int x, int y) {
        Player player = getPlayer();
        Position current = findLocationOf(player);
        Area destination = getAreaAt(x, y);
        if (current == null || destination == null) {
            return false;
        }
        int distance = Math.abs(x - current.xPos()) + Math.abs(y - current.yPos());
        if (distance != 1) {
            return false;
        }
        charactersToPositions.put(player, new Position(x, y));
        player.visitArea(destination);
        return true;
    }

    public boolean pickup(Collectible item) {
        Area area = getPlayerArea();
        if (area == null || !area.getCollectibles().contains(item)) {
            return false;
        }
        if (!getPlayer().pickup(item)) {
            return false;
        }
        area.removeCollectible(item);
        return true;
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
        for (int y = 0; y < areas.size(); y++) {
            List<Area> row = areas.get(y);
            int x = row.indexOf(area);
            if (x >= 0) {
                if (!getCharactersAt(x, y).isEmpty()) {
                    return false;
                }
                row.remove(x);
                for (Character character : charactersToPositions.keySet()) {
                    Position position = charactersToPositions.get(character);
                    if (position.yPos() == y && position.xPos() > x) {
                        charactersToPositions.put(character, new Position(position.xPos() - 1, y));
                    }
                }
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

        // Areas are supplied row by row; x is the column and y is the row.
        public Builder createGridOfAreas(int rows, int columns, List<Area> gridAreas) {
            if (rows <= 0 || columns <= 0 || gridAreas.size() != rows * columns) {
                throw new IllegalArgumentException("Supply one area for every grid position");
            }
            for (Area area : gridAreas) {
                if (area == null) {
                    throw new IllegalArgumentException("Grid areas cannot be null");
                }
            }
            areas.clear();
            charactersToPositions.clear();
            for (int y = 0; y < rows; y++) {
                List<Area> row = new ArrayList<>();
                for (int x = 0; x < columns; x++) {
                    row.add(gridAreas.get(y * columns + x));
                }
                areas.add(row);
            }
            return this;
        }

        public Builder addCharacterAtXY(int x, int y, Character character) {
            if (y < 0 || y >= areas.size() || x < 0 || x >= areas.get(y).size()) {
                throw new IllegalArgumentException("Character position is outside the world");
            }
            charactersToPositions.put(character, new Position(x, y));
            return this;
        }

        public Builder addPlayerAtXY(int x, int y, Player player) {
            return addCharacterAtXY(x, y, player);
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
            return addPlayerAtXY(0, 0, player);
        }

        public Builder addCharactersRandomly(List<Character> charactersToAdd){
            //TODO
            return this;
        }

        public Builder distributeCollectiblesRandomly(List<Collectible> collectibles){
            if (areas.isEmpty()) {
                throw new IllegalStateException("Create the areas before placing collectibles");
            }
            Random random = new Random();
            for (Collectible item : collectibles) {
                int y = random.nextInt(areas.size());
                int x = random.nextInt(areas.get(y).size());
                addCollectibleAtXY(x, y, item);
            }
            return this;
        }

        public Builder addCollectibleAtXY(int x, int y, Collectible thingToAdd){
            if (y < 0 || y >= areas.size() || x < 0 || x >= areas.get(y).size()) {
                throw new IllegalArgumentException("Collectible position is outside the world");
            }
            areas.get(y).get(x).addCollectible(thingToAdd);
            return this;
        }

        public Builder addCharacterToArea(String areaName, Character character){
            for (int y = 0; y < areas.size(); y++) {
                for (int x = 0; x < areas.get(y).size(); x++) {
                    if (areas.get(y).get(x).getName().equals(areaName)) {
                        return addCharacterAtXY(x, y, character);
                    }
                }
            }
            throw new IllegalArgumentException("No area named " + areaName);
        }

        public World build(){
            if (areas.isEmpty()) {
                throw new IllegalStateException("A world needs at least one area");
            }
            int playerCount = 0;
            for (Character character : charactersToPositions.keySet()) {
                if (Player.class.isInstance(character)) {
                    playerCount++;
                }
            }
            if (playerCount != 1) {
                throw new IllegalStateException("A world needs exactly one player");
            }
            List<List<Area>> copiedAreas = new ArrayList<>();
            for (List<Area> row : areas) {
                copiedAreas.add(new ArrayList<>(row));
            }
            World world = new World(copiedAreas, new HashMap<>(charactersToPositions));
            world.getPlayer().visitArea(world.getPlayerArea());
            return world;
        }
    }

    public record Position(int xPos, int yPos){

    }
}
