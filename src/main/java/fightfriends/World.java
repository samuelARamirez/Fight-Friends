package fightfriends;

import fightfriends.areas.Area;

import java.util.ArrayList;
import java.util.List;

public class World {
    private final List<Area> areas = new ArrayList<>();

    public void addArea(Area area) {
        if (!areas.contains(area)) {
            areas.add(area);
        }
    }

    public boolean removeArea(Area area) {
        return areas.remove(area);
    }

    public List<Area> getAreas() {
        return List.copyOf(areas);
    }
}
