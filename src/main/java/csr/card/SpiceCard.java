package csr.card;

import csr.game.SpiceInventory;
import csr.view.GameInputHandler;

public class SpiceCard extends MerchantCard {

	SpiceInventory spiceInventory;
	
	public SpiceCard()
	{
		spiceInventory = new SpiceInventory();
	}
	
	public SpiceCard(SpiceInventory spiceInventory)
	{
		this.spiceInventory = spiceInventory;
	}
	
	@Override
	public SpiceInventory getCost(GameInputHandler input) {
		return new SpiceInventory();
	}
	
	@Override
	public void play(GameInputHandler input) {
		input.getPlayer().gainSpices(spiceInventory);
	}

	@Override
	public String toString() {
		return "MerchantCard [Gain " + spiceInventory.toString() + "]";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((spiceInventory == null) ? 0 : spiceInventory.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		SpiceCard other = (SpiceCard) obj;
		if (spiceInventory == null) {
			if (other.spiceInventory != null)
				return false;
		} else if (!spiceInventory.equals(other.spiceInventory))
			return false;
		return true;
	}
	
	
}
