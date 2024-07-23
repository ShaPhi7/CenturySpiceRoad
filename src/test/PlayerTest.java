package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.Before;
import org.junit.Test;

import game.Game;
import game.Player;

	public class PlayerTest {

	    @Before
	    public void setUp() {
	    	Game.players.clear();
	        Player.setupPlayers(Game.players);
	    }

	    @Test
	    public void testNumberOfPlayers() {
	    	assertEquals(Game.NUMBER_OF_PLAYERS, Game.players.size(), "Incorrect number of players.");
	    }

	    @Test
	    public void testFirstPlayerIsStartingPlayer() {
	    	assertTrue(Game.players.get(0).isStartingPlayer(), "First player is not the starting player.");
	    }

	    @Test
	    public void testOtherPlayersAreNotStartingPlayers() {
	        for (int i = 1; i < Game.players.size(); i++) {
	        	assertFalse(Game.players.get(i).isStartingPlayer(), "Non-first player is the starting player.");
	        }
	    }

	    @Test
	    public void testNextPlayer() {
	        Player.nextPlayer(Game.players);

	        // Check if the first player has been moved to the end
	        assertFalse(Game.players.getFirst().isStartingPlayer(), "Last player should not be the starting player after rotation.");
	        assertTrue(Game.players.getLast().isStartingPlayer(), "First player should have moved to the last position.");
	    }
}
