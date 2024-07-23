package game;

import java.util.List;
import java.util.TreeSet;

/**
 * aka Caravan
 */
public class Player {

	private final boolean startingPlayer;
	
	private TreeSet<Card> hand = new TreeSet<>();
	private TreeSet<Card> discard = new TreeSet<>();
	
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
	
	public TreeSet<Card> getHand() {
		return hand;
	}

	public void setHand(TreeSet<Card> hand) {
		this.hand = hand;
	}

	public TreeSet<Card> getDiscard() {
		return discard;
	}

	public void setDiscard(TreeSet<Card> discard) {
		this.discard = discard;
	}
}
