import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class VendingMachineExceptionTest {
    
    @Test
    public void testDefaultConstructor() {
        VendingMachineException exception = new VendingMachineException();

        assertNotNull(exception);
    }

    @Test
    public void testConstructorWithMessage() {
        VendingMachineException exception = new VendingMachineException("Something went wrong");

        assertEquals("Something went wrong", exception.getMessage());
    }
}


