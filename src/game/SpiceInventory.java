package game;

import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

public class SpiceInventory {

	Map<Spice, Integer> cubes = new HashMap<>();
	
    public SpiceInventory() {
        cubes = new HashMap<>();
        for (Spice spice : Spice.values()) {
            cubes.put(spice, 0);
        }
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

	@Override
	public String toString() {
		StringJoiner sj = new StringJoiner(" ");
		
		sj.add("SpiceInventory:");
		sj.add("Y:");
		sj.add(String.valueOf(getQuantity(Spice.YELLOW_TUMERIC)));
		sj.add("G:");
		sj.add(String.valueOf(getQuantity(Spice.RED_SAFFRON)));
		sj.add("R:");
		sj.add(String.valueOf(getQuantity(Spice.GREEN_CARDAMOM)));
		sj.add("B:");
		sj.add(String.valueOf(getQuantity(Spice.BROWN_CINNAMON)));
		
		return sj.toString();
				
				
	}
	
	
}
