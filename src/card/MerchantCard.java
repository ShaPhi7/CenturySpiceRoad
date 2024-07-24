package card;

import java.util.List;

import game.Game;
import game.Spice;
import game.SpiceInventory;

public abstract class MerchantCard extends Card {

	SpiceInventory cubes = new SpiceInventory();
	
	public abstract void play();
	
	public SpiceInventory getCubesOnCard()
	{
		return cubes;
	}
	
	public int getTotalNumberOfCubesOnCard() {
		return cubes.getTotalCubes();
	}
	
	public void placeCubeOnCard(Spice spice) {
		cubes.addSpices(spice, 1);
	}
	
	public int getCubeCostOf()
	{
		List<Card> deck = Game.merchantCardDeckRow.getDeck();
		int indexOf = deck.indexOf(this);
		
		return Math.max(indexOf, 0);
	}
}
