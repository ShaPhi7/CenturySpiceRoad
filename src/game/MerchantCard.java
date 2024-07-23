package game;

public abstract class MerchantCard extends Card {

	SpiceInventory cubes = new SpiceInventory();
	
	public abstract void play();
	
	public void placeCubeOnCard(Spice spice) {
		cubes.addSpices(spice, 1);
	}
}
