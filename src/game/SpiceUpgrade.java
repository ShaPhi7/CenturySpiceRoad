package game;

import java.util.List;
import java.util.stream.Collectors;

public class SpiceUpgrade {

	Spice cubeToBeUpgraded;
	int numberOfTimesToUpgrade;
	
	public SpiceUpgrade(Spice cubeToBeUpgraded, int numberOfTimesToUpgrade) {
		this.cubeToBeUpgraded = cubeToBeUpgraded;
		this.numberOfTimesToUpgrade = numberOfTimesToUpgrade;
	}
	
	public Spice getCubeToBeUpgraded() {
		return cubeToBeUpgraded;
	}
	
	public void setCubeToBeUpgraded(Spice cubeToBeUpgraded) {
		this.cubeToBeUpgraded = cubeToBeUpgraded;
	}
	
	public int getNumberOfTimesToUpgrade() {
		return numberOfTimesToUpgrade;
	}
	
	public void setNumberOfTimesToUpgrade(int numberOfTimesToUpgrade) {
		this.numberOfTimesToUpgrade = numberOfTimesToUpgrade;
	}
	
	public static SpiceInventory getCost(List<SpiceUpgrade> spiceUpgrades) {
		SpiceInventory spiceInventory = new SpiceInventory();
		
		spiceUpgrades.stream()
        .collect(Collectors.groupingBy(
            SpiceUpgrade::getCubeToBeUpgraded,
            Collectors.summingInt(e -> 1)
        ))
        .forEach(spiceInventory::addSpices);
        
		return spiceInventory;
    }
}
