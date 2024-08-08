import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class TownGraphManager_STUDENT_Test {
    private TownGraphManager manager;

    @Before
    public void setUp() {
        manager = new TownGraphManager();
    }

    @Test
    public void testAddTown() {
        assertTrue(manager.addTown("Chicago"));
        assertFalse(manager.addTown("Chicago")); // Town already exists
    }

    @Test
    public void testAddRoad() {
        manager.addTown("Chicago");
        manager.addTown("Detroit");
        assertTrue(manager.addRoad("Chicago", "Detroit", 282, "I-94"));
    }

    @Test
    public void testGetRoad() {
        manager.addTown("Chicago");
        manager.addTown("Detroit");
        manager.addRoad("Chicago", "Detroit", 282, "I-94");
        assertEquals("I-94", manager.getRoad("Chicago", "Detroit"));
    }

    @Test
    public void testContainsTown() {
        manager.addTown("Chicago");
        assertTrue(manager.containsTown("Chicago"));
        assertFalse(manager.containsTown("Detroit"));
    }

    @Test
    public void testContainsRoadConnection() {
        manager.addTown("Chicago");
        manager.addTown("Detroit");
        manager.addRoad("Chicago", "Detroit", 282, "I-94");
        assertTrue(manager.containsRoadConnection("Chicago", "Detroit"));
    }

    @Test
    public void testAllRoads() {
        manager.addTown("Chicago");
        manager.addTown("Detroit");
        manager.addRoad("Chicago", "Detroit", 282, "I-94");
        ArrayList<String> roads = manager.allRoads();
        assertEquals(1, roads.size());
        assertEquals("I-94", roads.get(0));
    }

    @Test
    public void testDeleteRoadConnection() {
        manager.addTown("Chicago");
        manager.addTown("Detroit");
        manager.addRoad("Chicago", "Detroit", 282, "I-94");
        assertTrue(manager.deleteRoadConnection("Chicago", "Detroit", "I-94"));
        assertFalse(manager.containsRoadConnection("Chicago", "Detroit"));
    }

    @Test
    public void testDeleteTown() {
        manager.addTown("Chicago");
        assertTrue(manager.deleteTown("Chicago"));
        assertFalse(manager.containsTown("Chicago"));
    }

    @Test
    public void testAllTowns() {
        manager.addTown("Chicago");
        manager.addTown("Detroit");
        ArrayList<String> towns = manager.allTowns();
        assertEquals(2, towns.size());
        assertTrue(towns.contains("Chicago"));
        assertTrue(towns.contains("Detroit"));
    }

    @Test
    public void testGetPath() {
        manager.addTown("Chicago");
        manager.addTown("Detroit");
        manager.addRoad("Chicago", "Detroit", 282, "I-94");
        ArrayList<String> path = manager.getPath("Chicago", "Detroit");
        assertNotNull(path);
        assertEquals(1, path.size());
        assertEquals("Chicago via I-94 to Detroit 282 mi", path.get(0));
    }

}
