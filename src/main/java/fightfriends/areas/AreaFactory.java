package fightfriends.areas;

public class AreaFactory {

    public Area createDesert(String name) {
        return new Desert(name);
    }

    public Area createForest(String name) {
        return new Forest(name);
    }

    public Area createTundra(String name) {
        return new Tundra(name);
    }
}
