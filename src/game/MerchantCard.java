package game;

import java.util.List;

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
		List<Card> deck = Game.merchantCardDeckRow.deck;
		int indexOf = deck.indexOf(this);
		
		return Math.max(indexOf, 0);
	}
}
