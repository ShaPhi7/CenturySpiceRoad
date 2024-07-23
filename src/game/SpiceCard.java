package game;

public class SpiceCard extends Card {

	SpiceInventory cubes = new SpiceInventory();
	
	public void placeCubeOnCard(Spice spice) {
		cubes.addSpices(spice, 1);
	}
}
