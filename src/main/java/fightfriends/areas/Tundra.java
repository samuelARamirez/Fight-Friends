package fightfriends.areas;

public class Tundra extends Area {
    public Tundra(String name) {
        super(name);
    }

    @Override
    public String getBiome() {
        return "Tundra";
    }
}
