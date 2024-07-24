package card;

import game.SpiceInventory;

public class PointCard extends Card {

	private SpiceInventory cost = new SpiceInventory();
	private int value = 0;
	
	public SpiceInventory getCost() {
		return cost;
	}
	public void setCost(SpiceInventory cost) {
		this.cost = cost;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}	
}
