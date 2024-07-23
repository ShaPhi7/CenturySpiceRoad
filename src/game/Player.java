package game;

/**
 * aka Caravan
 */
public class Player {

	private final boolean firstPlayer;
	
	public Player(boolean firstPlayer) {
		this.firstPlayer = firstPlayer;
		System.out.println("Player entered the game");
	}

	public boolean isFirstPlayer() {
		return firstPlayer;
	}

}
