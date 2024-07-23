package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import game.Spice;
import game.SpiceInventory;

public class SpiceInventoryTest {

    private SpiceInventory inventory1;
    private SpiceInventory inventory2;

    @BeforeEach
    public void setUp() {
        inventory1 = new SpiceInventory();
        inventory2 = new SpiceInventory();
    }

    @Test
    public void testAddSpices() {
        inventory1.addSpices(Spice.RED_SAFFRON, 10);
        inventory1.addSpices(Spice.GREEN_CARDAMOM, 5);
        
        assertEquals(10, inventory1.getQuantity(Spice.RED_SAFFRON));
        assertEquals(5, inventory1.getQuantity(Spice.GREEN_CARDAMOM));
    }

    @Test
    public void testRemoveSpices() {
        inventory1.addSpices(Spice.RED_SAFFRON, 10);
        boolean removed = inventory1.removeSpices(Spice.RED_SAFFRON, 5);
        
        assertTrue(removed);
        assertEquals(5, inventory1.getQuantity(Spice.RED_SAFFRON));
    }

    @Test
    public void testRemoveSpicesNotEnough() {
        inventory1.addSpices(Spice.RED_SAFFRON, 5);
        boolean removed = inventory1.removeSpices(Spice.RED_SAFFRON, 10);
        
        assertFalse(removed);
        assertEquals(5, inventory1.getQuantity(Spice.RED_SAFFRON));
    }

    @Test
    public void testRemoveSpicesNegativeQuantity() {
        inventory1.addSpices(Spice.RED_SAFFRON, 10);
        assertThrows(IllegalArgumentException.class, () -> {
            inventory1.removeSpices(Spice.RED_SAFFRON, -5);
        });
    }

    @Test
    public void testRemoveSpicesBasedOnInventory() {
        inventory1.addSpices(Spice.RED_SAFFRON, 10);
        inventory1.addSpices(Spice.GREEN_CARDAMOM, 5);

        inventory2.addSpices(Spice.RED_SAFFRON, 5);
        inventory2.addSpices(Spice.GREEN_CARDAMOM, 3);

        boolean allRemoved = inventory1.removeSpices(inventory2);

        assertTrue(allRemoved);
        assertEquals(5, inventory1.getQuantity(Spice.RED_SAFFRON));
        assertEquals(2, inventory1.getQuantity(Spice.GREEN_CARDAMOM));
    }

    @Test
    public void testRemoveSpicesBasedOnInventoryNotRemoved() {
        inventory1.addSpices(Spice.RED_SAFFRON, 5);
        inventory1.addSpices(Spice.GREEN_CARDAMOM, 5);

        inventory2.addSpices(Spice.RED_SAFFRON, 10); // More than available
        inventory2.addSpices(Spice.GREEN_CARDAMOM, 3);

        boolean allRemoved = inventory1.removeSpices(inventory2);

        assertFalse(allRemoved);
        assertEquals(5, inventory1.getQuantity(Spice.RED_SAFFRON)); // do not remove
        assertEquals(2, inventory1.getQuantity(Spice.GREEN_CARDAMOM)); // do not remove
    }
}