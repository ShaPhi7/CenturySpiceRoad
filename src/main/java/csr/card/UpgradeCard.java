package csr.card;

import csr.game.Player;
import csr.game.Spice;
import csr.game.SpiceInventory;
import csr.game.SpiceUpgrade;
import csr.view.GameInputHandler;

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
	public SpiceInventory getCost(GameInputHandler input) {
		return SpiceUpgrade.getCost(input.getSelectedUpgrades());
	}
	
	@Override
	public void play(GameInputHandler input) {
		Player player = input.getPlayer();
		for (SpiceUpgrade su : input.getSelectedUpgrades())
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
		return "MerchantCard [Upgrade " + permittedUpgrades + " spices]";
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
