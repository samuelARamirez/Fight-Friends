package fightfriends.areas;

import fightfriends.Collectible;
import java.util.ArrayList;
import java.util.List;

/** A named place in the world. Add exploration behavior here. */
public abstract class Area {
    private final String name;
    private final List<Collectible> collectibles = new ArrayList<>();

    protected Area(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void addCollectible(Collectible item) {
        if (!collectibles.contains(item)) {
            collectibles.add(item);
        }
    }

    public boolean removeCollectible(Collectible item) {
        return collectibles.remove(item);
    }

    public List<Collectible> getCollectibles() {
        return List.copyOf(collectibles);
    }

    public abstract String getBiome();
}
