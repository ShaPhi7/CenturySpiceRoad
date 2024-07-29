package game;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import board.MerchantCardDeckRow;
import board.PointCardDeckRow;

public class Game {

	
	public final static int NUMBER_OF_VISIBLE_MERCHANT_CARDS = 6;
	public final static int NUMBER_OF_VISIBLE_POINT_CARDS = 5;
	public final static int POINT_CARDS_GOAL_FOR_ONLY_TWO_PLAYERS = 6;
	public final static int POINT_CARDS_GOAL_FOR_OVER_TWO_PLAYERS = 5;
	
	private int numberOfPlayers = 4;
	private final String uuid = UUID.randomUUID().toString();
	private List<Player> players = new LinkedList<Player>();
	private MerchantCardDeckRow merchantCardDeckRow = new MerchantCardDeckRow(this);
	private PointCardDeckRow pointCardDeckRow = new PointCardDeckRow(this);
	
	public Player currentPlayer;
	
	public void init() {

		try {
			Player.setupPlayers(this);
			
			populateDecks();
			shuffleDecks();
			
/*			while(!shouldEndGame())
			{
				setCurrentPlayer(players.getFirst());
				getCurrentPlayer().getDiscard().execute(); //TODO - add 3 others and make generic
				Player.nextPlayer(this);
			}*/
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void populateDecks() throws IOException {
		merchantCardDeckRow.populateFromCsv("merchant-card-deck.csv");
		pointCardDeckRow.populateFromCsv("point-card-deck.csv");
		
	}
	
	private void shuffleDecks() {
		merchantCardDeckRow.shuffle();
		pointCardDeckRow.shuffle();
	}

	public boolean shouldEndGame() {
		
		if (players.stream().anyMatch(p -> p.getPointCards().size() >= getTargetNumberOfPointCards())
		  && currentPlayer.isStartingPlayer()) {
			return true;
		}
		
		return false;
	}

	public int getTargetNumberOfPointCards()
	{
		if (getNumberOfPlayers() == 2)
		{
			return POINT_CARDS_GOAL_FOR_ONLY_TWO_PLAYERS;
		}
		
		return POINT_CARDS_GOAL_FOR_OVER_TWO_PLAYERS;
	}
	
	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	public void setCurrentPlayer(Player currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

	public String getUuid() {
		return uuid;
	}
	
	public int getNumberOfPlayers() {
		return numberOfPlayers;
	}

	public void setNumberOfPlayers(int numberOfPlayers) {
		this.numberOfPlayers = numberOfPlayers;
	}

	public List<Player> getPlayers() {
		return players;
	}

	public void setPlayers(List<Player> players) {
		this.players = players;
	}

	public MerchantCardDeckRow getMerchantCardDeckRow() {
		return merchantCardDeckRow;
	}

	public void setMerchantCardDeckRow(MerchantCardDeckRow merchantCardDeckRow) {
		this.merchantCardDeckRow = merchantCardDeckRow;
	}

	public PointCardDeckRow getPointCardDeckRow() {
		return pointCardDeckRow;
	}

	public void setPointCardDeckRow(PointCardDeckRow pointCardDeckRow) {
		this.pointCardDeckRow = pointCardDeckRow;
	}	
}
