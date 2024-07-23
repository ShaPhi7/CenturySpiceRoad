package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import action.RestAction;
import game.Card;
import game.Game;
import game.Player;

public class ActionTest {
	 
	 @Before
	 public void setUp() {
		 Player.setupPlayers(Game.players);
	 }
	
	 @Test
	 public void testRestAction() {
	        
		 
		 Player player = Game.players.getFirst();
		 RestAction restAction = new RestAction(); 
		 // Before action: discard should have cards and hand should be empty
	        Set<Card> discardBefore = new HashSet<>(player.getDiscard());
	        Set<Card> handBefore = new HashSet<>(player.getHand());

	        // Perform action
	        restAction.doAction(player);

	        // After action: discard should be empty and hand should contain the cards
	        Set<Card> discardAfter = player.getDiscard();
	        Set<Card> handAfter = player.getHand();

	        // Verify discard is empty
	        assertTrue(discardAfter.isEmpty(), "Discard set should be empty after action");

	        // Verify hand contains all cards that were in discard
	        assertTrue(handAfter.containsAll(discardBefore), "Hand should contain all cards from discard");
	        assertTrue(handAfter.containsAll(handBefore), "Hand should still contain all cards");
	        assertEquals(handAfter.size(), discardBefore.size(), "Hand size should match number of cards moved from discard");
	    }
	}
