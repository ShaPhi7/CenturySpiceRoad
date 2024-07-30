package game;

import java.util.HashMap;
import java.util.Map;

public class SpiceInventory {

	Map<Spice, Integer> cubes = new HashMap<>();
	
    public SpiceInventory() {
        cubes = new HashMap<>();
        for (Spice spice : Spice.values()) {
            cubes.put(spice, 0);
        }
    }
    
    public SpiceInventory(int y, int r, int g, int b) {
        cubes = new HashMap<>();
        cubes.put(Spice.YELLOW_TUMERIC, y);
        cubes.put(Spice.RED_SAFFRON, r);
        cubes.put(Spice.GREEN_CARDAMOM, g);
        cubes.put(Spice.BROWN_CINNAMON, b);
    }
    
    public void addSpice(Spice spice) {
    	addSpices(spice, 1);
    }
    
    // Add spices to the inventory
    public void addSpices(Spice spice, int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative");
        }
        cubes.put(spice, cubes.get(spice) + quantity);
    }
    
    public void addSpices(SpiceInventory otherInventory) {
        for (Map.Entry<Spice, Integer> entry : otherInventory.cubes.entrySet()) {
            Spice spice = entry.getKey();
            int quantityToAdd = entry.getValue();
            
            if (quantityToAdd > 0) {
                addSpices(spice, quantityToAdd);
            }
        }
    }

    // Remove spices from the inventory
    public boolean removeSpices(Spice spice, int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative");
        }
        int currentQuantity = cubes.get(spice);
        if (currentQuantity < quantity) {
            return false; // Not enough spices to remove
        }
        cubes.put(spice, currentQuantity - quantity);
        return true;
    }
    
    public boolean removeSpices(SpiceInventory otherInventory) {
        boolean allRemoved = true;
        
        for (Map.Entry<Spice, Integer> entry : otherInventory.cubes.entrySet()) {
            Spice spice = entry.getKey();
            int quantityToRemove = entry.getValue();
            
            if (quantityToRemove > 0) {
                boolean result = removeSpices(spice, quantityToRemove);
                if (!result) {
                    allRemoved = false; // Not enough of this spice to remove
                }
            }
        }
        
        return allRemoved;
    }

    // Get the quantity of a specific spice
    public int getQuantity(Spice spice) {
        return cubes.getOrDefault(spice, 0);
    }
    
    public Map<Spice, Integer> getCubes() {
		return cubes;
	}

	// Print out the inventory
    public void printInventory() {
        System.out.println("Spice Inventory:");
        for (Map.Entry<Spice, Integer> entry : cubes.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    public boolean canAfford(SpiceInventory cost) {
        for (Map.Entry<Spice, Integer> entry : cost.cubes.entrySet()) {
            Spice spice = entry.getKey();
            int requiredQuantity = entry.getValue();
            if (this.getQuantity(spice) < requiredQuantity) {
                return false;
            }
        }
        return true;
    }

	public int getTotalCubes() {
		return cubes.values().stream().mapToInt(Integer::intValue).sum();	
	}

	public Spice getLowestValueCubePresent() {
		if (getQuantity(Spice.YELLOW_TUMERIC) > 0)
		{
			return Spice.YELLOW_TUMERIC;
		}
		else if (getQuantity(Spice.RED_SAFFRON) > 0)
		{
			return Spice.RED_SAFFRON;
		} 
		else if (getQuantity(Spice.GREEN_CARDAMOM) > 0)
		{
			return Spice.GREEN_CARDAMOM;
		} 
		else if (getQuantity(Spice.BROWN_CINNAMON) > 0)
		{
			return Spice.BROWN_CINNAMON;
		} 
		else {
			return null;
		}
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("Y:");
		sb.append(String.valueOf(getQuantity(Spice.YELLOW_TUMERIC)));
		sb.append(" G:");
		sb.append(String.valueOf(getQuantity(Spice.RED_SAFFRON)));
		sb.append(" R:");
		sb.append(String.valueOf(getQuantity(Spice.GREEN_CARDAMOM)));
		sb.append(" B:");
		sb.append(String.valueOf(getQuantity(Spice.BROWN_CINNAMON)));
		
		return sb.toString();		
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cubes == null) ? 0 : cubes.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SpiceInventory other = (SpiceInventory) obj;
		if (cubes == null) {
			if (other.cubes != null)
				return false;
		} else if (!cubes.equals(other.cubes))
			return false;
		return true;
	}
}
