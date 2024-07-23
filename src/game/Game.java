package game;

import java.util.LinkedList;
import java.util.List;

public class Game {

	public static int NUMBER_OF_PLAYERS = 4;
	
	public static List<Player> players = new LinkedList<Player>();
	
	public static Player currentPlayer;
	
	public static void main(String[] args) {

		Player.setupPlayers(players);
		
		while(!shouldEndGame())
		{
			currentPlayer = players.getFirst();
			currentPlayer.getDiscard().execute(); //TODO - add 3 others and make generic
			Player.nextPlayer(players);
		}
	}

	private static boolean shouldEndGame() {
		//one player has 5 cards (6 if 2 players)
		//and it is the turn of the starting player
		return false;
	}


}
