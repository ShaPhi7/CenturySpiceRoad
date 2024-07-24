package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import game.Card;
import game.DeckRow;
import game.DiscardPile;
import game.Game;
import game.Player;
import game.TradeCard;

public class DiscardPileTest {
	 
	Player player = new Player(true);
	DeckRow discard = player.getDiscard();
	
	@Before
	public void setUp() {
		player = new Player(true);
		discard = player.getDiscard();
		player.getDiscard().getDeck().add(new TradeCard());
		Game.setCurrentPlayer(player);
	}
	 
	@Test
	public void testRestAction() {
	        
		// Before action: discard should have cards and hand should be empty
		Set<Card> discardBefore = new HashSet<>(discard.getDeck());
		Set<Card> handBefore = new HashSet<>(player.getHand().getDeck());
		
		// Perform action
		discard.doAction(player);
		
		// After action: discard should be empty and hand should contain the cards
		List<Card> discardAfter = discard.getDeck();
		List<Card> handAfter = player.getHand().getDeck();
		
		// Verify discard is empty
		assertTrue(discardAfter.isEmpty(), "Discard set should be empty after action");
		
		// Verify hand contains all cards that were in discard
		assertTrue(handAfter.containsAll(discardBefore), "Hand should contain all cards from discard");
		assertTrue(handAfter.containsAll(handBefore), "Hand should still contain all cards");
		assertEquals(handAfter.size(), discardBefore.size(), "Hand size should match number of cards moved from discard");
	 }
	 
    @Test
    public void testValidateActionNotCurrentPlayer() {
    	Game.setCurrentPlayer(new Player(false));
    	assertFalse(discard.validateAction(player));
    }
    
    @Test
    public void testValidateActionWithNoCardInDiscard() {
    	discard.getDeck().clear();
        assertFalse(discard.validateAction(player));
    }
    
    @Test
    public void testValidateActionWithOtherPlayersDiscard() {
    	Player otherPlayer = new Player(false);
    	DiscardPile otherDiscard = otherPlayer.getDiscard();
    	otherDiscard.getDeck().add(new TradeCard());
        assertFalse(otherDiscard.validateAction(player));
    }
 
    @Test
    public void testValidateActionWithNoCardSelected() {
    	player.setSelectedCard(Optional.empty());
        assertTrue(discard.validateAction(player));
    }
    
    @Test
    public void testValidateActionWithCardSelected() {
    	player.setSelectedCard(Optional.of(new TradeCard()));
        assertTrue(discard.validateAction(player));
    }
    
    @Test
    public void testGetActionName() {
		DeckRow discard = player.getDiscard();
    	assertEquals("Rest", discard.getActionName());
    }
}
