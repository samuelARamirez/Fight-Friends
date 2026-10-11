package fightfriends.characters;

import fightfriends.Collectible;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Merchant extends Character{
    private Map<Collectible, Double> itemsForSale;

    public Merchant(String name, Map<Collectible, Double> itemsForSale){
        super(name);
        this.itemsForSale = itemsForSale;
    }

    public List<Collectible> viewItemsForSale(){
        return itemsForSale.keySet().stream().toList();
    }

    private Collectible lookupItemByName(String name){
        return itemsForSale.keySet().stream().filter(
                possibleItem -> Objects.equals(possibleItem.getName(), name)
            ).toList().getFirst();
    }

    public Collectible purchaseItemByName(String name){
        Collectible item = lookupItemByName(name);
        if(item != null){
            itemsForSale.remove(item);
        }
        return item;
    }

    public void returnItem(Collectible item, double price){
        itemsForSale.put(item, price);
    }

    public Double askPrice(String name){
        return itemsForSale.get(lookupItemByName(name));
    }

    @Override
    public boolean isMerchant() {
        return true;
    }
}

