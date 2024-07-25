package card;

import game.Player;
import game.SpiceInventory;

public abstract class Card {

	public abstract SpiceInventory getCost(Player player);
}
