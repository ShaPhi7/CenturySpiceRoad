package test;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

import board.Hand;
import card.Card;
import card.PointCard;
import card.SpiceCard;
import card.TradeCard;
import game.Game;
import game.Player;
import game.Spice;
import game.SpiceInventory;

public class HandTest {

	Player player = new Player(true);
	TradeCard card = new TradeCard();
	
	@Before
	public void setUp() {
		player = new Player(true);
		Game.setCurrentPlayer(player);
		player.addToHand(card);
		player.setSelectedCard(Optional.of(card));
	}
	
    @Test
    public void testValidateActionNotCurrentPlayer() {
        Game.setCurrentPlayer(new Player(false));
    	assertFalse(player.getHand().validateAction(player));
    }
    
    @Test
    public void testValidateActionWithNoCardSelected() {
    	player.setSelectedCard(Optional.empty());
        assertFalse(player.getHand().validateAction(player));
    }
    
    @Test
    public void testValidateActionWithNoCardInHand() {
    	player.getHand().getDeck().clear();
        assertFalse(player.getHand().validateAction(player));
    }
    
    @Test
    public void testValidateActionWithCardNotInHand() {
    	player.getHand().getDeck().clear();
    	player.addToHand(new TradeCard());
        assertFalse(player.getHand().validateAction(player));
    }
    
    @Test
    public void testValidateActionWithNonMerchantCard() {
    	PointCard pointCard = new PointCard();
		player.addToHand(pointCard);
    	player.setSelectedCard(Optional.of(pointCard));
        assertFalse(player.getHand().validateAction(player));
    }
    
    @Test
    public void testValidateActionWithOtherPlayersHand() {
    	Player otherPlayer = new Player(false);
    	otherPlayer.addToHand(card);
        assertFalse(otherPlayer.getHand().validateAction(player));
    }
    
    @Test
    public void testDoActionWithSpiceCard() {
    	
    	SpiceInventory spiceInventory = new SpiceInventory();
    	spiceInventory.addSpices(Spice.YELLOW_TUMERIC, 1);
    	spiceInventory.addSpices(Spice.RED_SAFFRON, 2);
    	spiceInventory.addSpices(Spice.GREEN_CARDAMOM, 3);
    	spiceInventory.addSpices(Spice.BROWN_CINNAMON, 4);
		SpiceCard spiceCard = new SpiceCard(spiceInventory);
		Optional<Card> spiceCardOptional = Optional.of(spiceCard);
    		
		player.addToHand(spiceCard);
		player.setSelectedCard(spiceCardOptional);
		
    	Hand hand = player.getHand();
		assertTrue(hand.validateAction(player));
    	hand.doAction(player);
    	
    	assertEquals(1, player.getSpiceCount(Spice.YELLOW_TUMERIC));
    	assertEquals(2, player.getSpiceCount(Spice.RED_SAFFRON));
    	assertEquals(3, player.getSpiceCount(Spice.GREEN_CARDAMOM));
    	assertEquals(4, player.getSpiceCount(Spice.BROWN_CINNAMON));
    	
    	assertFalse(player.getHand().contains(spiceCard));
    	assertTrue(player.getDiscard().contains(spiceCard));
    }
}
