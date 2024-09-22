package csr.game;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import csr.action.Action;
import csr.action.Actionable;
import csr.action.GameOutputHandler;
import csr.board.MerchantCardDeckRow;
import csr.board.PointCardDeckRow;
import csr.card.SpiceCard;
import csr.card.UpgradeCard;
import csr.view.GameInputHandler;

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
	public State currentState = State.NEW_GAME;
	
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
			
			for (Player player: players)
			{				
				SpiceInventory spiceInventory = new SpiceInventory();
				spiceInventory.addSpices(Spice.YELLOW_TUMERIC, 2);
				player.addToHand(new SpiceCard(spiceInventory));
				player.addToHand(new UpgradeCard(2));
				
				SpiceInventory startingCubes = getStartingCubes(players.indexOf(player));
				player.gainSpices(startingCubes);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

    public SpiceInventory getStartingCubes(int index) {
    	SpiceInventory ret = new SpiceInventory();
    	int position = index+1;
    	switch (position) {
            case 1:
                ret.addSpices(Spice.YELLOW_TUMERIC, 3);
                break;
            case 2:
            case 3:
                ret.addSpices(Spice.YELLOW_TUMERIC, 4);
                break;
            case 4:
            case 5:
            	ret.addSpices(Spice.YELLOW_TUMERIC, 3);
                ret.addSpices(Spice.RED_SAFFRON, 1);
                break;
    	}
    	return ret;
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
			currentState = State.GAME_ENDED;
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
	
	public State getCurrentState() {
		return currentState;
	}

	public void setCurrentState(State currentState) {
		this.currentState = currentState;
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
		if (!validateAction(input, output))
		{
			return;
		}
		this.numberOfPlayers = input.getSelectedNumberOfPlayers();
		init();
		currentState = State.NEW_TURN;
		output.addUpdate("The game is now setup for " + numberOfPlayers + " players.");
		output.addUpdate("Good luck!");
		output.updateAllVisibleComponents(this);
	}

	@Override
	public boolean validateAction(GameInputHandler input, GameOutputHandler output) {
		if (!currentState.equals(State.NEW_GAME))
		{
			System.out.println("Game is already setup!");
			return false;
		}
		
		return true;
	}
}
