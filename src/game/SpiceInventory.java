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
    
    // Add spices to the inventory
    public void addSpices(Spice spice, int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative");
        }
        cubes.put(spice, cubes.get(spice) + quantity);
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

}
