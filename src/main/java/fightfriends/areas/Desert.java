package fightfriends.areas;

public class Desert extends Area {
    public Desert(String name) {
        super(name);
    }

    @Override
    public String getBiome() {
        return "Desert";
    }
}
