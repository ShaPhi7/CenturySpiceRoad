package card;

import game.Player;
import game.Spice;
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
		for (SpiceUpgrade su : player.getSelectedUpgrades())
		{
			Spice cubeToBeUpgraded = su.getCubeToBeUpgraded();
			player.payCube(cubeToBeUpgraded);
			Spice upgradedCube = null;
			
			for (int i=0; i<su.getNumberOfTimesToUpgrade(); i++)
			{
				upgradedCube = cubeToBeUpgraded.getUpgrade();
				cubeToBeUpgraded = upgradedCube;
			}
			player.gainSpice(upgradedCube);
		}
		
	}

	public int getPermittedUpgrades() {
		return permittedUpgrades;
	}

	public void setPermittedUpgrades(int permittedUpgrades) {
		this.permittedUpgrades = permittedUpgrades;
	}

	@Override
	public String toString() {
		return "MerchantCard [ Upgrade " + permittedUpgrades + " spices]";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + permittedUpgrades;
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
		UpgradeCard other = (UpgradeCard) obj;
		if (permittedUpgrades != other.permittedUpgrades)
			return false;
		return true;
	}
}
