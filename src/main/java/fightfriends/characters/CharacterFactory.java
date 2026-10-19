package fightfriends.characters;

import fightfriends.Collectible;
import fightfriends.Gold;
import fightfriends.artifacts.Artifact;
import fightfriends.artifacts.ArtifactFactory;
import fightfriends.friends.Friend;

import java.util.*;

public class CharacterFactory {
    private static final ArtifactFactory artifactFactory = new ArtifactFactory();
    private static final Random rand = new Random();

    private static final String[] MERCHANT_NAMES = {"Melvin", "Marv", "Marco", "Mlav the Blacksmith", "Madeline" };
    private static final String[] TRAINER_NAMES = {"Steve", "Anthony", "Bartholomew", "Anita", "Lestra", "Daphne",
            "Violet", "Tegan"};

    private static final int DEFAULT_NUM_ITEMS_FOR_SALE = 3;
    private static final double DEFAULT_GOLD_PER_POWER = 2.5;
    private static final double CHANCE_OF_POTION = .4;
    private static final double CHANCE_OF_FOOD = .4;
    private static final double CHANCE_OF_GOLD = 1-(CHANCE_OF_FOOD+CHANCE_OF_POTION);
    private static final double MIN_AMT_GOLD = 10;
    private static final double MAX_AMT_GOLD = 50;

    private static String getRandomMerchantName(){
        return MERCHANT_NAMES[rand.nextInt(0, MERCHANT_NAMES.length)];
    }


    private static String getRandomTrainerName(){
        return TRAINER_NAMES[rand.nextInt(0, TRAINER_NAMES.length)];
    }

    private static double findPrice(Collectible item){
        return item.getPower()*DEFAULT_GOLD_PER_POWER;
    }

    //TODO add in the ability to randomly sell friends
    private Map<Collectible, Double> getRandomMerchantItems(){
        Map<Collectible, Double> itemsForSale = new HashMap<>();
        for(int i=0;i<DEFAULT_NUM_ITEMS_FOR_SALE; i++){
            double randomNumber = rand.nextDouble(0, 1);
            Collectible itemForSale;
            if(randomNumber < CHANCE_OF_FOOD ){
                //make food
                 itemForSale = artifactFactory.createFood();
            }else if(randomNumber < CHANCE_OF_FOOD+CHANCE_OF_POTION){
                //make potion
                itemForSale = artifactFactory.createPotion();
            }else{
                //make gold
                //TODO don't use concrete subclass here, maybe need a CollectibleFactory?
                itemForSale = new Gold("Gold", rand.nextDouble(MIN_AMT_GOLD, MAX_AMT_GOLD));
            }
            itemsForSale.put(itemForSale, findPrice(itemForSale));
        }
        return itemsForSale;
    }

    public Character createMerchant(){
        return createMerchant(getRandomMerchantName(), getRandomMerchantItems());
    }

    public Character createMerchant(String name, Map<Collectible, Double> itemsForSale){
        return new Merchant(name, itemsForSale);
    }

    public Character createTrainer(){
        //TODO add in logic to get a random list of friends (probably more functions need to be involved)
        return createTrainer(getRandomTrainerName());
    }

    public Character createTrainer(String name){
        return new Trainer(name);

    }

    public Character createPlayer(){
        //TODO this should be looked at to make sure its working
        return createPlayer(getRandomTrainerName());
    }

    public Character createPlayer(String name){
        //TODO probably need to update fields in this function
        return new Player(name);

    }


}
