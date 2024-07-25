package card;

import game.Player;
import game.SpiceInventory;
import game.SpiceUpgrade;

public class UpgradeCard extends MerchantCard {

	private int permittedUpgrades;
	
	public UpgradeCard()
	{
		this.permittedUpgrades = 2; //starting card
	}
	
	public UpgradeCard(int permittedUpgrades)
	{
		this.permittedUpgrades = permittedUpgrades;
	}
	
	@Override
	public SpiceInventory getCost(Player player) {
		return SpiceUpgrade.getCost(player.getSelectedUpgrades());
	}
	
	@Override
	public void play(Player player) {
		//TODO
		
	}

	public int getPermittedUpgrades() {
		return permittedUpgrades;
	}

	public void setPermittedUpgrades(int permittedUpgrades) {
		this.permittedUpgrades = permittedUpgrades;
	}
}
