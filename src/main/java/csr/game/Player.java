package csr.game;

import java.util.List;
import java.util.stream.Collectors;

import csr.board.DiscardPile;
import csr.board.Hand;
import csr.card.Card;
import csr.card.MerchantCard;
import csr.card.PointCard;
import csr.view.GameInputHandler;

/**
 * aka Caravan
 */
public class Player {

	private final boolean startingPlayer;
	
	private final Game game;
	private Hand hand;
	private DiscardPile discard;
	private SpiceInventory caravan = new SpiceInventory();
	private int goldCoinCount = 0;
	private int silverCoinCount = 0;
	
	public Player(Game game, boolean startingPlayer) {
		this.game = game;
		this.hand = new Hand(game);
		this.discard = new DiscardPile(game);
		this.startingPlayer = startingPlayer;
		System.out.println("Player entered the game");
	}

	public boolean isStartingPlayer() {
		return startingPlayer;
	}
	
	public static void setupPlayers(Game game) {
		List<Player> players = game.getPlayers();
		players.add(new Player(game, true));
		
		for (int i=1;i<game.getNumberOfPlayers();i++)
		{
			players.add(new Player(game, false));
		}
		game.setCurrentPlayer(players.getFirst());
	}

	public static void nextPlayer(Game game) {
		List<Player> players = game.getPlayers();
		Player player = players.removeFirst();
		players.addLast(player);
		game.setCurrentPlayer(players.getFirst());
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

	public void gainSpice(Spice spice)
	{
		gainSpices(spice, 1);
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

	public boolean canAfford(Card card, GameInputHandler input) {
		SpiceInventory cost = card.getCost(input);
		return caravan.canAfford(cost);
	}
	
	/*
	 * must pay 1 cube per card below it
	 */
	public boolean canAffordToAcquire(MerchantCard card) {
		int cost = card.getCostToAcquire(game);
		return getCubeCount() >= cost;
	}
	
	public List<PointCard> getPointCards() {
		return getHand().getDeck().stream()
                .filter(card -> card instanceof PointCard)
                .map(card -> (PointCard) card)
                .collect(Collectors.toList());
	}

	public void play(MerchantCard card, GameInputHandler input) {
		card.play(input);
		getHand().remove(card);
		getDiscard().add(card);
	}
}
