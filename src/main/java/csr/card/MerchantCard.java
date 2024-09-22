package csr.card;

import java.util.List;

import csr.game.Game;
import csr.game.Spice;
import csr.game.SpiceInventory;
import csr.view.GameInputHandler;

public abstract class MerchantCard extends Card {

	private SpiceInventory cubesWithAcquire = new SpiceInventory();
	
	public abstract void play(GameInputHandler input);
	
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
	
	public int getCostToAcquire(Game game)
	{
		List<Card> deck = game.getMerchantCardDeckRow().getDeck();
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
