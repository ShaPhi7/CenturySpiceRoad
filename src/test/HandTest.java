package test;

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

import game.Game;
import game.Player;
import game.TradeCard;

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
    public void testValidateActionWithOtherPlayersHand() {
    	Player otherPlayer = new Player(false);
    	otherPlayer.addToHand(card);
        assertFalse(otherPlayer.getHand().validateAction(player));
    }
}
