package card;

import java.util.List;

import game.Game;
import game.Player;
import game.Spice;
import game.SpiceInventory;

public abstract class MerchantCard extends Card {

	SpiceInventory cubesWithAcquire = new SpiceInventory();
	
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
	
	public int getAcquireCost()
	{
		List<Card> deck = Game.merchantCardDeckRow.getDeck();
		int indexOf = deck.indexOf(this);
		
		return Math.max(indexOf, 0);
	}
}
