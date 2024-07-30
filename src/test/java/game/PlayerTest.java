package game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PlayerTest {

	Game game =	new Game();
	
    @BeforeEach
    public void setUp() {
    	game = new Game();
    }

    @Test
    public void testNumberOfPlayers() {
    	assertEquals(game.getNumberOfPlayers(), game.getPlayers().size(), "Incorrect number of players.");
    }

    @Test
    public void testFirstPlayerIsStartingPlayer() {
    	assertTrue(game.getPlayers().get(0).isStartingPlayer(), "First player is not the starting player.");
    }

    @Test
    public void testOtherPlayersAreNotStartingPlayers() {
        for (int i = 1; i < game.getPlayers().size(); i++) {
        	assertFalse(game.getPlayers().get(i).isStartingPlayer(), "Non-first player is the starting player.");
        }
    }

    @Test
    public void testNextPlayer() {
        Player.nextPlayer(game);

        // Check if the first player has been moved to the end
        assertFalse(game.getPlayers().getFirst().isStartingPlayer(), "Last player should not be the starting player after rotation.");
        assertTrue(game.getPlayers().getLast().isStartingPlayer(), "First player should have moved to the last position.");
    }
    
    @Test
    public void testGainingCoins() {
    	Player currentPlayer = game.getCurrentPlayer();
		assertEquals(currentPlayer.getGoldCoinCount(), 0);
    	assertEquals(currentPlayer.getSilverCoinCount(), 0);
    	
    	currentPlayer.gainGoldCoin();
		assertEquals(currentPlayer.getGoldCoinCount(), 1);
    	assertEquals(currentPlayer.getSilverCoinCount(), 0);
    	
    	currentPlayer.gainSilverCoin();
		assertEquals(currentPlayer.getGoldCoinCount(), 1);
    	assertEquals(currentPlayer.getSilverCoinCount(), 1);
    	
    	currentPlayer.gainGoldCoin();
    	currentPlayer.gainSilverCoin();
		assertEquals(currentPlayer.getGoldCoinCount(), 2);
    	assertEquals(currentPlayer.getSilverCoinCount(), 2);
    }
}
