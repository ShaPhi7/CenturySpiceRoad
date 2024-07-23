package game;

import java.util.LinkedList;
import java.util.List;

import action.RestAction;

public class Game {

	public static int NUMBER_OF_PLAYERS = 4;
	
	public static List<Player> players = new LinkedList<Player>();
	
	public static void main(String[] args) {

		Player.setupPlayers(players);
				
		while(!shouldEndGame())
		{
			Player currentPlayer = players.getFirst();
			doAction(currentPlayer);
			Player.nextPlayer(players);
		}
	}

	private static void doAction(Player currentPlayer) {
		
		RestAction restAction = new RestAction(); //3 other types of actions
		restAction.doAction(currentPlayer);
	}

	private static boolean shouldEndGame() {
		//one player has 5 cards (6 if 2 players)
		//and it is the turn of the starting player
		return false;
	}


}
