import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VendingMachineItemTest {

    @Test
    void constructorStoresNameAndPrice() throws VendingMachineException {
        // Arrange
        String expectedName = "Chips";
        double expectedPrice = 1.50;

        // Act
        VendingMachineItem item =
                new VendingMachineItem(expectedName, expectedPrice);

        // Assert
        assertEquals(expectedName, item.getName());
        assertEquals(expectedPrice, item.getPrice(), 0.0001);
    }

    @Test
    void constructorAcceptsZeroPrice() throws VendingMachineException {
        // Arrange
        String name = "Free Sample";
        double price = 0.0;

        // Act
        VendingMachineItem item = new VendingMachineItem(name, price);

        // Assert
        assertEquals(name, item.getName());
        assertEquals(0.0, item.getPrice(), 0.0001);
    }

    @Test
    void constructorRejectsNegativePrice() {
        // Arrange
        String name = "Chips";
        double price = -0.01;

        // Act
        VendingMachineException exception = assertThrows(
                VendingMachineException.class,
                () -> new VendingMachineItem(name, price)
        );

        // Assert
        assertEquals(
                "Price cannot be less than zero",
                exception.getMessage()
        );
    }

    @Test
    void getNameReturnsOriginalName() throws VendingMachineException {
        // Arrange
        VendingMachineItem item =
                new VendingMachineItem("Water", 1.25);

        // Act
        String actualName = item.getName();

        // Assert
        assertEquals("Water", actualName);
    }

    @Test
    void getPriceReturnsOriginalPrice() throws VendingMachineException {
        // Arrange
        VendingMachineItem item =
                new VendingMachineItem("Water", 1.25);

        // Act
        double actualPrice = item.getPrice();

        // Assert
        assertEquals(1.25, actualPrice, 0.0001);
    }
}