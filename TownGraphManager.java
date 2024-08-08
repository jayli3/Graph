import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

/**
 * Manages a graph of towns and roads. Provides functionality to add, remove, and query towns and roads,
 * and to read graph data from a file. Implements {@link TownGraphManagerInterface}.
 * 
 * @author JLi
 */
public class TownGraphManager implements TownGraphManagerInterface {
    private Graph graph;
    private Map<String, Town> towns;
    
    /**
     * Constructs a new TownGraphManager with an empty graph and town map.
     */
    public TownGraphManager() {
        graph = new Graph();
        towns = new HashMap<>();
    }

    /**
     * Adds a road between two towns with a specified weight and name.
     *
     * @param town1 the name of the source town
     * @param town2 the name of the destination town
     * @param weight the weight of the road
     * @param roadName the name of the road
     * @return true if the road was added successfully, false otherwise
     */
    @Override
    public boolean addRoad(String town1, String town2, int weight, String roadName) {
        Town t1 = getOrCreateTown(town1);
        Town t2 = getOrCreateTown(town2);
        return graph.addEdge(t1, t2, weight, roadName) != null;
    }

    /**
     * Retrieves the name of the road connecting two towns.
     *
     * @param town1 the name of the source town
     * @param town2 the name of the destination town
     * @return the name of the road connecting the two towns, or null if no such road exists
     */
    @Override
    public String getRoad(String town1, String town2) {
        Town t1 = towns.get(town1);
        Town t2 = towns.get(town2);
        Road road = graph.getEdge(t1, t2);
        return road != null ? road.getName() : null;
    }

    /**
     * Adds a town with the specified name to the graph.
     *
     * @param v the name of the town to add
     * @return true if the town was added successfully, false if the town already exists
     */
    @Override
    public boolean addTown(String v) {
        if (towns.containsKey(v)) return false;
        Town town = new Town(v);
        towns.put(v, town);
        return graph.addVertex(town);
    }

    /**
     * Retrieves a town by its name.
     *
     * @param name the name of the town
     * @return the Town object with the specified name, or null if no such town exists
     */
    @Override
    public Town getTown(String name) {
        return towns.get(name);
    }
    
    /**
     * Checks if a town with the specified name exists in the graph.
     *
     * @param v the name of the town
     * @return true if the town exists, false otherwise
     */
    @Override
    public boolean containsTown(String v) {
        return towns.containsKey(v);
    }

    /**
     * Checks if there is a road connection between two towns.
     *
     * @param town1 the name of the source town
     * @param town2 the name of the destination town
     * @return true if there is a road connecting the two towns, false otherwise
     */
    @Override
    public boolean containsRoadConnection(String town1, String town2) {
        Town t1 = towns.get(town1);
        Town t2 = towns.get(town2);
        return graph.containsEdge(t1, t2);
    }

    /**
     * Returns a list of all road names in the graph, sorted alphabetically.
     *
     * @return an ArrayList containing the names of all roads
     */
    @Override
    public ArrayList<String> allRoads() {
        ArrayList<String> roadNames = new ArrayList<>();
        for (Road road : graph.edgeSet()) {
            roadNames.add(road.getName());
        }
        Collections.sort(roadNames);
        return roadNames;
    }

    /**
     * Deletes a road connection between two towns.
     *
     * @param town1 the name of the source town
     * @param town2 the name of the destination town
     * @param roadName the name of the road to delete
     * @return true if the road was deleted successfully, false otherwise
     */
    @Override
    public boolean deleteRoadConnection(String town1, String town2, String roadName) {
        Town t1 = towns.get(town1);
        Town t2 = towns.get(town2);
        Road road = graph.getEdge(t1, t2);
        if (road != null && road.getName().equals(roadName)) {
            return graph.removeEdge(t1, t2, road.getWeight(), roadName) != null;
        }
        return false;
    }

    /**
     * Deletes a town and its associated roads from the graph.
     *
     * @param v the name of the town to delete
     * @return true if the town was deleted successfully, false otherwise
     */
    @Override
    public boolean deleteTown(String v) {
        Town town = towns.remove(v);
        if (town != null) {
            return graph.removeVertex(town);
        }
        return false;
    }

    /**
     * Returns a list of all town names in the graph, sorted alphabetically.
     *
     * @return an ArrayList containing the names of all towns
     */
    @Override
    public ArrayList<String> allTowns() {
        ArrayList<String> townNames = new ArrayList<>(towns.keySet());
        Collections.sort(townNames);
        return townNames;
    }

    /**
     * Retrieves the shortest path between two towns as a list of road descriptions.
     *
     * @param town1 the name of the source town
     * @param town2 the name of the destination town
     * @return an ArrayList of strings describing the path from town1 to town2, or null if no path exists
     */
    @Override
    public ArrayList<String> getPath(String town1, String town2) {
        Town t1 = towns.get(town1);
        Town t2 = towns.get(town2);
        if (t1 == null || t2 == null) return null;
        return graph.shortestPath(t1, t2);
    }

    /**
     * Retrieves or creates a town with the specified name.
     *
     * @param name the name of the town
     * @return the Town object with the specified name
     */
    private Town getOrCreateTown(String name) {
        return towns.computeIfAbsent(name, Town::new);
    }
    
    /**
     * Populates the graph with towns and roads from the specified file. The file should contain lines in the format:
     * "roadName,miles;town1;town2".
     *
     * @param file the file containing town and road data
     * @throws FileNotFoundException if the specified file is not found
     * @throws IOException if an I/O error occurs while reading the file
     */
    public void populateTownGraph(File file) throws FileNotFoundException, IOException {
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.split(";");
            if (parts.length == 3) {
                String[] roadParts = parts[0].split(",");
                String roadName = roadParts[0];
                int miles = Integer.parseInt(roadParts[1]);
                String town1 = parts[1];
                String town2 = parts[2];

                addTown(town1);
                addTown(town2);
                addRoad(town1, town2, miles, roadName);
            }
        }
        scanner.close();
    }
}
