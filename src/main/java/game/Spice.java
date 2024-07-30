package game;

import java.util.Map;
import java.util.Optional;

public enum Spice {

	BROWN_CINNAMON("brown",'B'),
	GREEN_CARDAMOM("green", 'G'),
	RED_SAFFRON("red", 'R'),
	YELLOW_TUMERIC("yellow", 'Y');

	public Spice getUpgrade() {
		Map<Spice, Spice> upgradesMap = SpiceUpgrade.getUpgradesMap();
		return upgradesMap.get(this);		
	}
	
	private final String spiceColour;
	private final Character spiceChar;

	private Spice(String spiceColour, Character spiceChar) {
		this.spiceColour = spiceColour;
		this.spiceChar = spiceChar;
	}

	public Character getSpiceChar() {
		return spiceChar;
	}
	
	public String getSpiceColour() {
		return spiceColour;
	}

	@Override
	public String toString() {
		return spiceColour;
	}
	
	public static Optional<Spice> fromString(String text) {
        if (text.length() > 1)
        {
        	return fromColour(text);
        }
        else
        {
        	return fromChar(text.charAt(0));
        }
    }

	private static Optional<Spice> fromChar(Character text) {
		for (Spice spice : Spice.values()) {
            if (spice.getSpiceChar().equals(text)) {
                return Optional.of(spice);
            }
        }
        return Optional.empty();
	}
	
	private static Optional<Spice> fromColour(String text) {
        for (Spice spice : Spice.values()) {
            if (spice.getSpiceColour().equalsIgnoreCase(text)) {
                return Optional.of(spice);
            }
        }
        return Optional.empty();
    }
}
