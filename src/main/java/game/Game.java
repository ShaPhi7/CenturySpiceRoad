package game;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import board.MerchantCardDeckRow;
import board.PointCardDeckRow;

public class Game {

	public static int NUMBER_OF_PLAYERS = 4;
	public final static int NUMBER_OF_VISIBLE_MERCHANT_CARDS = 6;
	public final static int NUMBER_OF_VISIBLE_POINT_CARDS = 5;
	public final static int POINT_CARDS_GOAL_FOR_ONLY_TWO_PLAYERS = 6;
	public final static int POINT_CARDS_GOAL_FOR_OVER_TWO_PLAYERS = 5;
	
	public static List<Player> players = new LinkedList<Player>();
	public static MerchantCardDeckRow merchantCardDeckRow = new MerchantCardDeckRow();
	public static PointCardDeckRow pointCardDeckRow = new PointCardDeckRow();
	
	public static Player currentPlayer;
	
	public static void main(String[] args) {

		try {
			Player.setupPlayers(players);
			
			populateDecks();
			
			while(!shouldEndGame())
			{
				setCurrentPlayer(players.getFirst());
				getCurrentPlayer().getDiscard().execute(); //TODO - add 3 others and make generic
				Player.nextPlayer(players);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void populateDecks() throws IOException {
		merchantCardDeckRow.populateFromCsv("merchant-card-deck.csv");
		pointCardDeckRow.populateFromCsv("point-card-deck.csv");
		
	}

	private static boolean shouldEndGame() {
		//one player has 5 cards (6 if 2 players)
		//and it is the turn of the starting player
		return false;
	}

	private static int targetNumberOfPointCards()
	{
		if (NUMBER_OF_PLAYERS == 2)
		{
			return POINT_CARDS_GOAL_FOR_ONLY_TWO_PLAYERS;
		}
		
		return POINT_CARDS_GOAL_FOR_OVER_TWO_PLAYERS;
	}
	
	public static Player getCurrentPlayer() {
		return currentPlayer;
	}

	public static void setCurrentPlayer(Player currentPlayer) {
		Game.currentPlayer = currentPlayer;
	}


}
