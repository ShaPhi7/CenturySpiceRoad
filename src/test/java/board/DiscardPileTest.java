package board;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import action.Action;
import action.CliOutputHandler;
import action.GameOutputHandler;
import card.Card;
import card.TradeCard;
import game.Game;
import game.Player;
import game.State;
import view.CliInputHandler;
import view.GameInputHandler;

public class DiscardPileTest {
	 
	Game game = new Game();
	Player player = new Player(game, true);
	GameInputHandler input = new CliInputHandler();
	GameOutputHandler output = new CliOutputHandler();
	
	DeckRow discard = player.getDiscard();
	
	@BeforeEach
	public void setUp() {
		game = new Game();
		player = new Player(game, true);
		input = new CliInputHandler();
		output = new CliOutputHandler();
		discard = player.getDiscard();
		player.getDiscard().getDeck().add(new TradeCard());
		game.setCurrentState(State.NEW_TURN);
		game.setCurrentPlayer(player);
		input.setPlayer(player);
		input.setAction(Action.REST);
	}
	 
	@Test
	public void testRestAction() {
	        
		// Before action: discard should have cards and hand should be empty
		Set<Card> discardBefore = new HashSet<>(discard.getDeck());
		Set<Card> handBefore = new HashSet<>(player.getHand().getDeck());
		
		// Perform action
		discard.doAction(input, output);
		
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
    	game.setCurrentPlayer(new Player(game, false));
    	assertFalse(discard.validateAction(input, output));
    }
    
    @Test
    public void testValidateActionWithNoCardInDiscard() {
    	discard.getDeck().clear();
        assertFalse(discard.validateAction(input, output));
    }
    
    @Test
    public void testValidateActionWithOtherPlayersDiscard() {
    	Player otherPlayer = new Player(game, false);
    	DiscardPile otherDiscard = otherPlayer.getDiscard();
    	otherDiscard.getDeck().add(new TradeCard());
        assertFalse(otherDiscard.validateAction(input, output));
    }
 
    @Test
    public void testValidateActionWithNoCardSelected() {
    	input.setSelectedCard(Optional.empty());
        assertTrue(discard.validateAction(input, output));
    }
    
    @Test
    public void testValidateActionWithCardSelected() {
    	input.setSelectedCard(Optional.of(new TradeCard()));
        assertTrue(discard.validateAction(input, output));
    }
    
    @Test
    public void testGetActionName() {
		DeckRow discard = player.getDiscard();
    	assertEquals(Action.REST, discard.getAction());
    }
}
