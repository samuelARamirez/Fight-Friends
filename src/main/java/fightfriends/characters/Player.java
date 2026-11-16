package fightfriends.characters;

import fightfriends.Backpack;
import fightfriends.Collectible;
import fightfriends.Gold;
import fightfriends.areas.Area;
import fightfriends.artifacts.Artifact;
import fightfriends.friends.Friend;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class Player extends Trainer{
    private static final Logger logger  = LoggerFactory.getLogger(fightfriends.characters.Player.class);
    private final Set<Area> visitedAreas = new LinkedHashSet<>();
    private final Backpack backpack;
    private double goldBalance;

    private static final double DEFAULT_INITIAL_BALANCE = 100.0;

    public Player(String name){
        this(name,
            DEFAULT_HP,
            new ArrayList<>(DEFAULT_MAX_NUM_FRIENDS),
            DEFAULT_MAX_NUM_FRIENDS,
            new Backpack(),
            DEFAULT_INITIAL_BALANCE);
    }

    public Player(String name, double health_points, List<Friend> friends, int maxNumFriends, Backpack backpack, double goldBalance){
        super(name, health_points, friends, maxNumFriends);
        this.goldBalance = goldBalance;
        this.backpack = backpack;
    }

    public void visitArea(Area area) {
        visitedAreas.add(area);
    }

    public boolean hasVisited(Area area) {
        return visitedAreas.contains(area);
    }

    public double getGoldBalance(){
        return this.goldBalance;
    }

    public Backpack getBackpack(){
        return this.backpack;
    }

    /** Returns unique areas in the order they were first visited. */
    public List<Area> getVisitedAreas() {
        return List.copyOf(visitedAreas);
    }

    public void spendMoney(double amount){
        goldBalance -= amount;
        // intentionally allowing negative balance for now
    }

    /**
     * attempts to add an item to inventory
     * @param something what to add to player
     * @return true if successful
     */
    public boolean pickup(Collectible something){
        if (Gold.class.isInstance(something)) {
            return pickupGold(Gold.class.cast(something));
        }
        if (Artifact.class.isInstance(something)) {
            return pickupArtifact(Artifact.class.cast(something));
        }
        if (Friend.class.isInstance(something)) {
            return pickupFriend(Friend.class.cast(something));
        }
        return false;
    }

    private boolean pickupGold(Gold gold) {
        goldBalance += gold.getAmount();
        return true;
    }

    private boolean pickupArtifact(Artifact artifact) {
        if (!backpack.hasRoom() || backpack.contains(artifact)) {
            return false;
        }
        backpack.add(artifact);
        return true;
    }

    private boolean pickupFriend(Friend friend) {
        int previousSize = getFriends().size();
        addFriend(friend);
        return getFriends().size() > previousSize;
    }

    /**
     * Attempts to purchase an item from a Merchant
     * @param merchant The Merchant to purchase from
     * @param name The name of the item to purchase
     * @return true if item purchased successfully, false otherwise
     */
    public boolean purchaseItem(Merchant merchant, String name){
        Double itemPrice = merchant.askPrice(name);
        if(itemPrice == null || itemPrice <0){
            logger.info("Not for sale: " + name);
            return false;
        } else if(itemPrice > goldBalance){
            logger.info("Cannot afford "+name+" for $" + itemPrice);
            return false;
        }

        Collectible purchasedItem = merchant.purchaseItemByName(name);
        if(pickup(purchasedItem)){
            // added to backpack successfully
            this.spendMoney(itemPrice);
            logger.info("Purchased "+ purchasedItem.getName()+" for "+itemPrice);
            return true;
        }else{
            merchant.returnItem(purchasedItem, itemPrice);
            logger.info("Could not purchase "+purchasedItem.getName()+" because inventory could not accept it");
            //TODO: add in a choice of returning the item, using the item, purchasing more backpack spae, or dropping/consuming something out of the backpack
            return false;
        }
    }

    @Override
    public boolean isPlayer(){
        return true;
    }
}
