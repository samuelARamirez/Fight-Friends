package fightfriends.areas;

public class Forest extends Area {
    public Forest(String name) {
        super(name);
    }

    @Override
    public String getBiome() {
        return "Forest";
    }
}
