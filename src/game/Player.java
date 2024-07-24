package game;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import board.DiscardPile;
import board.Hand;
import card.Card;
import card.MerchantCard;
import card.PointCard;
import card.SpiceCard;
import card.TradeCard;
import card.UpgradeCard;

/**
 * aka Caravan
 */
public class Player {

	private final boolean startingPlayer;
	
	private Hand hand = new Hand();
	private DiscardPile discard = new DiscardPile();
	private SpiceInventory caravan = new SpiceInventory();
	private int goldCoinCount = 0;
	private int silverCoinCount = 0;
	private Optional<Card> selectedCard = Optional.empty(); //TODO - probably want a PlayerTurn object
	private int selectedTimes = 1; 
	
	public Player(boolean startingPlayer) {
		this.startingPlayer = startingPlayer;
		System.out.println("Player entered the game");
	}

	public boolean isStartingPlayer() {
		return startingPlayer;
	}
	
	public static void setupPlayers(List<Player> players) {
		players.add(new Player(true));
		
		for (int i=1;i<Game.NUMBER_OF_PLAYERS;i++)
		{
			players.add(new Player(false));
		}
		Game.currentPlayer = players.getFirst();
	}

	public static void nextPlayer(List<Player> players) {
		Player player = players.removeFirst();
		players.addLast(player);
		Game.currentPlayer = players.getFirst();
	}
	
	public void payCubes(SpiceInventory cost)
	{
		caravan.removeSpices(cost);
	}
	
	public void payCube(Spice spice)
	{
		caravan.removeSpices(spice, 1);
	}

	public Hand getHand() {
		return hand;
	}

	public void setHand(Hand hand) {
		this.hand = hand;
	}
	
	public void addToHand(Card card) {
		List<Card> deck = hand.getDeck();
		deck.add(card);
	}

	public DiscardPile getDiscard() {
		return discard;
	}

	public void setDiscard(DiscardPile discard) {
		this.discard = discard;
	}

	public int getGoldCoinCount() {
		return goldCoinCount;
	}

	public void gainGoldCoin() {
		this.goldCoinCount++;
	}

	public int getSilverCoinCount() {
		return silverCoinCount;
	}
	
	public void gainSilverCoin() {
		this.silverCoinCount++;
	}

	public void setGoldCoinCount(int goldCoinCount) {
		this.goldCoinCount = goldCoinCount;
	}

	public void setSilverCoinCount(int silverCoinCount) {
		this.silverCoinCount = silverCoinCount;
	}
	
	private int getCubeCount() {
		return getCaravan().getTotalCubes();
	}
	
	public SpiceInventory getCaravan() {
		return caravan;
	}

	public void setCaravan(SpiceInventory caravan) {
		this.caravan = caravan;
	}

	public void gainSpices(Spice spice, int quantity) {
		caravan.addSpices(spice, quantity);
	}
	
	public void gainSpices(SpiceInventory spiceInventory) {
		caravan.addSpices(spiceInventory);
	}
	
	public int getSpiceCount(Spice spice) {
		return caravan.getQuantity(spice);
	}
	
	public Optional<Card> getSelectedCard() {
		return selectedCard;
	}

	public void setSelectedCard(Optional<Card> selectedCard) {
		this.selectedCard = selectedCard;
	}

	public int getSelectedTimes() {
		return selectedTimes;
	}

	public void setSelectedTimes(int selectedTimes) {
		this.selectedTimes = selectedTimes;
	}

	/*
	 * must pay exact cost
	 */
	public boolean canAffordToClaim(PointCard card) {
		SpiceInventory cost = card.getCost();
		return caravan.canAfford(cost);
	}

	/*
	 * must pay 1 cube per card below it
	 */
	public boolean canAffordToAcquire(MerchantCard card) {
		
		int cost = card.getAcquireCost();
		if (getCubeCount() < cost)
		{
			return false;
		}
		return true;
	}

	/*
	 * Must have right cubes
	 */
	public boolean canAffordToPlay(MerchantCard card) {
		if (card instanceof SpiceCard)
		{
			//no cost for playing these
			return true;
		}
		else if (card instanceof TradeCard)
		{
			TradeCard tradeCard = (TradeCard) card;
			SpiceInventory from = tradeCard.getFrom();
			SpiceInventory multSpiceInventory = new SpiceInventory();
			for (int i=0; i<selectedTimes; i++)
			{
				multSpiceInventory.addSpices(from);
			}
			return caravan.canAfford(multSpiceInventory);
		}
		else if (card instanceof UpgradeCard)
		{
			return false;
		}
		System.out.println("Unknown class: " + card.getClass());
		return false;
	}
	
	public List<PointCard> getPointCards() {
		return getHand().getDeck().stream()
                .filter(card -> card instanceof PointCard)
                .map(card -> (PointCard) card)
                .collect(Collectors.toList());
	}

	public void play(MerchantCard card) {
		card.play(this);
		getHand().remove(card);
		getDiscard().add(card);
	}
}
