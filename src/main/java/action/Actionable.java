package action;

import game.Player;

public interface Actionable {

	public Action getAction();
	public void doAction(Player player);
	public boolean validateAction(Player player);
}