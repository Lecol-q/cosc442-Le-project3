import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import org.junit.jupiter.api.Test;

public class VendingMachineTest {

    @Test 
    public void testInitialBalance() {
        VendingMachine machine = new VendingMachine();

        assertEquals(0, machine.getBalance());
    }
    
    @Test 
    public void testInitialSlotsAreEmpty() {
        VendingMachine machine = new VendingMachine();

        assertNull(machine.getItem("A"));
        assertNull(machine.getItem("B"));
        assertNull(machine.getItem("C"));
        assertNull(machine.getItem("D"));
    }

    @Test 
    public void addItem() {
        VendingMachine machine = new VendingMachine();
        VendingMachineItem item = new VendingMachineItem("Coke", 2.5);

        machine.addItem(item, "A");

        assertEquals(item, machine.getItem("A"));
    }

    @Test 
    public void testAddItemToOccupiedSlotThrowsException() {
        VendingMachine machine = new VendingMachine();
        VendingMachineItem firstItem = new VendingMachineItem("Coke", 2.5);
        VendingMachineItem secondItem = new VendingMachineItem("Pepsi", 2);

        machine.addItem(firstItem, "A");

        assertThrows(VendingMachineException.class, () -> {
            machine.addItem(secondItem, "A");
        });
    }

    @Test 
    public void testAddItemWithInvalidCodeThrowsException() {
        VendingMachine machine = new VendingMachine();
        VendingMachineItem item = new VendingMachineItem("Coke", 2.5);

        assertThrows(VendingMachineException.class, () -> {
            machine.addItem(item, "E");
        });
    }

    @Test 
    public void testRemoveItem() {
        VendingMachine machine = new VendingMachine();
        VendingMachineItem item = new VendingMachineItem("Coke", 2.5);
        machine.addItem(item, "A");

        VendingMachineItem removedItem = machine.removeItem("A");

        assertEquals(item, removedItem);
        assertNull(machine.getItem("A"));
    }

    @Test 
    public void testRemoveItemFromEmptySlotThrowsException() {
        VendingMachine machine = new VendingMachine();

        assertThrows(VendingMachineException.class, () -> {
            machine.removeItem("A");
        });
    }

    @Test 
    public void testRemoveItemWithInvalidCodeThrowsException() {
        VendingMachine machine = new VendingMachine();

        assertThrows(VendingMachineException.class, () -> {
            machine.removeItem("E");
        });
    }

    @Test 
    public void testInsertMoney() {
        VendingMachine machine = new VendingMachine();

        machine.insertMoney(5.00);

        assertEquals(5.00, machine.getBalance());
    }

    @Test 
    public void testInsertNegativeMoneyThrowsException() {
        VendingMachine machine = new VendingMachine();

        assertThrows(VendingMachineException.class, () -> {
            machine.insertMoney(-1);
        });
    }

    @Test 
    public void testInitialBalance() {
        VendingMachine machine = new VendingMachine();

        assertEquals(0.0, machine.getBalance());
    }

    @Test
    public void testReturnChange() {
        VendingMachine machine = new VendingMachine();

        machine.insertMoney(5.00);

        double change = machine.returnChange();

        assertEquals(5, change);
        assertEquals(0, machine.getBalance());
    }

    @Test 
    public void testReturnChangeWithZeroBalance() {
        VendingMachine machine = new VendingMachine();

        double change = machine.returnChange();

        assertEquals(0, change);
        assertEquals(0, machine.getBalance());
    }

    @Test 
    public void testMakePurchaseWithSufficientBalance() {
        VendingMachine machine = new VendingMachine();
        VendingMachineItem item = new VendingMachineItem("Coke", 2);

        machine.addItem(item, "A");
        machine.insertMoney(5);

        boolean result = machine.makePurchase("A");

        assertTrue(result);
        assertNull(machine.getItem("A"));
        assertEquals(3, machine.getBalance());
    }

    @Test 
    public void testMakePurchaseWithExactBalance() {
        VendingMachine machine = new VendingMachine();
        VendingMachineItem item = new VendingMachineItem("Coke", 2);

        machine.addItem(item, "A");
        machine.insertMoney(2);

        boolean result = machine.makePurchase("A");

        assertTrue(result);
        assertNull(machine.getItem("A"));
        assertEquals(0, machine.getBalance());
    }

    @Test
    public void testMakePurchaseWithInsufficientBalance() {
        VendingMachine machine = new VendingMachine();
        VendingMachineItem item = new VendingMachineItem("Coke", 5.00);

        machine.addItem(item, "A");
        machine.insertMoney(2.00);

        boolean result = machine.makePurchase("A");

        assertFalse(result);
        assertEquals(2.00, machine.getBalance());
        assertEquals(item, machine.getItem("A"));
    }

    @Test 
    public void testMakePurchaseFromEmptySlot() {
        VendingMachine machine = new VendingMachine();

        machine.insertMoney(5);

        boolean result = machine.makePurchase("A");

        assertFalse(result);
        assertEquals(5.00, machine.getBalance());
        assertNull(machine.getItem("A"));
    }

    @Test
    public void testMakePurchaseWithInvalidCodeThrowsException() {
        VendingMachine machine = new VendingMachine();

        machine.insertMoney(5.00);

        assertThrows(VendingMachineException.class, () -> {
        machine.makePurchase("E");
    });
    }

    @ParameterizedTest 
    @CsvSource({
        "A, true",
        "B, true",
        "C, true",
        "D, true",
        "E, false",
        "'', false"
    })

    public void testSlotCodes(String code, boolean valid) {
        VendingMachine machine = new VendingMachine();

        if(valid) {
            VendingMachineItem item = new VendingMachineItem("Coke", 2.00);

            machine.addItem(item, code);

            assertEquals(item, machine.getItem(code));
        } else {
            VendingMachineItem item = new VendingMachineItem("Coke", 2);

            assertThrows(VendingMachineException.class, () -> {
                machine.addItem(item, code);
            });
        }
    }
}