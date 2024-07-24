package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

import game.Game;
import game.MerchantCard;
import game.MerchantCardDeckRow;
import game.Player;
import game.PointCard;
import game.Spice;
import game.SpiceCard;
import game.TradeCard;
import game.UpgradeCard;

public class MerchantCardDeckRowTest {

	Player player = new Player(true);
	MerchantCardDeckRow merchantCardDeckRow = new MerchantCardDeckRow();
	SpiceCard spiceCard = new SpiceCard();
	UpgradeCard upgradeCard = new UpgradeCard();
	TradeCard tradeCard = new TradeCard();
	
    @Before
    public void setUp() {
    	player = new Player(true);
    	merchantCardDeckRow = new MerchantCardDeckRow();
    	spiceCard = new SpiceCard();
    	upgradeCard = new UpgradeCard();
    	tradeCard = new TradeCard();
        
        //Player setup
        Game.setCurrentPlayer(player);
        player.setSelectedCard(Optional.of(tradeCard));
        
        //Deck setup
        Game.merchantCardDeckRow = merchantCardDeckRow;
        merchantCardDeckRow.getDeck().add(tradeCard);
        for (int i=0;i<20;i++)
        {
        	merchantCardDeckRow.getDeck().add(new SpiceCard());
        	merchantCardDeckRow.getDeck().add(new TradeCard());
        	merchantCardDeckRow.getDeck().add(new UpgradeCard());
        }
    }

    @Test
    public void testValidatePlayerNotCurrentPlayer() {
        Game.setCurrentPlayer(new Player(false));
    	assertFalse(merchantCardDeckRow.validateAction(player));
    }
    
    @Test
    public void testValidatePlayerWithNoCard() {
    	player.setSelectedCard(Optional.empty());
        assertFalse(merchantCardDeckRow.validateAction(player));
    }

    @Test
    public void testValidatePlayerWithNonMerchantCard() {
    	player.setSelectedCard(Optional.of(new PointCard()));
    	assertFalse(merchantCardDeckRow.validateAction(player));
    }
    
    @Test
    public void testValidatePlayerWithPointCardNotInDeck() {
    	player.setSelectedCard(Optional.of(new TradeCard()));
    	assertFalse(merchantCardDeckRow.validateAction(player));
    }
    
    @Test
    public void testValidatePlayerWithPointCardNotInVisibleCards() {
    	
    	for (int i=0;i<10;i++)
    	{
    		merchantCardDeckRow.getDeck().add(0, new TradeCard());
    	}
    	
    	assertFalse(merchantCardDeckRow.validateAction(player));
    }
    
    @Test
    public void testValidatePlayerCanAfford() {
    	
    	merchantCardDeckRow.getDeck().add(0, new TradeCard());
    	merchantCardDeckRow.getDeck().add(0, new TradeCard());
    	
    	assertEquals(2, tradeCard.getCubeCostOf());
    	assertFalse(merchantCardDeckRow.validateAction(player));
    	
    	player.gainSpices(Spice.YELLOW_TUMERIC, 1);
    	assertFalse(merchantCardDeckRow.validateAction(player));
    	
    	player.gainSpices(Spice.YELLOW_TUMERIC, 1);
    	assertTrue(merchantCardDeckRow.validateAction(player));
    	
    	player.gainSpices(Spice.YELLOW_TUMERIC, 1);
    	assertTrue(merchantCardDeckRow.validateAction(player));
    }
    
    @Test
    public void testValidatePlayerAllowedToAcquireTradeCard() {

    	assertTrue(merchantCardDeckRow.validateAction(player));
    }
    
    @Test
    public void testValidatePlayerAllowedToAcquireUpgradeCard() {

    	player.setSelectedCard(Optional.of(upgradeCard));
        merchantCardDeckRow.getDeck().add(0, upgradeCard);
    	assertTrue(merchantCardDeckRow.validateAction(player));
    }
    
    @Test
    public void testValidatePlayerAllowedToAcquireSpiceCard() {

    	player.setSelectedCard(Optional.of(spiceCard));
        merchantCardDeckRow.getDeck().add(0, spiceCard);
    	assertTrue(merchantCardDeckRow.validateAction(player));
    }
    
    @Test
    public void testValidatePlayerPlaceCubes() {

    	MerchantCard lowestCard = new TradeCard();
    	MerchantCard lowerCard = new TradeCard();
    	MerchantCard firstPurchase = new TradeCard();
    	MerchantCard middleCard = new TradeCard();    	
    	MerchantCard secondPurchase = new TradeCard();
    	MerchantCard higherCard = new TradeCard();
    	
    	merchantCardDeckRow.getDeck().add(0, lowestCard); //1
    	merchantCardDeckRow.getDeck().add(1, lowerCard); //2
    	merchantCardDeckRow.getDeck().add(2, firstPurchase); //3
    	merchantCardDeckRow.getDeck().add(3, middleCard); //4
    	merchantCardDeckRow.getDeck().add(4, secondPurchase); //5
    	merchantCardDeckRow.getDeck().add(5, higherCard); //6
    	
    	player.gainSpices(Spice.YELLOW_TUMERIC, 10);
    	
    	player.setSelectedCard(Optional.of(firstPurchase));
    	assertTrue(merchantCardDeckRow.validateAction(player));
    	merchantCardDeckRow.doAction(player);
    	
    	assertEquals(8, player.getSpiceCount(Spice.YELLOW_TUMERIC));
    	assertEquals(1, lowestCard.getTotalNumberOfCubesOnCard());
    	assertEquals(1, lowerCard.getTotalNumberOfCubesOnCard());
    	assertEquals(0, firstPurchase.getTotalNumberOfCubesOnCard());
    	assertEquals(0, middleCard.getTotalNumberOfCubesOnCard());
    	assertEquals(0, secondPurchase.getTotalNumberOfCubesOnCard());
    	assertEquals(0, higherCard.getTotalNumberOfCubesOnCard());
    	
    	player.setSelectedCard(Optional.of(secondPurchase));
    	assertTrue(merchantCardDeckRow.validateAction(player));
    	merchantCardDeckRow.doAction(player);
    	
    	assertEquals(0, firstPurchase.getTotalNumberOfCubesOnCard());
    	assertEquals(5, player.getSpiceCount(Spice.YELLOW_TUMERIC));
    	assertEquals(2, lowestCard.getTotalNumberOfCubesOnCard());
    	assertEquals(2, lowerCard.getTotalNumberOfCubesOnCard());
    	assertEquals(1, middleCard.getTotalNumberOfCubesOnCard());
    	assertEquals(0, secondPurchase.getTotalNumberOfCubesOnCard());
    	assertEquals(0, higherCard.getTotalNumberOfCubesOnCard());
    }
    
    @Test
    public void testGetActionName() {
    	
    	assertEquals("Acquire", merchantCardDeckRow.getActionName());
    }
}
