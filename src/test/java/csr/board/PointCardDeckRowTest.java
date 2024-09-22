package csr.board;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import csr.action.Action;
import csr.action.CliOutputHandler;
import csr.card.Card;
import csr.card.PointCard;
import csr.card.TradeCard;
import csr.cli.CliInputHandler;
import csr.game.Game;
import csr.game.Player;
import csr.game.Spice;
import csr.game.SpiceInventory;
import csr.game.State;
import csr.view.GameInputHandler;

public class PointCardDeckRowTest {

	Game game = new Game();
	Player player = new Player(game, true);
	GameInputHandler input = new CliInputHandler();
	CliOutputHandler output = new CliOutputHandler();
	PointCardDeckRow pointCardDeckRow = new PointCardDeckRow(game);
	PointCard pointCard = new PointCard();
	
    @BeforeEach
    public void setUp() {
    	game = new Game();
    	player = new Player(game, true);
    	input = new CliInputHandler();
    	output = new CliOutputHandler();
    	pointCardDeckRow = new PointCardDeckRow(game);
    	pointCard = new PointCard();
        
        //Player setup
    	game.setCurrentState(State.NEW_TURN);
    	game.setCurrentPlayer(player);
        input.setPlayer(player);
        input.setSelectedCard(Optional.of(pointCard));
        input.setAction(Action.CLAIM);
        
        //Deck setup
        pointCardDeckRow.getDeck().add(pointCard);
        for (int i=0;i<50;i++)
        {
        	pointCardDeckRow.getDeck().add(new PointCard());
        }
    }

    @Test
    public void testValidatePlayerNotCurrentPlayer() {
        game.setCurrentPlayer(new Player(game, false));
    	assertFalse(pointCardDeckRow.validateAction(input, output));
    }
    
    @Test
    public void testValidatePlayerWithNoCard() {
    	input.setSelectedCard(Optional.empty());
        assertFalse(pointCardDeckRow.validateAction(input, output));
    }

    @Test
    public void testValidatePlayerWithNonPointCard() {
    	input.setSelectedCard(Optional.of(new TradeCard()));
    	assertFalse(pointCardDeckRow.validateAction(input, output));
    }
    
    @Test
    public void testValidatePlayerWithPointCardNotInDeck() {
    	input.setSelectedCard(Optional.of(new PointCard(1,1,1,1,10)));
    	assertFalse(pointCardDeckRow.validateAction(input, output));
    }
    
    @Test
    public void testValidatePlayerWithPointCardNotInVisibleCards() {
    	
    	for (int i=0;i<10;i++)
    	{
    		pointCardDeckRow.getDeck().add(0, new PointCard(1,1,1,1,10));
    	}
    	
    	assertFalse(pointCardDeckRow.validateAction(input, output));
    }
    
    @Test
    public void testValidatePlayerCanNotAfford() {
    	
    	SpiceInventory spiceInventory = new SpiceInventory();
		spiceInventory.addSpices(Spice.YELLOW_TUMERIC, 1);
    	pointCard.setCost(spiceInventory);
    	assertFalse(pointCardDeckRow.validateAction(input, output));
    }
    
    @Test
    public void testValidatePlayerTooManyPointCards() {
    	
    	List<Card> pointCards = new ArrayList<>(); 
    	for (int i=0;i<6;i++)
    	{
    		pointCards.add(new PointCard());
    	}
    	Hand hand = new Hand(game, pointCards);
    	player.setHand(hand);
    	
    	assertFalse(pointCardDeckRow.validateAction(input, output));
    }
    
    @Test
    public void testValidatePlayerAllowedToAcquirePointCard() {
    	game.setNumberOfPlayers(3);
    	List<Card> pointCards = new ArrayList<>(); 
    	for (int i=0;i<4;i++)
    	{
    		pointCards.add(new PointCard());
    	}
    	Hand hand = new Hand(game, pointCards);
    	player.setHand(hand);
    	
    	assertTrue(pointCardDeckRow.validateAction(input, output));
    }
    
    @Test
    public void testValidatePlayerAllowedToAcquirePointCardTwoPlayer() {
    	game.setNumberOfPlayers(2);
    	List<Card> pointCards = new ArrayList<>(); 
    	for (int i=0;i<5;i++)
    	{
    		pointCards.add(new PointCard());
    	}
    	Hand hand = new Hand(game, pointCards);
    	player.setHand(hand);
    	
    	assertTrue(pointCardDeckRow.validateAction(input, output));
    }
    
    @Test
    public void testDoActionCardCost() {
    	
    	SpiceInventory pointCardCost = new SpiceInventory();
    	pointCardCost.addSpices(Spice.YELLOW_TUMERIC, 2);
    	pointCardCost.addSpices(Spice.RED_SAFFRON, 3);
    	pointCardCost.addSpices(Spice.GREEN_CARDAMOM, 1);
    	pointCardCost.addSpices(Spice.BROWN_CINNAMON, 0);
    	
		pointCard.setCost(pointCardCost);
    	
		player.gainSpices(Spice.YELLOW_TUMERIC, 4);
		player.gainSpices(Spice.RED_SAFFRON, 4);
		player.gainSpices(Spice.GREEN_CARDAMOM, 1);
		player.gainSpices(Spice.BROWN_CINNAMON, 1);
    	
    	assertTrue(pointCardDeckRow.validateAction(input, output));
    	pointCardDeckRow.doAction(input, output);
    	
    	assertEquals(2, player.getSpiceCount(Spice.YELLOW_TUMERIC));
    	assertEquals(1, player.getSpiceCount(Spice.RED_SAFFRON));
    	assertEquals(0, player.getSpiceCount(Spice.GREEN_CARDAMOM));
    	assertEquals(1, player.getSpiceCount(Spice.BROWN_CINNAMON));
    }
    
    @Test
    public void testDoActionGainGoldCoinFirstIndex() {
    	
    	pointCardDeckRow.setGoldCoins(2);
    	pointCardDeckRow.setSilverCoins(2);
    	
    	assertTrue(pointCardDeckRow.validateAction(input, output));
    	pointCardDeckRow.doAction(input, output);
    	
    	assertEquals(1, player.getGoldCoinCount());
    	assertEquals(0, player.getSilverCoinCount());

    	assertEquals(1, pointCardDeckRow.getGoldCoins());
    	assertEquals(2, pointCardDeckRow.getSilverCoins());
    }
    
    @Test
    public void testDoActionGainSilverCoinFirstIndex() {
    	
    	pointCardDeckRow.setGoldCoins(0);
    	pointCardDeckRow.setSilverCoins(2);
    	
    	assertTrue(pointCardDeckRow.validateAction(input, output));
    	pointCardDeckRow.doAction(input, output);
    	
    	assertEquals(0, player.getGoldCoinCount());
    	assertEquals(1, player.getSilverCoinCount());

    	assertEquals(0, pointCardDeckRow.getGoldCoins());
    	assertEquals(1, pointCardDeckRow.getSilverCoins());
    }
    
    @Test
    public void testDoActionGainSilverCoinSecondIndex() {
    	
    	pointCardDeckRow.setGoldCoins(2);
    	pointCardDeckRow.setSilverCoins(2);
    	
    	pointCardDeckRow.getDeck().add(0, new PointCard(1,1,1,1,10));
    	
    	assertTrue(pointCardDeckRow.validateAction(input, output));
    	pointCardDeckRow.doAction(input, output);
    	
    	assertEquals(0, player.getGoldCoinCount());
    	assertEquals(1, player.getSilverCoinCount());

    	assertEquals(2, pointCardDeckRow.getGoldCoins());
    	assertEquals(1, pointCardDeckRow.getSilverCoins());
    }
    
    @Test
    public void testDoActionGainNoCoinSecondIndex() {
    	
    	pointCardDeckRow.setGoldCoins(2);
    	pointCardDeckRow.setSilverCoins(0);
    	
    	pointCardDeckRow.getDeck().add(0, new PointCard(1,1,1,1,10));
    	
    	assertTrue(pointCardDeckRow.validateAction(input, output));
    	pointCardDeckRow.doAction(input, output);
    	
    	assertEquals(0, player.getGoldCoinCount());
    	assertEquals(0, player.getGoldCoinCount());
    	assertEquals(2, pointCardDeckRow.getGoldCoins());
    	assertEquals(0, pointCardDeckRow.getSilverCoins());
    }
    
    @Test
    public void testDoActionGainNoCoinSecondIndexNoCoinsAtAll() {
    	
    	pointCardDeckRow.setGoldCoins(0);
    	pointCardDeckRow.setSilverCoins(0);
    	
    	pointCardDeckRow.getDeck().add(0, new PointCard());
    	
    	assertTrue(pointCardDeckRow.validateAction(input, output));
    	pointCardDeckRow.doAction(input, output);
    	
    	assertEquals(0, player.getGoldCoinCount());
    	assertEquals(0, player.getGoldCoinCount());
    	assertEquals(0, pointCardDeckRow.getGoldCoins());
    	assertEquals(0, pointCardDeckRow.getSilverCoins());
    }
    
    @Test
    public void testDoActionGainNoCoinThirdIndex() {
    	
    	pointCardDeckRow.getDeck().add(0, new PointCard());
    	pointCardDeckRow.getDeck().add(0, new PointCard());
    	
    	pointCardDeckRow.setGoldCoins(0);
    	pointCardDeckRow.setSilverCoins(0);
    	
    	assertTrue(pointCardDeckRow.validateAction(input, output));
    	pointCardDeckRow.doAction(input, output);
    	
    	assertEquals(0, player.getGoldCoinCount());
    	assertEquals(0, player.getGoldCoinCount());
    	assertEquals(0, pointCardDeckRow.getGoldCoins());
    	assertEquals(0, pointCardDeckRow.getSilverCoins());
    }
    
    @Test
    public void testGetActionName() {
    	
    	assertEquals(Action.CLAIM, pointCardDeckRow.getAction());
    }
    
}
