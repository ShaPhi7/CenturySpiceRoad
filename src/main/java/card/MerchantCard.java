package card;

import java.util.List;

import game.Game;
import game.Player;
import game.Spice;
import game.SpiceInventory;

public abstract class MerchantCard extends Card {

	private SpiceInventory cubesWithAcquire = new SpiceInventory();
	
	public abstract void play(Player player);
	
	public SpiceInventory getCubesWithAcquire()
	{
		return cubesWithAcquire;
	}
	
	public int getTotalNumberOfCubesOnCard() {
		return cubesWithAcquire.getTotalCubes();
	}
	
	public void placeCubeOnCard(Spice spice) {
		cubesWithAcquire.addSpices(spice, 1);
	}
	
	public int getCostToAcquire()
	{
		List<Card> deck = Game.merchantCardDeckRow.getDeck();
		int indexOf = deck.indexOf(this);
		
		return Math.max(indexOf, 0);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cubesWithAcquire == null) ? 0 : cubesWithAcquire.hashCode());
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
		MerchantCard other = (MerchantCard) obj;
		if (cubesWithAcquire == null) {
			if (other.cubesWithAcquire != null)
				return false;
		} else if (!cubesWithAcquire.equals(other.cubesWithAcquire))
			return false;
		return true;
	}
}
