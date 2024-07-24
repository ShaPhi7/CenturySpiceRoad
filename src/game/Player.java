package game;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * aka Caravan
 */
public class Player {

	private final boolean startingPlayer;
	
	private DeckRow hand = new Hand(Optional.empty());
	private DeckRow discard = new DiscardPile(Optional.empty());
	private SpiceInventory caravan = new SpiceInventory();
	private int goldCoinCount = 0;
	private int silverCoinCount = 0;
	private Optional<Card> selectedCard = Optional.empty(); 

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

	public DeckRow getHand() {
		return hand;
	}

	public void setHand(DeckRow hand) {
		this.hand = hand;
	}

	public DeckRow getDiscard() {
		return discard;
	}

	public void setDiscard(DeckRow discard) {
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
	
	public SpiceInventory getCaravan() {
		return caravan;
	}

	public void setCaravan(SpiceInventory caravan) {
		this.caravan = caravan;
	}

	public Spice selectCubeFromCaravan() {
		caravan.getCubes();
		return Spice.YELLOW_TUMERIC; //TODO - fill in
	}
	
	public Optional<Card> getSelectedCard() {
		return selectedCard;
	}

	public void setSelectedCard(Optional<Card> selectedCard) {
		this.selectedCard = selectedCard;
	}

	public boolean canAfford(PointCard card) {
		SpiceInventory cost = card.getCost();
		return caravan.canAfford(cost);
	}

	public List<PointCard> getPointCards() {
		return hand.getDeck().stream()
                .filter(card -> card instanceof PointCard)
                .map(card -> (PointCard) card)
                .collect(Collectors.toList());
	}

}
