package game;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import board.DiscardPile;
import board.Hand;
import card.Card;
import card.MerchantCard;
import card.PointCard;
import card.UpgradeCard;

/**
 * aka Caravan
 */
public class Player {

	private final boolean startingPlayer;
	
	private Game game;
	private Hand hand;
	private DiscardPile discard;
	private SpiceInventory caravan = new SpiceInventory();
	private int goldCoinCount = 0;
	private int silverCoinCount = 0;
	private Optional<Card> selectedCard = Optional.empty(); //TODO - probably want a PlayerTurn object
	private int selectedNumberOfTrades = 1; 
	private List<SpiceUpgrade> selectedUpgrades = new ArrayList<>();
	
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
	
	public Optional<Card> getSelectedCard() {
		return selectedCard;
	}

	public void setSelectedCard(Optional<Card> selectedCard) {
		this.selectedCard = selectedCard;
	}

	public int getSelectedNumberOfTrades() {
		return selectedNumberOfTrades;
	}

	public void setSelectedNumberOfTrades(int selectedNumberOfTrades) {
		this.selectedNumberOfTrades = selectedNumberOfTrades;
	}

	public List<SpiceUpgrade> getSelectedUpgrades() {
		return selectedUpgrades;
	}

	public void setSelectedUpgrades(List<SpiceUpgrade> selectedUpgrades) {
		this.selectedUpgrades = selectedUpgrades;
	}

	public boolean canAfford(Card card) {
		SpiceInventory cost = card.getCost(this);
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

	public void play(MerchantCard card) {
		card.play(this);
		getHand().remove(card);
		getDiscard().add(card);
	}
	
	/*
	 * If an Upgrade card is not selected, then we would not expect to have any selected upgrades to fail this check.
	 * I do not see a need to explicitly check that it is empty though.
	 */
	public boolean selectedUpgradesAreSensible() {
		 return getSelectedUpgrades().stream().noneMatch(spiceUpgrade ->
	        spiceUpgrade.getNumberOfTimesToUpgrade() > SpiceUpgrade.getMaxUpgrades().getOrDefault(spiceUpgrade.getCubeToBeUpgraded(), 0));
	}

	public boolean selectedMoreUpgradesThanPermitted(Card card) {
		if (!(card instanceof UpgradeCard upgradeCard))
		{
			//only applicable to upgrade cards
			return true;
		}
		
		return SpiceUpgrade.getTotalUpgrades(getSelectedUpgrades()) <= upgradeCard.getPermittedUpgrades();
	}
}
