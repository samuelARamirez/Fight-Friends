package fightfriends.artifacts;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ArtifactFactory {
    private final String[] foodNames = {"apple", "bread", "wild berry", "cherries", "cheese"};
    private final String[] potionNames = {"Magic Elixir", "Water of Life", "Fruit Juice", "Baja Blast"};
    private final Random rand = new Random();

    private static final double MIN_FOOD_HP = 0.5;
    private static final double MAX_FOOD_HP = 2.0;
    private static final double MEAN_FOOD_HP = (MAX_FOOD_HP - MIN_FOOD_HP)/2.0;
    private static final double STD_DEV_FOOD_HP = (MAX_FOOD_HP - MIN_FOOD_HP)/3.0;

    private static final double MIN_POTION_HP = 1.0;
    private static final double MAX_POTION_HP = 5.0;
    private static final double MEAN_POTION_HP = (MAX_POTION_HP - MIN_POTION_HP)/2.0;
    private static final double STD_DEV_POTION_HP = (MAX_POTION_HP - MIN_POTION_HP)/3.0;
    private static final double MIN_POTION_STRENGTH = 1.0;
    private static final double MAX_POTION_STRENGTH = 5.0;
    private static final double MEAN_POTION_STRENGTH = (MAX_POTION_STRENGTH - MIN_POTION_STRENGTH)/2.0;
    private static final double STD_DEV_POTION_STRENGTH = (MAX_POTION_STRENGTH - MIN_POTION_STRENGTH)/3.0;


    private double getRandomFoodHP(){
        double randomValue =  rand.nextGaussian(MEAN_FOOD_HP, STD_DEV_FOOD_HP);
        if(randomValue < MIN_FOOD_HP){
            randomValue = MIN_FOOD_HP;
        }else if(randomValue > MAX_FOOD_HP){
            randomValue = MAX_FOOD_HP;
        }
        return randomValue;
    }

    private String getRandomFoodName(){
        return foodNames[rand.nextInt(0, foodNames.length)];
    }

    private double getRandomPotionHP(){
        double randomValue =  rand.nextGaussian(MEAN_POTION_HP, STD_DEV_POTION_HP);
        if(randomValue < MIN_POTION_HP){
            randomValue = MIN_POTION_HP;
        }else if(randomValue > MAX_POTION_HP){
            randomValue = MAX_POTION_HP;
        }
        return randomValue;
    }

    private double getRandomPotionStrength(){
        double randomValue =  rand.nextGaussian(MEAN_POTION_STRENGTH, STD_DEV_POTION_STRENGTH);
        if(randomValue < MIN_POTION_STRENGTH){
            randomValue = MIN_POTION_STRENGTH;
        }else if(randomValue > MAX_POTION_STRENGTH){
            randomValue = MAX_POTION_STRENGTH;
        }
        return randomValue;
    }

    private String getRandomPotionName(){
        return potionNames[rand.nextInt(0, potionNames.length)];
    }

    public Artifact createFood(String name, double healthValue){
        return new Food(name, healthValue);
    }

    public Artifact createFood(){
        return createFood(getRandomFoodName(), getRandomFoodHP());
    }
    
    public Artifact createPotion(String name, double HP, double strength){
        return new FriendPotion(name, HP, strength);
    }
    
    public Artifact createPotion(){
        return createPotion(getRandomPotionName(), getRandomPotionHP(), getRandomPotionStrength());
    }
    
    public List<Artifact> createNFoods(int nFoods){
        List<Artifact> foods = new ArrayList<>(nFoods);
        for(int i=0; i<nFoods; i++){
            foods.add(createFood());
        }
        return foods;
    }
    
    public List<Artifact> createNPotions(int nPotions){
        List<Artifact> potions = new ArrayList<>(nPotions);
        for(int i=0; i<nPotions; i++){
            potions.add(createPotion());
        }
        return potions;
    }
}
