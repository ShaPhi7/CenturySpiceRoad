package csr.card;

import csr.game.SpiceInventory;
import csr.view.GameInputHandler;

public class PointCard extends Card {

	private SpiceInventory cost = new SpiceInventory();
	private int value = 0;
	
	public PointCard() {
	}
	
	public PointCard(int y, int r, int g, int b, int value) {
		this.cost = new SpiceInventory(y, r, g, b);
		this.value = value;
	}
	
	@Override
	public SpiceInventory getCost(GameInputHandler input) {
		return getCost();
	}
	
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

	@Override
	public String toString() {
		return "PointCard [" + cost + " V:" + value + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cost == null) ? 0 : cost.hashCode());
		result = prime * result + value;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PointCard other = (PointCard) obj;
		if (cost == null) {
			if (other.cost != null)
				return false;
		} else if (!cost.equals(other.cost))
			return false;
		if (value != other.value)
			return false;
		return true;
	}
}
