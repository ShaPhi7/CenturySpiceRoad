package game;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import action.Action;
import action.Actionable;
import action.GameOutputHandler;
import board.MerchantCardDeckRow;
import board.PointCardDeckRow;
import view.GameInputHandler;

public class Game implements Actionable {
	
	public final static int NUMBER_OF_VISIBLE_MERCHANT_CARDS = 6;
	public final static int NUMBER_OF_VISIBLE_POINT_CARDS = 5;
	public final static int POINT_CARDS_GOAL_FOR_ONLY_TWO_PLAYERS = 6;
	public final static int POINT_CARDS_GOAL_FOR_OVER_TWO_PLAYERS = 5;
	
	private int numberOfPlayers = 4;
	private List<Player> players = new LinkedList<Player>();
	private MerchantCardDeckRow merchantCardDeckRow = new MerchantCardDeckRow(this);
	private PointCardDeckRow pointCardDeckRow = new PointCardDeckRow(this);
	
	public Player currentPlayer;
	public State currentState;
	
	public Game() {
	}
	 
	public Game(int numberOfPlayers) {
		this.numberOfPlayers = numberOfPlayers;
	}
	
	public void init() {

		try {
			Player.setupPlayers(this);
			
			populateDecks();
			shuffleDecks();
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

	@Override
	public Action getAction() {
		return Action.SETUP;
	}

	@Override
	public void doAction(GameInputHandler input, GameOutputHandler output) {
		init();
	}

	@Override
	public boolean validateAction(GameInputHandler input, GameOutputHandler output) {
		this.numberOfPlayers = input.getSelectedNumberOfPlayers();
				//TODO
		if (!currentState.equals(State.GAME_START))
		{
			System.out.println("Game is already setup!");
			return false;
		}
		
		return true;
	}	
}
