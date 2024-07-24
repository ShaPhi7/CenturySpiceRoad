package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import game.Card;
import game.DeckRow;
import game.Game;
import game.Player;

public class DiscardPileTest {
	 
	 @Before
	 public void setUp() {
		Player.setupPlayers(Game.players);
	 }
	 
	 @Test
	 public void testRestAction() {
	        
		Player player = Game.players.getFirst();
		DeckRow discard = player.getDiscard();
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
	    public void testGetActionName() {
			Player player = Game.players.getFirst();
			DeckRow discard = player.getDiscard();
	    	assertEquals("Rest", discard.getActionName());
	    }
	}
