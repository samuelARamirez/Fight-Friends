package fightfriends.characters;

import fightfriends.Collectible;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

public class MerchantTest {
    private static CharacterFactory factory = new CharacterFactory();

    @Test
    public void testCreation(){
        Merchant merchant = (Merchant) factory.createMerchant();

        assertFalse(merchant.viewItemsForSale().isEmpty());
        merchant.viewItemsForSale().forEach(item -> assertTrue(merchant.askPrice(item.getName()) > 0));
    }

    @Test
    public void testPurchasing(){
        Merchant merchant = (Merchant) factory.createMerchant();

        String nameOfFirstItem = merchant.viewItemsForSale().getFirst().getName();
        double priceOfFirstItem = merchant.askPrice(nameOfFirstItem);
        Collectible firstItem = merchant.purchaseItemByName(nameOfFirstItem);

        assertTrue(priceOfFirstItem >0);

        assertFalse(merchant.viewItemsForSale().contains(firstItem));

    }
}