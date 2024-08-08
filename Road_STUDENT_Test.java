import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class Road_STUDENT_Test {
    private Town town1, town2, town3;
    private Road road1, road2, road3, road4;

    @Before
    public void setUp() {
        town1 = new Town("Town1");
        town2 = new Town("Town2");
        town3 = new Town("Town3");

        road1 = new Road(town1, town2, 10, "Road1");
        road2 = new Road(town2, town3, 20, "Road2");
        road3 = new Road(town1, town2, 10, "Road1"); // Same as road1
        road4 = new Road(town2, town1, 10, "Road1"); // Same as road1 but reversed
    }

    @Test
    public void testConstructorAndGetters() {
        assertEquals(town1, road1.getSource());
        assertEquals(town2, road1.getDestination());
        assertEquals(10, road1.getWeight());
        assertEquals("Road1", road1.getName());
    }

    @Test
    public void testCompareTo() {
        assertTrue(road1.compareTo(road2) < 0); // "Road1" < "Road2" lexicographically
        assertTrue(road2.compareTo(road1) > 0); // "Road2" > "Road1"
        assertEquals(0, road1.compareTo(road3)); // road1 and road3 are equal
    }

    @Test
    public void testContains() {
        assertTrue(road1.contains(town1));
        assertTrue(road1.contains(town2));
        assertFalse(road1.contains(town3));
    }

    @Test
    public void testEquals() {
        assertTrue(road1.equals(road3)); // Same source, destination, weight, and name
        assertTrue(road1.equals(road4)); // Same source and destination but in reversed order
        assertFalse(road1.equals(road2)); // Different weight and name
        assertFalse(road1.equals(null)); // Comparison with null
        assertFalse(road1.equals("String")); // Comparison with different type
    }

    @Test
    public void testHashCode() {
        assertEquals(road1.hashCode(), road3.hashCode()); // Same source, destination, weight, and name
        assertNotEquals(road1.hashCode(), road2.hashCode()); // Different weight and name
    }

    @Test
    public void testToString() {
        assertEquals("Road1[Town1 to Town2, 10 miles]", road1.toString());
        assertEquals("Road2[Town2 to Town3, 20 miles]", road2.toString());
    }
}
