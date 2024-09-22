package csr.card;

import csr.game.SpiceInventory;
import csr.view.GameInputHandler;

public abstract class Card {

	public abstract SpiceInventory getCost(GameInputHandler input);
}
