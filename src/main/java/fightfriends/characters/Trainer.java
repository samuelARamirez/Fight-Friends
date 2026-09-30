package fightfriends.characters;

import fightfriends.areas.Area;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class Trainer {
    private final String name;
    private final List<Friend> friends = new ArrayList<>();
    private final Set<Area> visitedAreas = new LinkedHashSet<>();

    public Trainer(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void addFriend(Friend friend) {
        if (!friends.contains(friend)) {
            friends.add(friend);
        }
    }

    public boolean removeFriend(Friend friend) {
        return friends.remove(friend);
    }

    public List<Friend> getFriends() {
        return List.copyOf(friends);
    }

    public void visitArea(Area area) {
        visitedAreas.add(area);
    }

    public boolean hasVisited(Area area) {
        return visitedAreas.contains(area);
    }

    /** Returns unique areas in the order they were first visited. */
    public List<Area> getVisitedAreas() {
        return List.copyOf(visitedAreas);
    }
}
