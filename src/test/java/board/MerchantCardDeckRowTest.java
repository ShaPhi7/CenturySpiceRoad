package board;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import card.MerchantCard;
import card.PointCard;
import card.SpiceCard;
import card.TradeCard;
import card.UpgradeCard;
import game.Game;
import game.Player;
import game.Spice;
import game.SpiceInventory;

public class MerchantCardDeckRowTest {

	Player player = new Player(true);
	MerchantCardDeckRow merchantCardDeckRow = new MerchantCardDeckRow();
	SpiceCard spiceCard = new SpiceCard();
	UpgradeCard upgradeCard = new UpgradeCard();
	TradeCard tradeCard = new TradeCard();
	
    @BeforeEach
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
    	player.setSelectedCard(Optional.of(new TradeCard(new SpiceInventory(1,0,0,0), new SpiceInventory(0,0,0,2))));
    	assertFalse(merchantCardDeckRow.validateAction(player));
    }
    
    @Test
    public void testValidatePlayerWithPointCardNotInVisibleCards() {
    	
    	for (int i=0;i<10;i++)
    	{
    		merchantCardDeckRow.getDeck().add(0, new TradeCard(new SpiceInventory(1,0,0,0),new SpiceInventory(0,0,0,2)));
    	}
    	
    	assertFalse(merchantCardDeckRow.validateAction(player));
    }
    
    @Test
    public void testValidatePlayerCanAfford() {
    	
    	merchantCardDeckRow.getDeck().add(0, new TradeCard(new SpiceInventory(0,0,0,0),new SpiceInventory(0,0,0,1)));
    	merchantCardDeckRow.getDeck().add(0, new TradeCard(new SpiceInventory(0,0,0,0),new SpiceInventory(0,0,0,2)));
    	
    	assertEquals(2, tradeCard.getCostToAcquire());
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

    	MerchantCard lowestCard = new TradeCard(new SpiceInventory(1,0,0,0),new SpiceInventory(0,0,0,1));
    	MerchantCard lowerCard = new TradeCard(new SpiceInventory(2,0,0,0),new SpiceInventory(0,0,0,1));
    	MerchantCard firstPurchase = new TradeCard(new SpiceInventory(3,0,0,0),new SpiceInventory(0,0,0,1));
    	MerchantCard middleCard = new TradeCard(new SpiceInventory(4,0,0,0),new SpiceInventory(0,0,0,1));    	
    	MerchantCard secondPurchase = new TradeCard(new SpiceInventory(5,0,0,0),new SpiceInventory(0,0,0,1));
    	MerchantCard higherCard = new TradeCard(new SpiceInventory(6,0,0,0),new SpiceInventory(0,0,0,1));
    	
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
    public void testGainCubes() {
    	
    	tradeCard.placeCubeOnCard(Spice.YELLOW_TUMERIC);
    	merchantCardDeckRow.doAction(player);
    	assertEquals(1, player.getSpiceCount(Spice.YELLOW_TUMERIC));
    }
    
    @Test
    public void testGetActionName() {
    	
    	assertEquals("Acquire", merchantCardDeckRow.getActionName());
    }
}
