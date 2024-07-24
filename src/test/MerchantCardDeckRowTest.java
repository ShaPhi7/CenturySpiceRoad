package test;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

import game.Game;
import game.MerchantCardDeckRow;
import game.Player;
import game.SpiceCard;
import game.TradeCard;
import game.UpgradeCard;

public class MerchantCardDeckRowTest {

	Player player = new Player(true);
	MerchantCardDeckRow merchantCardDeckRow = new MerchantCardDeckRow();
	SpiceCard spiceCard = new SpiceCard();
	UpgradeCard upgradeCard = new UpgradeCard();
	TradeCard tradeCard = new TradeCard();
	
    @Before
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
    public void testValidatePlayerWithNonPointCard() {
    	player.setSelectedCard(Optional.of(new TradeCard()));
    	assertFalse(merchantCardDeckRow.validateAction(player));
    }
    
    @Test
    public void testValidatePlayerWithPointCardNotInDeck() {
    	player.setSelectedCard(Optional.of(new TradeCard()));
    	assertFalse(merchantCardDeckRow.validateAction(player));
    }
    
    @Test
    public void testValidatePlayerWithPointCardNotInVisibleCards() {
    	
    	for (int i=0;i<10;i++)
    	{
    		merchantCardDeckRow.getDeck().add(0, new TradeCard());
    	}
    	
    	assertFalse(merchantCardDeckRow.validateAction(player));
    }
    
    /*@Test
    public void testValidatePlayerCanNotAfford() {
    	
    	SpiceInventory spiceInventory = new SpiceInventory();
		spiceInventory.addSpices(Spice.YELLOW_TUMERIC, 1);
    	merchantCard.setCost(spiceInventory);
    	assertFalse(merchantCardDeckRow.validateAction(player));
    }*/
    
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
    
}
