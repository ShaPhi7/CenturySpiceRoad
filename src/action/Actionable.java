package action;

import game.Player;

public interface Actionable {

	public String getActionName();
	public void doAction(Player player);
	public boolean valid(Player player);
}