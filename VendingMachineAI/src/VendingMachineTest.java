import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class VendingMachineTest {

    private VendingMachine machine;

    @BeforeEach
    void setUp() {
        machine = new VendingMachine();
    }

    private VendingMachineItem createItem(String name, double price)
            throws VendingMachineException {
        return new VendingMachineItem(name, price);
    }

    @Test
    void constructorInitializesEmptySlotsAndZeroBalance()
            throws VendingMachineException {
        // Arrange
        // A new machine is created in setUp().

        // Act
        double balance = machine.getBalance();

        // Assert
        assertEquals(0.0, balance, 0.0001);

        assertNull(machine.getItem(VendingMachine.A_CODE));
        assertNull(machine.getItem(VendingMachine.B_CODE));
        assertNull(machine.getItem(VendingMachine.C_CODE));
        assertNull(machine.getItem(VendingMachine.D_CODE));
    }

    @Test
    void constantsDefineFourSlotsAndTheirCodes() {
        // Arrange
        // No additional setup is required.

        // Act and Assert
        assertEquals(4, VendingMachine.NUM_SLOTS);
        assertEquals("A", VendingMachine.A_CODE);
        assertEquals("B", VendingMachine.B_CODE);
        assertEquals("C", VendingMachine.C_CODE);
        assertEquals("D", VendingMachine.D_CODE);
    }

    @ParameterizedTest
    @ValueSource(strings = {"A", "B", "C", "D"})
    void addItemStoresItemInSpecifiedSlot(String code)
            throws VendingMachineException {
        // Arrange
        VendingMachineItem item = createItem("Water", 1.25);

        // Act
        machine.addItem(item, code);

        // Assert
        assertSame(item, machine.getItem(code));
    }

    @Test
    void addItemRejectsOccupiedSlot() throws VendingMachineException {
        // Arrange
        VendingMachineItem original = createItem("Water", 1.25);
        VendingMachineItem replacement = createItem("Chips", 1.50);
        machine.addItem(original, "A");

        // Act
        VendingMachineException exception = assertThrows(
                VendingMachineException.class,
                () -> machine.addItem(replacement, "A")
        );

        // Assert
        assertEquals("Slot A already occupied", exception.getMessage());
        assertSame(original, machine.getItem("A"));
    }

    @Test
    void addItemRejectsInvalidCode() throws VendingMachineException {
        // Arrange
        VendingMachineItem item = createItem("Water", 1.25);

        // Act
        VendingMachineException exception = assertThrows(
                VendingMachineException.class,
                () -> machine.addItem(item, "E")
        );

        // Assert
        assertEquals(
                "Invalid code for vending machine item",
                exception.getMessage()
        );
    }

    @ParameterizedTest
    @ValueSource(strings = {"A", "B", "C", "D"})
    void removeItemReturnsItemAndEmptiesSlot(String code)
            throws VendingMachineException {
        // Arrange
        VendingMachineItem item = createItem("Water", 1.25);
        machine.addItem(item, code);

        // Act
        VendingMachineItem removed = machine.removeItem(code);

        // Assert
        assertSame(item, removed);
        assertNull(machine.getItem(code));
    }

    @Test
    void removeItemFromEmptySlotThrowsException() {
        // Arrange
        // Slot A is empty.

        // Act
        VendingMachineException exception = assertThrows(
                VendingMachineException.class,
                () -> machine.removeItem("A")
        );

        // Assert
        assertEquals(
                "Slot A is empty -- cannot remove item",
                exception.getMessage()
        );
    }

    @Test
    void removeItemWithInvalidCodeThrowsException() {
        // Arrange
        String invalidCode = "E";

        // Act
        VendingMachineException exception = assertThrows(
                VendingMachineException.class,
                () -> machine.removeItem(invalidCode)
        );

        // Assert
        assertEquals(
                "Invalid code for vending machine item",
                exception.getMessage()
        );
    }

    @Test
    void insertMoneyIncreasesBalance() throws VendingMachineException {
        // Arrange
        // The initial balance is expected to be zero.

        // Act
        machine.insertMoney(2.50);
        machine.insertMoney(1.25);

        // Assert
        assertEquals(3.75, machine.getBalance(), 0.0001);
    }

    @Test
    void insertMoneyAcceptsZeroAccordingToSpecification()
            throws VendingMachineException {
        // Arrange
        // The initial balance is expected to be zero.

        // Act
        machine.insertMoney(0.0);

        // Assert
        assertEquals(0.0, machine.getBalance(), 0.0001);
    }

    @Test
    void insertMoneyAcceptsPositiveAmountBelowOneDollar()
            throws VendingMachineException {
        // Arrange
        double amount = 0.50;

        // Act
        machine.insertMoney(amount);

        // Assert
        assertEquals(amount, machine.getBalance(), 0.0001);
    }

    @Test
    void insertMoneyRejectsNegativeAmount() {
        // Arrange
        double initialBalance = machine.getBalance();

        // Act
        VendingMachineException exception = assertThrows(
                VendingMachineException.class,
                () -> machine.insertMoney(-1.0)
        );

        // Assert
        assertEquals(
                "Invalid amount.  Amount must be >= 0",
                exception.getMessage()
        );
        assertEquals(initialBalance, machine.getBalance(), 0.0001);
    }

    @Test
    void getBalanceReturnsCurrentBalance()
            throws VendingMachineException {
        // Arrange
        machine.insertMoney(5.00);

        // Act
        double balance = machine.getBalance();

        // Assert
        assertEquals(5.00, balance, 0.0001);
    }

    @Test
    void makePurchaseSucceedsWithExactAmount()
            throws VendingMachineException {
        // Arrange
        VendingMachineItem item = createItem("Water", 1.50);
        machine.addItem(item, "A");
        machine.insertMoney(1.50);

        // Act
        boolean purchased = machine.makePurchase("A");

        // Assert
        assertTrue(purchased);
        assertEquals(0.0, machine.getBalance(), 0.0001);
        assertNull(machine.getItem("A"));
    }

    @Test
    void makePurchaseSucceedsWithExcessMoney()
            throws VendingMachineException {
        // Arrange
        VendingMachineItem item = createItem("Chips", 1.25);
        machine.addItem(item, "B");
        machine.insertMoney(2.00);

        // Act
        boolean purchased = machine.makePurchase("B");

        // Assert
        assertTrue(purchased);
        assertEquals(0.75, machine.getBalance(), 0.0001);
        assertNull(machine.getItem("B"));
    }

    @Test
    void makePurchaseReturnsFalseWhenBalanceIsInsufficient()
            throws VendingMachineException {
        // Arrange
        VendingMachineItem item = createItem("Chips", 1.50);
        machine.addItem(item, "A");
        machine.insertMoney(1.00);

        // Act
        boolean purchased = machine.makePurchase("A");

        // Assert
        assertFalse(purchased);
        assertEquals(1.00, machine.getBalance(), 0.0001);
        assertSame(item, machine.getItem("A"));
    }

    @Test
    void makePurchaseReturnsFalseForEmptySlot()
            throws VendingMachineException {
        // Arrange
        machine.insertMoney(2.00);

        // Act
        boolean purchased = machine.makePurchase("A");

        // Assert
        assertFalse(purchased);
        assertEquals(2.00, machine.getBalance(), 0.0001);
        assertNull(machine.getItem("A"));
    }

    @Test
    void makePurchaseWithInvalidCodeThrowsException() {
        // Arrange
        String invalidCode = "E";

        // Act
        assertThrows(
                VendingMachineException.class,
                () -> machine.makePurchase(invalidCode)
        );

        // Assert
        assertEquals(0.0, machine.getBalance(), 0.0001);
    }

    @Test
    void makePurchaseAllowsFreeItem()
            throws VendingMachineException {
        // Arrange
        VendingMachineItem item = createItem("Free Sample", 0.0);
        machine.addItem(item, "A");

        // Act
        boolean purchased = machine.makePurchase("A");

        // Assert
        assertTrue(purchased);
        assertEquals(0.0, machine.getBalance(), 0.0001);
        assertNull(machine.getItem("A"));
    }

    @Test
    void returnChangeReturnsBalanceAndResetsItToZero()
            throws VendingMachineException {
        // Arrange
        machine.insertMoney(3.75);

        // Act
        double change = machine.returnChange();

        // Assert
        assertEquals(3.75, change, 0.0001);
        assertEquals(0.0, machine.getBalance(), 0.0001);
    }

    @Test
    void returnChangeOnZeroBalanceReturnsZero()
            throws VendingMachineException {
        // Arrange
        // The initial balance is expected to be zero.

        // Act
        double change = machine.returnChange();

        // Assert
        assertEquals(0.0, change, 0.0001);
        assertEquals(0.0, machine.getBalance(), 0.0001);
    }

    @Test
    void returnChangeCanBeCalledRepeatedly()
            throws VendingMachineException {
        // Arrange
        machine.insertMoney(2.00);

        // Act
        double firstReturn = machine.returnChange();
        double secondReturn = machine.returnChange();

        // Assert
        assertEquals(2.00, firstReturn, 0.0001);
        assertEquals(0.0, secondReturn, 0.0001);
        assertEquals(0.0, machine.getBalance(), 0.0001);
    }
}