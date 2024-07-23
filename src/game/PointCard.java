package game;

import java.util.HashMap;

public class PointCard extends Card {

	private HashMap<Spice, Integer> cost;
	private int value;
	
	public HashMap<Spice, Integer> getCost() {
		return cost;
	}
	public void setCost(HashMap<Spice, Integer> cost) {
		this.cost = cost;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}	
}
