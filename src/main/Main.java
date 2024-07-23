package main;

import java.util.LinkedList;
import java.util.List;

import game.Player;

public class Main {

	private static int NUMBER_OF_PLAYERS = 4;
	
	public static List<Player> players = new LinkedList<Player>();
	
	public static void main(String[] args) {

		setupPlayers();
				
		//while(!endGame)
		//{
		//	
		//}
	}

	private static void setupPlayers() {
		players.add(new Player(true));
		
		for (int i=1;i<NUMBER_OF_PLAYERS;i++)
		{
			players.add(new Player(false));
		}
	}
}
