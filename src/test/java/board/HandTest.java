package board;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import action.CliOutputHandler;
import action.GameOutputHandler;
import card.Card;
import card.PointCard;
import card.SpiceCard;
import card.TradeCard;
import card.UpgradeCard;
import game.Game;
import game.Player;
import game.Spice;
import game.SpiceInventory;
import game.SpiceUpgrade;
import view.CliInputHandler;
import view.GameInputHandler;

public class HandTest {

	Game game = new Game();
	Player player = new Player(game, true);
	GameInputHandler input = new CliInputHandler();
	GameOutputHandler output = new CliOutputHandler();
	TradeCard card = new TradeCard();
	
	@BeforeEach
	public void setUp() {
		game = new Game();
		player = new Player(game, true);
		input = new CliInputHandler();
		output = new CliOutputHandler();
		game.setCurrentPlayer(player);
		player.addToHand(card);
		input.setPlayer(player);
		input.setSelectedCard(Optional.of(card));
		input.setSelectedNumberOfTrades(1);
	}
	
    @Test
    public void testValidateActionNotCurrentPlayer() {
        game.setCurrentPlayer(new Player(game, false));
    	assertFalse(player.getHand().validateAction(input, output));
    }
    
    @Test
    public void testValidateActionWithNoCardSelected() {
    	input.setSelectedCard(Optional.empty());
        assertFalse(player.getHand().validateAction(input, output));
    }
    
    @Test
    public void testValidateActionWithNoCardInHand() {
    	player.getHand().getDeck().clear();
        assertFalse(player.getHand().validateAction(input, output));
    }
    
    @Test
    public void testValidateActionWithCardNotInHand() {
    	player.getHand().getDeck().clear();
    	player.addToHand(new TradeCard(new SpiceInventory(1,0,0,0),new SpiceInventory(0,0,0,2)));
        assertFalse(player.getHand().validateAction(input, output));
    }
    
    @Test
    public void testValidateActionWithNonMerchantCard() {
    	PointCard pointCard = new PointCard();
		player.addToHand(pointCard);
    	input.setSelectedCard(Optional.of(pointCard));
        assertFalse(player.getHand().validateAction(input, output));
    }
    
    @Test
    public void testValidateActionWithOtherPlayersHand() {
    	Player otherPlayer = new Player(game, false);
    	otherPlayer.addToHand(card);
        assertFalse(otherPlayer.getHand().validateAction(input, output));
    }
    
    @Test
    public void testValidateActionCanNotAffordTradeCard() {
    	SpiceInventory from = new SpiceInventory();
    	from.addSpices(Spice.YELLOW_TUMERIC, 1);
    	from.addSpices(Spice.RED_SAFFRON, 1);
    	SpiceInventory to = new SpiceInventory();
    	from.addSpices(Spice.BROWN_CINNAMON, 1);
		TradeCard tradeCard = new TradeCard(from, to);
		
		player.addToHand(tradeCard);
		input.setSelectedCard(Optional.of(tradeCard));
		
		assertFalse(player.getHand().validateAction(input, output));
    }
    
    @Test
    public void testValidateActionCanNotAffordUpgradeCardWrongColour() {	
		SpiceInventory caravan = new SpiceInventory();
		caravan.addSpices(Spice.YELLOW_TUMERIC, 1);
		caravan.addSpices(Spice.RED_SAFFRON, 1);
    	
    	List<SpiceUpgrade> sul = new ArrayList<>();
    	sul.add(new SpiceUpgrade(Spice.YELLOW_TUMERIC, 2));
    	sul.add(new SpiceUpgrade(Spice.GREEN_CARDAMOM, 1));
    	
		player.setCaravan(caravan);
		input.setSelectedUpgrades(sul);
		
		UpgradeCard upgradeCard = new UpgradeCard(4);
		player.addToHand(upgradeCard);
		input.setSelectedCard(Optional.of(upgradeCard));
		
		assertFalse(player.getHand().validateAction(input, output));
    }
    
    @Test
    public void testValidateActionCanNotAffordUpgradeCardNotEnoughColour() {	
		SpiceInventory caravan = new SpiceInventory();
		caravan.addSpices(Spice.YELLOW_TUMERIC, 1);
		caravan.addSpices(Spice.RED_SAFFRON, 1);
    	
    	List<SpiceUpgrade> sul = new ArrayList<>();
    	sul.add(new SpiceUpgrade(Spice.YELLOW_TUMERIC, 2));
    	sul.add(new SpiceUpgrade(Spice.YELLOW_TUMERIC, 1));
    	sul.add(new SpiceUpgrade(Spice.RED_SAFFRON, 1));
    	
		player.setCaravan(caravan);
		input.setSelectedUpgrades(sul);
		
		UpgradeCard upgradeCard = new UpgradeCard(4);
		player.addToHand(upgradeCard);
		input.setSelectedCard(Optional.of(upgradeCard));
		
		assertFalse(player.getHand().validateAction(input, output));
    }
    
    @Test
    public void testValidateActionCanNotAffordUpgradeCardNotEnoughUpgradesDifferentColour() {	
		SpiceInventory caravan = new SpiceInventory();
		caravan.addSpices(Spice.YELLOW_TUMERIC, 1);
		caravan.addSpices(Spice.RED_SAFFRON, 1);
		caravan.addSpices(Spice.GREEN_CARDAMOM, 1);
    	
    	List<SpiceUpgrade> sul = new ArrayList<>();
    	sul.add(new SpiceUpgrade(Spice.YELLOW_TUMERIC, 1));
    	sul.add(new SpiceUpgrade(Spice.RED_SAFFRON, 1));
    	sul.add(new SpiceUpgrade(Spice.GREEN_CARDAMOM, 1));
    	
		player.setCaravan(caravan);
		input.setSelectedUpgrades(sul);
		
		UpgradeCard upgradeCard = new UpgradeCard(2);
		player.addToHand(upgradeCard);
		input.setSelectedCard(Optional.of(upgradeCard));
		
		assertFalse(player.getHand().validateAction(input, output));
    }
    
    @Test
    public void testValidateActionCanNotAffordUpgradeCardNotEnoughUpgradesSameColour() {	
		SpiceInventory caravan = new SpiceInventory();
		caravan.addSpices(Spice.YELLOW_TUMERIC, 3);
    	
    	List<SpiceUpgrade> sul = new ArrayList<>();
    	sul.add(new SpiceUpgrade(Spice.YELLOW_TUMERIC, 3));
    	
		player.setCaravan(caravan);
		input.setSelectedUpgrades(sul);
		
		UpgradeCard upgradeCard = new UpgradeCard(2);
		player.addToHand(upgradeCard);
		input.setSelectedCard(Optional.of(upgradeCard));
		
		assertFalse(player.getHand().validateAction(input, output));
    }
    
    @Test
    public void testValidateActionCanNotUpgradeBrown() {	
		SpiceInventory caravan = new SpiceInventory();
		caravan.addSpices(Spice.YELLOW_TUMERIC, 1);
		caravan.addSpices(Spice.BROWN_CINNAMON, 1);
    	
    	List<SpiceUpgrade> sul = new ArrayList<>();
    	sul.add(new SpiceUpgrade(Spice.BROWN_CINNAMON, 1));
    	
		player.setCaravan(caravan);
		input.setSelectedUpgrades(sul);
		
		UpgradeCard upgradeCard = new UpgradeCard(4);
		player.addToHand(upgradeCard);
		input.setSelectedCard(Optional.of(upgradeCard));
		
		assertFalse(player.getHand().validateAction(input, output));
    }
    
    @Test
    public void testValidateActionCanNotUpgradeGreenTwice() {	
		SpiceInventory caravan = new SpiceInventory();
		caravan.addSpices(Spice.GREEN_CARDAMOM, 1);
    	
    	List<SpiceUpgrade> sul = new ArrayList<>();
    	sul.add(new SpiceUpgrade(Spice.GREEN_CARDAMOM, 2));
    	
		player.setCaravan(caravan);
		input.setSelectedUpgrades(sul);
		
		UpgradeCard upgradeCard = new UpgradeCard(4);
		player.addToHand(upgradeCard);
		input.setSelectedCard(Optional.of(upgradeCard));
		
		assertFalse(player.getHand().validateAction(input, output));
    }
    
    @Test
    public void testValidateActionCanNotUpgradeRedThrice() {	
		SpiceInventory caravan = new SpiceInventory();
		caravan.addSpices(Spice.RED_SAFFRON, 1);
    	
    	List<SpiceUpgrade> sul = new ArrayList<>();
    	sul.add(new SpiceUpgrade(Spice.RED_SAFFRON, 3));
    	
		player.setCaravan(caravan);
		input.setSelectedUpgrades(sul);
		
		UpgradeCard upgradeCard = new UpgradeCard(4);
		player.addToHand(upgradeCard);
		input.setSelectedCard(Optional.of(upgradeCard));
		
		assertFalse(player.getHand().validateAction(input, output));
    }
    
    @Test
    public void testValidateActionCanNotUpgradeYellow4TimesSanity() {	
		SpiceInventory caravan = new SpiceInventory();
		caravan.addSpices(Spice.YELLOW_TUMERIC, 1);
    	
    	List<SpiceUpgrade> sul = new ArrayList<>();
    	sul.add(new SpiceUpgrade(Spice.YELLOW_TUMERIC, 4));
    	
		player.setCaravan(caravan);
		input.setSelectedUpgrades(sul);
		
		UpgradeCard upgradeCard = new UpgradeCard(4);
		player.addToHand(upgradeCard);
		input.setSelectedCard(Optional.of(upgradeCard));
		
		assertFalse(player.getHand().validateAction(input, output));
    }
    
    @Test
    public void testDoActionWithSpiceCard() {
    	
    	SpiceInventory spiceInventory = new SpiceInventory();
    	spiceInventory.addSpices(Spice.YELLOW_TUMERIC, 1);
    	spiceInventory.addSpices(Spice.RED_SAFFRON, 2);
    	spiceInventory.addSpices(Spice.GREEN_CARDAMOM, 3);
    	spiceInventory.addSpices(Spice.BROWN_CINNAMON, 4);
		SpiceCard spiceCard = new SpiceCard(spiceInventory);
		Optional<Card> spiceCardOptional = Optional.of(spiceCard);
    		
		player.addToHand(spiceCard);
		input.setSelectedCard(spiceCardOptional);
		
    	Hand hand = player.getHand();
		assertTrue(hand.validateAction(input, output));
    	hand.doAction(input, output);
    	
    	assertEquals(1, player.getSpiceCount(Spice.YELLOW_TUMERIC));
    	assertEquals(2, player.getSpiceCount(Spice.RED_SAFFRON));
    	assertEquals(3, player.getSpiceCount(Spice.GREEN_CARDAMOM));
    	assertEquals(4, player.getSpiceCount(Spice.BROWN_CINNAMON));
    	
    	assertFalse(player.getHand().contains(spiceCard));
    	assertTrue(player.getDiscard().contains(spiceCard));
    }
    
    @Test
    public void testDoActionWithTradeCard() {
    	SpiceInventory from = new SpiceInventory();
    	from.addSpices(Spice.YELLOW_TUMERIC, 1);
    	from.addSpices(Spice.RED_SAFFRON, 1);
    	SpiceInventory to = new SpiceInventory();
    	to.addSpices(Spice.BROWN_CINNAMON, 1);
		TradeCard tradeCard = new TradeCard(from, to);
		
		player.addToHand(tradeCard);
		input.setSelectedCard(Optional.of(tradeCard));
		input.setSelectedNumberOfTrades(4);
		
		player.gainSpices(Spice.YELLOW_TUMERIC, 5);
		player.gainSpices(Spice.RED_SAFFRON, 5);
		
		assertTrue(player.getHand().validateAction(input, output));
		player.getHand().doAction(input, output);
		
    	assertEquals(1, player.getSpiceCount(Spice.YELLOW_TUMERIC));
    	assertEquals(1, player.getSpiceCount(Spice.RED_SAFFRON));
    	assertEquals(0, player.getSpiceCount(Spice.GREEN_CARDAMOM));
    	assertEquals(4, player.getSpiceCount(Spice.BROWN_CINNAMON));
		
    	assertFalse(player.getHand().contains(tradeCard));
    	assertTrue(player.getDiscard().contains(tradeCard));
    }
    
    @Test
    public void testDoActionWithUpgradeCard() {
		SpiceInventory caravan = new SpiceInventory();
		caravan.addSpices(Spice.YELLOW_TUMERIC, 1);
		caravan.addSpices(Spice.RED_SAFFRON, 1);
		caravan.addSpices(Spice.GREEN_CARDAMOM, 1);
		caravan.addSpices(Spice.BROWN_CINNAMON, 4);
    	
    	List<SpiceUpgrade> sul = new ArrayList<>();
    	sul.add(new SpiceUpgrade(Spice.YELLOW_TUMERIC, 2));
    	sul.add(new SpiceUpgrade(Spice.RED_SAFFRON, 1));
    	sul.add(new SpiceUpgrade(Spice.GREEN_CARDAMOM, 1));
    	
		player.setCaravan(caravan);
		input.setSelectedUpgrades(sul);
		
		UpgradeCard upgradeCard = new UpgradeCard(4);
		player.addToHand(upgradeCard);
		input.setSelectedCard(Optional.of(upgradeCard));
		
		assertTrue(player.getHand().validateAction(input, output));
		player.getHand().doAction(input, output);
		
    	assertEquals(0, player.getSpiceCount(Spice.YELLOW_TUMERIC));
    	assertEquals(0, player.getSpiceCount(Spice.RED_SAFFRON));
    	assertEquals(2, player.getSpiceCount(Spice.GREEN_CARDAMOM));
    	assertEquals(5, player.getSpiceCount(Spice.BROWN_CINNAMON));
    }
    
    @Test
    public void testDoActionWithUpgradeCardSameColourTwice() {
		SpiceInventory caravan = new SpiceInventory();
		caravan.addSpices(Spice.YELLOW_TUMERIC, 2);
    	
    	List<SpiceUpgrade> sul = new ArrayList<>();
    	sul.add(new SpiceUpgrade(Spice.YELLOW_TUMERIC, 2));
    	sul.add(new SpiceUpgrade(Spice.YELLOW_TUMERIC, 1));
    	
		player.setCaravan(caravan);
		input.setSelectedUpgrades(sul);
		
		UpgradeCard upgradeCard = new UpgradeCard(3);
		player.addToHand(upgradeCard);
		input.setSelectedCard(Optional.of(upgradeCard));
		
		assertTrue(player.getHand().validateAction(input, output));
		player.getHand().doAction(input, output);
		
    	assertEquals(0, player.getSpiceCount(Spice.YELLOW_TUMERIC));
    	assertEquals(1, player.getSpiceCount(Spice.RED_SAFFRON));
    	assertEquals(1, player.getSpiceCount(Spice.GREEN_CARDAMOM));
    	assertEquals(0, player.getSpiceCount(Spice.BROWN_CINNAMON));
    }
    
    @Test
    public void testDoActionWithUpgradeCardSameColourThrice() {
		SpiceInventory caravan = new SpiceInventory();
		caravan.addSpices(Spice.YELLOW_TUMERIC, 1);
    	
    	List<SpiceUpgrade> sul = new ArrayList<>();
    	sul.add(new SpiceUpgrade(Spice.YELLOW_TUMERIC, 3));
    	
		player.setCaravan(caravan);
		input.setSelectedUpgrades(sul);
		
		UpgradeCard upgradeCard = new UpgradeCard(3);
		player.addToHand(upgradeCard);
		input.setSelectedCard(Optional.of(upgradeCard));
		
		assertTrue(player.getHand().validateAction(input, output));
		player.getHand().doAction(input, output);
		
    	assertEquals(0, player.getSpiceCount(Spice.YELLOW_TUMERIC));
    	assertEquals(0, player.getSpiceCount(Spice.RED_SAFFRON));
    	assertEquals(0, player.getSpiceCount(Spice.GREEN_CARDAMOM));
    	assertEquals(1, player.getSpiceCount(Spice.BROWN_CINNAMON));
    }
}
