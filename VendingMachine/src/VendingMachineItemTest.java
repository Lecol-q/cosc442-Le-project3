import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class VendingMachineItemTest {
    
    @Test
    public void testConstructorWithPositivePrice() {
        VendingMachineItem item = new VendingMachineItem("Coke", 2.50);

        assertEquals(2.50, item.getPrice());
    }
    

    @Test 
    public void testConstructorWithNegativePriceThrowsException() {
        assertThrows(VendingMachineException.class, () -> {
            new VendingMachineItem("Coke", -1.00);
        });
    }

    @Test 
    public void testConstructorWithZeroPrice() {
        VendingMachineItem item = new VendingMachineItem("Coke", 0);
    }

    @Test 
    public void testGetName() {
        VendingMachineItem item = new VendingMachineItem("Coke", 2.5);
    }

    @Test 
    public void testGetPrice() {
        VendingMachineItem item = new VendingMachineItem("Coke", 2.5);

        assertEquals(2.5, item.getPrice());
    }
}
