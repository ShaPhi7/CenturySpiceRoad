package game;

import java.util.Map;

public enum Spice {

	BROWN_CINNAMON,
	GREEN_CARDAMOM,
	RED_SAFFRON,
	YELLOW_TUMERIC;

	public Spice getUpgrade() {
		Map<Spice, Spice> upgradesMap = SpiceUpgrade.getUpgradesMap();
		return upgradesMap.get(this);		
	}
}
