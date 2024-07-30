package board;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import action.Action;
import action.CliOutputHandler;
import card.MerchantCard;
import card.PointCard;
import card.SpiceCard;
import card.TradeCard;
import card.UpgradeCard;
import game.Game;
import game.Player;
import game.Spice;
import game.SpiceInventory;
import game.State;
import view.CliInputHandler;
import view.GameInputHandler;

public class MerchantCardDeckRowTest {

	Game game = new Game();
	Player player = new Player(game, true);
	GameInputHandler input = new CliInputHandler();
	CliOutputHandler output = new CliOutputHandler();
	MerchantCardDeckRow merchantCardDeckRow = new MerchantCardDeckRow(game);
	SpiceCard spiceCard = new SpiceCard();
	UpgradeCard upgradeCard = new UpgradeCard();
	TradeCard tradeCard = new TradeCard();
	
    @BeforeEach
    public void setUp() {
    	game = new Game();
    	player = new Player(game, true);
    	input = new CliInputHandler();
    	output = new CliOutputHandler();
    	merchantCardDeckRow = new MerchantCardDeckRow(game);
    	spiceCard = new SpiceCard();
    	upgradeCard = new UpgradeCard();
    	tradeCard = new TradeCard();
        
    	game.setCurrentState(State.NEW_TURN);
    	game.setCurrentPlayer(player);
        input.setPlayer(player);
        input.setSelectedCard(Optional.of(tradeCard));
        input.setAction(Action.ACQUIRE);
        
        //Deck setup
        game.setMerchantCardDeckRow(merchantCardDeckRow);
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
        game.setCurrentPlayer(new Player(game, false));
    	assertFalse(merchantCardDeckRow.validateAction(input, output));
    }
    
    @Test
    public void testValidatePlayerWithNoCard() {
    	input.setSelectedCard(Optional.empty());
        assertFalse(merchantCardDeckRow.validateAction(input, output));
    }

    @Test
    public void testValidatePlayerWithNonMerchantCard() {
    	input.setSelectedCard(Optional.of(new PointCard()));
    	assertFalse(merchantCardDeckRow.validateAction(input, output));
    }
    
    @Test
    public void testValidatePlayerWithPointCardNotInDeck() {
    	input.setSelectedCard(Optional.of(new TradeCard(new SpiceInventory(1,0,0,0), new SpiceInventory(0,0,0,2))));
    	assertFalse(merchantCardDeckRow.validateAction(input, output));
    }
    
    @Test
    public void testValidatePlayerWithPointCardNotInVisibleCards() {
    	
    	for (int i=0;i<10;i++)
    	{
    		merchantCardDeckRow.getDeck().add(0, new TradeCard(new SpiceInventory(1,0,0,0),new SpiceInventory(0,0,0,2)));
    	}
    	
    	assertFalse(merchantCardDeckRow.validateAction(input, output));
    }
    
    @Test
    public void testValidatePlayerCanAfford() {
    	
    	merchantCardDeckRow.getDeck().add(0, new TradeCard(new SpiceInventory(0,0,0,0),new SpiceInventory(0,0,0,1)));
    	merchantCardDeckRow.getDeck().add(0, new TradeCard(new SpiceInventory(0,0,0,0),new SpiceInventory(0,0,0,2)));
    	
    	assertEquals(2, tradeCard.getCostToAcquire(game));
    	assertFalse(merchantCardDeckRow.validateAction(input, output));
    	
    	player.gainSpices(Spice.YELLOW_TUMERIC, 1);
    	assertFalse(merchantCardDeckRow.validateAction(input, output));
    	
    	player.gainSpices(Spice.YELLOW_TUMERIC, 1);
    	assertTrue(merchantCardDeckRow.validateAction(input, output));
    	
    	player.gainSpices(Spice.YELLOW_TUMERIC, 1);
    	assertTrue(merchantCardDeckRow.validateAction(input, output));
    }
    
    @Test
    public void testValidatePlayerAllowedToAcquireTradeCard() {

    	assertTrue(merchantCardDeckRow.validateAction(input, output));
    }
    
    @Test
    public void testValidatePlayerAllowedToAcquireUpgradeCard() {

    	input.setSelectedCard(Optional.of(upgradeCard));
        merchantCardDeckRow.getDeck().add(0, upgradeCard);
    	assertTrue(merchantCardDeckRow.validateAction(input, output));
    }
    
    @Test
    public void testValidatePlayerAllowedToAcquireSpiceCard() {

    	input.setSelectedCard(Optional.of(spiceCard));
        merchantCardDeckRow.getDeck().add(0, spiceCard);
    	assertTrue(merchantCardDeckRow.validateAction(input, output));
    }
    
    @Test
    public void testValidatePlayerPlaceCubes() {

    	MerchantCard lowestCard = new TradeCard(new SpiceInventory(1,0,0,0),new SpiceInventory(0,0,0,1));
    	MerchantCard lowerCard = new TradeCard(new SpiceInventory(2,0,0,0),new SpiceInventory(0,0,0,1));
    	MerchantCard firstPurchase = new TradeCard(new SpiceInventory(3,0,0,0),new SpiceInventory(0,0,0,1));
    	MerchantCard middleCard = new TradeCard(new SpiceInventory(4,0,0,0),new SpiceInventory(0,0,0,1));    	
    	MerchantCard secondPurchase = new TradeCard(new SpiceInventory(5,0,0,0),new SpiceInventory(0,0,0,1));
    	MerchantCard higherCard = new TradeCard(new SpiceInventory(6,0,0,0),new SpiceInventory(0,0,0,1));
    	
    	merchantCardDeckRow.getDeck().add(0, lowestCard); //1
    	merchantCardDeckRow.getDeck().add(1, lowerCard); //2
    	merchantCardDeckRow.getDeck().add(2, firstPurchase); //3
    	merchantCardDeckRow.getDeck().add(3, middleCard); //4
    	merchantCardDeckRow.getDeck().add(4, secondPurchase); //5
    	merchantCardDeckRow.getDeck().add(5, higherCard); //6
    	
    	player.gainSpices(Spice.YELLOW_TUMERIC, 10);
    	
    	input.setSelectedCard(Optional.of(firstPurchase));
    	assertTrue(merchantCardDeckRow.validateAction(input, output));
    	merchantCardDeckRow.doAction(input, output);
    	
    	assertEquals(8, player.getSpiceCount(Spice.YELLOW_TUMERIC));
    	assertEquals(1, lowestCard.getTotalNumberOfCubesOnCard());
    	assertEquals(1, lowerCard.getTotalNumberOfCubesOnCard());
    	assertEquals(0, firstPurchase.getTotalNumberOfCubesOnCard());
    	assertEquals(0, middleCard.getTotalNumberOfCubesOnCard());
    	assertEquals(0, secondPurchase.getTotalNumberOfCubesOnCard());
    	assertEquals(0, higherCard.getTotalNumberOfCubesOnCard());
    	
    	input.setSelectedCard(Optional.of(secondPurchase));
    	assertTrue(merchantCardDeckRow.validateAction(input, output));
    	merchantCardDeckRow.doAction(input, output);
    	
    	assertEquals(0, firstPurchase.getTotalNumberOfCubesOnCard());
    	assertEquals(5, player.getSpiceCount(Spice.YELLOW_TUMERIC));
    	assertEquals(2, lowestCard.getTotalNumberOfCubesOnCard());
    	assertEquals(2, lowerCard.getTotalNumberOfCubesOnCard());
    	assertEquals(1, middleCard.getTotalNumberOfCubesOnCard());
    	assertEquals(0, secondPurchase.getTotalNumberOfCubesOnCard());
    	assertEquals(0, higherCard.getTotalNumberOfCubesOnCard());
    }
    
    @Test
    public void testGainCubes() {
    	
    	tradeCard.placeCubeOnCard(Spice.YELLOW_TUMERIC);
    	merchantCardDeckRow.doAction(input, output);
    	assertEquals(1, player.getSpiceCount(Spice.YELLOW_TUMERIC));
    }
    
    @Test
    public void testGetActionName() {
    	
    	assertEquals(Action.ACQUIRE, merchantCardDeckRow.getAction());
    }
}
