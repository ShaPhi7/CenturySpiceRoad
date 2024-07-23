package game;

public class PointCard extends Card {

	private SpiceInventory cost;
	private int value;
	
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
