import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.*;

public class Graph_STUDENT_Test {
    private Graph graph;
    private Town town1, town2, town3;
    private Road road1, road2;

    @Before
    public void setUp() {
        graph = new Graph();
        town1 = new Town("Town1");
        town2 = new Town("Town2");
        town3 = new Town("Town3");

        graph.addVertex(town1);
        graph.addVertex(town2);
        graph.addVertex(town3);

        road1 = new Road(town1, town2, 10, "Road1");
        road2 = new Road(town2, town3, 20, "Road2");

        graph.addEdge(town1, town2, 10, "Road1");
        graph.addEdge(town2, town3, 20, "Road2");
    }

    @Test
    public void testAddVertex() {
        Town newTown = new Town("Town4");
        assertTrue(graph.addVertex(newTown));
        assertFalse(graph.addVertex(newTown)); // Should return false because Town4 already exists
    }

    @Test
    public void testContainsVertex() {
        assertTrue(graph.containsVertex(town1));
        assertFalse(graph.containsVertex(new Town("NonExistentTown")));
    }

    @Test
    public void testAddEdge() {
        Town town4 = new Town("Town4");
        graph.addVertex(town4);
        assertNotNull(graph.addEdge(town3, town4, 30, "Road3"));
    }

    @Test
    public void testGetEdge() {
        assertEquals(road1, graph.getEdge(town1, town2));
        assertNull(graph.getEdge(town1, town3)); // No direct road between town1 and town3
    }

    @Test
    public void testContainsEdge() {
        assertTrue(graph.containsEdge(town1, town2));
        assertFalse(graph.containsEdge(town1, town3)); // No edge between town1 and town3
    }

    @Test
    public void testEdgeSet() {
        Set<Road> expectedEdges = new HashSet<>(Arrays.asList(road1, road2));
        Set<Road> actualEdges = graph.edgeSet();
        assertEquals(expectedEdges, actualEdges);
    }

    @Test
    public void testEdgesOf() {
        Set<Road> expectedEdgesTown1 = new HashSet<>(Arrays.asList(road1));
        assertEquals(expectedEdgesTown1, graph.edgesOf(town1));
        
        Set<Road> expectedEdgesTown2 = new HashSet<>(Arrays.asList(road1, road2));
        assertEquals(expectedEdgesTown2, graph.edgesOf(town2));
        
        Set<Road> expectedEdgesTown3 = new HashSet<>(Arrays.asList(road2));
        assertEquals(expectedEdgesTown3, graph.edgesOf(town3));
    }

    @Test
    public void testRemoveEdge() {
        assertNotNull(graph.removeEdge(town1, town2, 10, "Road1"));
        assertNull(graph.removeEdge(town1, town2, 10, "NonExistentRoad")); // Should be null because the road doesn't exist
    }

    @Test
    public void testRemoveVertex() {
        assertTrue(graph.removeVertex(town3));
        assertFalse(graph.removeVertex(town3)); // Should return false because Town3 has already been removed
    }

    @Test
    public void testVertexSet() {
        Set<Town> expectedVertices = new HashSet<>(Arrays.asList(town1, town2, town3));
        assertEquals(expectedVertices, graph.vertexSet());
    }

    @Test
    public void testShortestPath() {
        ArrayList<String> expectedPath = new ArrayList<>(Arrays.asList(
                "Town1 via Road1 to Town2 10 mi",
                "Town2 via Road2 to Town3 20 mi"
        ));
        assertEquals(expectedPath, graph.shortestPath(town1, town3));
    }

    @Test
    public void testDijkstraShortestPath() {
        Town townA = new Town("A");
        Town townB = new Town("B");
        Town townC = new Town("C");
        Town townD = new Town("D");

        graph.addVertex(townA);
        graph.addVertex(townB);
        graph.addVertex(townC);
        graph.addVertex(townD);

        graph.addEdge(townA, townB, 1, "Road1");
        graph.addEdge(townB, townC, 2, "Road2");
        graph.addEdge(townA, townC, 4, "Road3");
        graph.addEdge(townC, townD, 1, "Road4");

        ArrayList<String> expectedPath = new ArrayList<>();
        expectedPath.add("A via Road1 to B 1 mi");
        expectedPath.add("B via Road2 to C 2 mi");
        expectedPath.add("C via Road4 to D 1 mi");

        ArrayList<String> actualPath = graph.shortestPath(townA, townD);

        assertNotNull(actualPath);  
        assertEquals(expectedPath, actualPath);
    }
}
