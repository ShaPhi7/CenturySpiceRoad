package card;

import game.Player;
import game.SpiceInventory;

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
	public void play(Player player) {
		player.gainSpices(spiceInventory);
	}
}
