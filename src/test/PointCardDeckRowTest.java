package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

import game.Card;
import game.Game;
import game.Hand;
import game.Player;
import game.PointCard;
import game.PointCardDeckRow;
import game.Spice;
import game.SpiceInventory;
import game.TradeCard;

public class PointCardDeckRowTest {

	Player player = new Player(true);
	PointCardDeckRow pointCardDeckRow = new PointCardDeckRow();
	PointCard pointCard = new PointCard();
	
    @Before
    public void setUp() {
    	player = new Player(true);
    	pointCardDeckRow = new PointCardDeckRow();
    	pointCard = new PointCard();
        
        //Player setup
        Game.setCurrentPlayer(player);
        player.setSelectedCard(Optional.of(pointCard));
        
        //Deck setup
        pointCardDeckRow.getDeck().add(pointCard);
        for (int i=0;i<50;i++)
        {
        	pointCardDeckRow.getDeck().add(new PointCard());
        }
    }

    @Test
    public void testValidatePlayerNotCurrentPlayer() {
        Game.setCurrentPlayer(new Player(false));
    	assertFalse(pointCardDeckRow.validateAction(player));
    }
    
    @Test
    public void testValidatePlayerWithNoCard() {
    	player.setSelectedCard(Optional.empty());
        assertFalse(pointCardDeckRow.validateAction(player));
    }

    @Test
    public void testValidatePlayerWithNonPointCard() {
    	player.setSelectedCard(Optional.of(new TradeCard()));
    	assertFalse(pointCardDeckRow.validateAction(player));
    }
    
    @Test
    public void testValidatePlayerWithPointCardNotInDeck() {
    	player.setSelectedCard(Optional.of(new PointCard()));
    	assertFalse(pointCardDeckRow.validateAction(player));
    }
    
    @Test
    public void testValidatePlayerWithPointCardNotInVisibleCards() {
    	
    	for (int i=0;i<10;i++)
    	{
    		pointCardDeckRow.getDeck().add(0, new PointCard());
    	}
    	
    	assertFalse(pointCardDeckRow.validateAction(player));
    }
    
    @Test
    public void testValidatePlayerCanNotAfford() {
    	
    	SpiceInventory spiceInventory = new SpiceInventory();
		spiceInventory.addSpices(Spice.YELLOW_TUMERIC, 1);
    	pointCard.setCost(spiceInventory);
    	assertFalse(pointCardDeckRow.validateAction(player));
    }
    
    @Test
    public void testValidatePlayerTooManyPointCards() {
    	
    	List<Card> pointCards = new ArrayList<>(); 
    	for (int i=0;i<7;i++)
    	{
    		pointCards.add(new PointCard());
    	}
    	Hand hand = new Hand(pointCards);
    	player.setHand(hand);
    	
    	assertFalse(pointCardDeckRow.validateAction(player));
    }
    
    @Test
    public void testValidatePlayerAllowedToAcquirePointCard() {
    	
    	List<Card> pointCards = new ArrayList<>(); 
    	for (int i=0;i<6;i++)
    	{
    		pointCards.add(new PointCard());
    	}
    	Hand hand = new Hand(pointCards);
    	player.setHand(hand);
    	
    	assertTrue(pointCardDeckRow.validateAction(player));
    }
    
    @Test
    public void testDoActionCardCost() {
    	
    	SpiceInventory pointCardCost = new SpiceInventory();
    	pointCardCost.addSpices(Spice.YELLOW_TUMERIC, 2);
    	pointCardCost.addSpices(Spice.RED_SAFFRON, 3);
    	pointCardCost.addSpices(Spice.GREEN_CARDAMOM, 1);
    	pointCardCost.addSpices(Spice.BROWN_CINNAMON, 0);
    	
		pointCard.setCost(pointCardCost);
    	
		SpiceInventory caravan = new SpiceInventory();
		caravan.addSpices(Spice.YELLOW_TUMERIC, 4);
		caravan.addSpices(Spice.RED_SAFFRON, 4);
		caravan.addSpices(Spice.GREEN_CARDAMOM, 1);
		caravan.addSpices(Spice.BROWN_CINNAMON, 1);
    	
    	player.setCaravan(caravan);
    	
    	assertTrue(pointCardDeckRow.validateAction(player));
    	pointCardDeckRow.doAction(player);
    	
    	assertEquals(player.getCaravan().getQuantity(Spice.YELLOW_TUMERIC), 2);
    	assertEquals(player.getCaravan().getQuantity(Spice.RED_SAFFRON), 1);
    	assertEquals(player.getCaravan().getQuantity(Spice.GREEN_CARDAMOM), 0);
    	assertEquals(player.getCaravan().getQuantity(Spice.BROWN_CINNAMON), 1);
    }
    
    @Test
    public void testDoActionGainGoldCoinFirstIndex() {
    	
    	pointCardDeckRow.setGoldCoins(2);
    	pointCardDeckRow.setSilverCoins(2);
    	
    	assertTrue(pointCardDeckRow.validateAction(player));
    	pointCardDeckRow.doAction(player);
    	
    	assertEquals(player.getGoldCoinCount(), 1);
    	assertEquals(player.getSilverCoinCount(), 0);

    	assertEquals(pointCardDeckRow.getGoldCoins(), 1);
    	assertEquals(pointCardDeckRow.getSilverCoins(), 2);
    }
    
    @Test
    public void testDoActionGainSilverCoinFirstIndex() {
    	
    	pointCardDeckRow.setGoldCoins(0);
    	pointCardDeckRow.setSilverCoins(2);
    	
    	assertTrue(pointCardDeckRow.validateAction(player));
    	pointCardDeckRow.doAction(player);
    	
    	assertEquals(player.getGoldCoinCount(), 0);
    	assertEquals(player.getSilverCoinCount(), 1);

    	assertEquals(pointCardDeckRow.getGoldCoins(), 0);
    	assertEquals(pointCardDeckRow.getSilverCoins(), 1);
    }
    
    @Test
    public void testDoActionGainSilverCoinSecondIndex() {
    	
    	pointCardDeckRow.setGoldCoins(2);
    	pointCardDeckRow.setSilverCoins(2);
    	
    	pointCardDeckRow.getDeck().add(0, new PointCard());
    	
    	assertTrue(pointCardDeckRow.validateAction(player));
    	pointCardDeckRow.doAction(player);
    	
    	assertEquals(player.getGoldCoinCount(), 0);
    	assertEquals(player.getSilverCoinCount(), 1);

    	assertEquals(pointCardDeckRow.getGoldCoins(), 2);
    	assertEquals(pointCardDeckRow.getSilverCoins(), 1);
    }
    
    @Test
    public void testDoActionGainNoCoinSecondIndex() {
    	
    	pointCardDeckRow.setGoldCoins(2);
    	pointCardDeckRow.setSilverCoins(0);
    	
    	pointCardDeckRow.getDeck().add(0, new PointCard());
    	
    	assertTrue(pointCardDeckRow.validateAction(player));
    	pointCardDeckRow.doAction(player);
    	
    	assertEquals(player.getGoldCoinCount(), 0);
    	assertEquals(player.getGoldCoinCount(), 0);
    	assertEquals(pointCardDeckRow.getGoldCoins(), 2);
    	assertEquals(pointCardDeckRow.getSilverCoins(), 0);
    }
    
    @Test
    public void testDoActionGainNoCoinSecondIndexNoCoinsAtAll() {
    	
    	pointCardDeckRow.setGoldCoins(0);
    	pointCardDeckRow.setSilverCoins(0);
    	
    	pointCardDeckRow.getDeck().add(0, new PointCard());
    	
    	assertTrue(pointCardDeckRow.validateAction(player));
    	pointCardDeckRow.doAction(player);
    	
    	assertEquals(player.getGoldCoinCount(), 0);
    	assertEquals(player.getGoldCoinCount(), 0);
    	assertEquals(pointCardDeckRow.getGoldCoins(), 0);
    	assertEquals(pointCardDeckRow.getSilverCoins(), 0);
    }
    
    @Test
    public void testDoActionGainNoCoinThirdIndex() {
    	
    	pointCardDeckRow.getDeck().add(0, new PointCard());
    	pointCardDeckRow.getDeck().add(0, new PointCard());
    	
    	pointCardDeckRow.setGoldCoins(0);
    	pointCardDeckRow.setSilverCoins(0);
    	
    	assertTrue(pointCardDeckRow.validateAction(player));
    	pointCardDeckRow.doAction(player);
    	
    	assertEquals(player.getGoldCoinCount(), 0);
    	assertEquals(player.getGoldCoinCount(), 0);
    	assertEquals(pointCardDeckRow.getGoldCoins(), 0);
    	assertEquals(pointCardDeckRow.getSilverCoins(), 0);
    }
    
    @Test
    public void testGetActionName() {
    	
    	assertEquals(pointCardDeckRow.getActionName(), "Claim");
    }
    
}
