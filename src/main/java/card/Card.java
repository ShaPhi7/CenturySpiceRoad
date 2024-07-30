package card;

import game.SpiceInventory;
import view.GameInputHandler;

public abstract class Card {

	public abstract SpiceInventory getCost(GameInputHandler input);
}
