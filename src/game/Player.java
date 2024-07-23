package game;

import java.util.List;

/**
 * aka Caravan
 */
public class Player {

	private final boolean startingPlayer;
	
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
	}

	public static void nextPlayer(List<Player> players) {
		Player player = players.removeFirst();
		players.addLast(player);
	}
}
