import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class Town_STUDENT_Test {
    private Town town1;
    private Town town2;
    private Town town3;

    @Before
    public void setUp() {
        town1 = new Town("Springfield");
        town2 = new Town("Shelbyville");
        town3 = new Town("Springfield"); // Same name as town1
    }

    @Test
    public void testConstructor() {
        assertNotNull(town1);
        assertEquals("Springfield", town1.getName());
    }

    @Test
    public void testCopyConstructor() {
        Town copiedTown = new Town(town1);
        assertEquals(town1.getName(), copiedTown.getName());
    }

    @Test
    public void testGetName() {
        assertEquals("Springfield", town1.getName());
    }

    @Test
    public void testSetName() {
        town1.setName("NewTown");
        assertEquals("NewTown", town1.getName());
    }

    @Test
    public void testCompareTo() {
        assertTrue(town1.compareTo(town2) > 0); // "Springfield" > "Shelbyville"
        assertTrue(town2.compareTo(town1) < 0); // "Shelbyville" < "Springfield"
        assertEquals(0, town1.compareTo(town3)); // Same name
    }

    @Test
    public void testEquals() {
        assertTrue(town1.equals(town3)); // Same name
        assertFalse(town1.equals(town2)); // Different names
        assertFalse(town1.equals(null)); // Comparing with null
        assertFalse(town1.equals(new Object())); // Comparing with a different class
    }

    @Test
    public void testHashCode() {
        assertEquals(town1.hashCode(), town3.hashCode()); // Same name
        assertNotEquals(town1.hashCode(), town2.hashCode()); // Different names
    }

    @Test
    public void testToString() {
        assertEquals("Springfield", town1.toString());
    }
}
