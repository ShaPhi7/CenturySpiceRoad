package game;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
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
	
	public static Map<Spice, Integer> getMaxUpgrades() {
	    Map<Spice, Integer> maxUpgrades = new EnumMap<>(Spice.class);
	    maxUpgrades.put(Spice.BROWN_CINNAMON, 0);
	    maxUpgrades.put(Spice.GREEN_CARDAMOM, 1);
	    maxUpgrades.put(Spice.RED_SAFFRON, 2);
	    maxUpgrades.put(Spice.YELLOW_TUMERIC, 3);
	    
	    return maxUpgrades;
	}
	
	public static Map<Spice, Spice> getUpgradesMap() {
	    Map<Spice, Spice> upgrades = new EnumMap<>(Spice.class);
	    upgrades.put(Spice.YELLOW_TUMERIC, Spice.RED_SAFFRON);
	    upgrades.put(Spice.RED_SAFFRON, Spice.GREEN_CARDAMOM);
	    upgrades.put(Spice.GREEN_CARDAMOM, Spice.BROWN_CINNAMON);
	    
	    return upgrades;
	}

	public static int getTotalUpgrades(List<SpiceUpgrade> selectedUpgrades) {
		return selectedUpgrades.stream().mapToInt(su -> su.getNumberOfTimesToUpgrade()).sum();
	}
}
