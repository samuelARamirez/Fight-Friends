package fightfriends;

import fightfriends.artifacts.Artifact;
import fightfriends.artifacts.Food;
import fightfriends.artifacts.FriendPotion;
import fightfriends.artifacts.Leash;

import java.util.ArrayList;
import java.util.List;

public class Backpack {
    private List<Artifact> items;
    private int capacity;
    static final String INDENT = "   ";
    private static final int DEFAULT_MAX_NUM_ARTIFACTS = 5;

    public Backpack() {
        this.items = new ArrayList<>();
        this.capacity = DEFAULT_MAX_NUM_ARTIFACTS;
    }

    public Backpack(List<Artifact> items, int capacity) {
        if (capacity < items.size()) {
            throw new IllegalArgumentException("Backpack capacity cannot be less than the number of items");
        }
        this.items = items;
        this.capacity = capacity;
    }

    public boolean contains(Object item) {
        return items.contains(item);
    }

    public int getCapacity() {
        return capacity;
    }

    public List<Artifact> getItems() {
        return items;
    }

    public void increaseCapacity(int additionalCapacity) {
        this.capacity += additionalCapacity;
    }

    public List<Food> getFoodList() {
        List<Food> foodList = new ArrayList<>();
        for(Artifact item: items) {
            if (item.getClass() == Food.class) {
                foodList.add((Food) item);
            }
        }
        return foodList;
    }

    public List<Leash> getLeashList() {
        List<Leash> leashList = new ArrayList<>();
        for(Artifact item: items) {
            if (item.getClass() == Leash.class) {
                leashList.add((Leash) item);
            }
        }
        return leashList;
    }

    public List<FriendPotion> getFriendPotionList() {
        List<FriendPotion> friendPotionList = new ArrayList<>();
        for(Artifact item: items) {
            if (item.getClass() == FriendPotion.class) {
                friendPotionList.add((FriendPotion) item);
            }
        }
        return friendPotionList;
    }

    @Override
    public String toString() {
        return getFoodListString() +
                getFriendPotionListString() +
                getLeashListString();
    }

    private String getFoodListString() {
        StringBuilder foodString = new StringBuilder();
        List<Food> foodList = getFoodList();
        if (!foodList.isEmpty()) {
            foodString.append("Food: \n");
            for (Food food: foodList) {
                foodString.append(INDENT).append(food.getName()).append(", ").append(food.getNutrition()).append("\n");
            }
            return foodString.toString();
        } else {
            return "";
        }
    }

    private String getFriendPotionListString() {
        StringBuilder friendPotionString = new StringBuilder();
        List<FriendPotion> friendPotionList = getFriendPotionList();
        if (!friendPotionList.isEmpty()) {
            friendPotionString.append("Friend Potions: \n");
            for (FriendPotion potion: friendPotionList) {
                friendPotionString.append(INDENT).append(potion.getName()).append(", ").append(potion.getStrengthPoints()).append("\n");
            }
            return friendPotionString.toString();
        } else {
            return "";
        }
    }

    private String getLeashListString() {
        StringBuilder leashString = new StringBuilder();
        List<Leash> leashList = getLeashList();
        if (!leashList.isEmpty()) {
            leashString.append("Leashes: \n");
            for (Leash leash: leashList) {
                leashString.append(INDENT).append(leash.getName()).append(", ").append(leash.getCapturePower()).append("\n");
            }
            return leashString.toString();
        } else {
            return "";
        }
    }

    public void add(Artifact artifact) {
        items.add(artifact);
    }

    public boolean hasRoom() {
        return items.size() < capacity;
    }

    public int getNumItemsInBackpack() {
        return items.size();
    }
}
