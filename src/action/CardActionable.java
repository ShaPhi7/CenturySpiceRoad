package action;

import card.Card;
import game.Player;

/**
 * Something in card actionable if an action can be performed on a specific card
 * 
 * TODO - needed?
 */
public interface CardActionable extends Actionable {

	public void doAction(Player player, Card card);
	public boolean valid(Player player, Card card);
	
	public default void doAction(Player player)
	{
		doAction(player, null);
	}
	public default boolean validateAction(Player player)
	{
		return valid(player, null);
	}
}
