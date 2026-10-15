package game;

import java.awt.Color;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class InventoryTest {

    private Item createItem() {
        return new Item('!', Color.WHITE, "Test Item", null);
    }

    @Test
    void constructorCreatesInventoryWithRequestedCapacity() {
        Inventory inventory = new Inventory(3);

        assertEquals(3, inventory.getItems().length);
        assertNull(inventory.get(0));
        assertFalse(inventory.isFull());
    }

    @Test
    void addPlacesItemInFirstEmptySlot() {
        Inventory inventory = new Inventory(2);
        Item item = createItem();

        inventory.add(item);

        assertSame(item, inventory.get(0));
        assertTrue(inventory.contains(item));
    }

    @Test
    void addPlacesItemsInOrderAcrossAvailableSlots() {
        Inventory inventory = new Inventory(2);
        Item first = createItem();
        Item second = createItem();

        inventory.add(first);
        inventory.add(second);

        assertSame(first, inventory.get(0));
        assertSame(second, inventory.get(1));
        assertTrue(inventory.isFull());
    }

    @Test
    void addDoesNothingWhenInventoryIsFull() {
        Inventory inventory = new Inventory(1);
        Item first = createItem();
        Item second = createItem();

        inventory.add(first);
        inventory.add(second);

        assertSame(first, inventory.get(0));
        assertFalse(inventory.contains(second));
    }

    @Test
    void removeClearsSlotContainingSameItem() {
        Inventory inventory = new Inventory(2);
        Item item = createItem();
        inventory.add(item);

        inventory.remove(item);

        assertNull(inventory.get(0));
        assertFalse(inventory.contains(item));
        assertFalse(inventory.isFull());
    }

    @Test
    void removeDoesNothingWhenItemIsNotPresent() {
        Inventory inventory = new Inventory(2);
        Item stored = createItem();
        Item absent = new Item('?', Color.RED, "Absent Item", null);
        inventory.add(stored);

        inventory.remove(absent);

        assertSame(stored, inventory.get(0));
        assertFalse(inventory.contains(absent));
    }

    @Test
    void removeUsesObjectIdentityRatherThanEquality() {
        Inventory inventory = new Inventory(1);
        Item stored = createItem();
        Item other = new Item('?', Color.RED, "Test Item", null);
        inventory.add(stored);

        inventory.remove(other);

        assertSame(stored, inventory.get(0));
    }

    @Test
    void containsReturnsFalseForEmptyInventory() {
        Inventory inventory = new Inventory(2);

        assertFalse(inventory.contains(createItem()));
    }

    @Test
    void containsReturnsTrueForItemInLaterSlot() {
        Inventory inventory = new Inventory(2);
        Item first = createItem();
        Item second = new Item('?', Color.RED, "Second Item", null);

        inventory.add(first);
        inventory.add(second);

        assertTrue(inventory.contains(second));
    }

    @Test
    void isFullReturnsTrueWhenAllSlotsAreOccupied() {
        Inventory inventory = new Inventory(2);
        inventory.add(createItem());
        inventory.add(new Item('?', Color.RED, "Second Item", null));

        assertTrue(inventory.isFull());
    }

    @Test
    void isFullReturnsFalseWhenAtLeastOneSlotIsEmpty() {
        Inventory inventory = new Inventory(2);
        inventory.add(createItem());

        assertFalse(inventory.isFull());
    }

    @Test
    void zeroCapacityInventoryIsFull() {
        Inventory inventory = new Inventory(0);

        assertTrue(inventory.isFull());
        assertFalse(inventory.contains(createItem()));
    }

    @Test
    void removeAllowsInventoryToBeFilledAgain() {
        Inventory inventory = new Inventory(1);
        Item first = createItem();
        Item second = new Item('?', Color.RED, "Second Item", null);

        inventory.add(first);
        inventory.remove(first);
        inventory.add(second);

        assertSame(second, inventory.get(0));
        assertTrue(inventory.isFull());
    }
}
