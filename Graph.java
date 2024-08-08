import java.util.*;

/**
 * Represents a graph of towns connected by roads. Implements the {@link GraphInterface} interface
 * for managing vertices (towns) and edges (roads) in the graph.
 * 
 * @author JLi
 */
public class Graph implements GraphInterface<Town, Road> {
    private Map<Town, Set<Road>> adjacencyMap;

    /**
     * Constructs an empty graph.
     */
    public Graph() {
        adjacencyMap = new HashMap<>();
    }

    /**
     * Returns the edge between the specified source and destination vertices, or null if no such edge exists.
     *
     * @param sourceVertex the source vertex of the edge
     * @param destinationVertex the destination vertex of the edge
     * @return the road connecting the source and destination vertices, or null if not found
     */
    @Override
    public Road getEdge(Town sourceVertex, Town destinationVertex) {
        if (sourceVertex == null || destinationVertex == null) {
        	return null;
        }
        Set<Road> roads = adjacencyMap.get(sourceVertex);
        if (roads != null) {
            for (Road road : roads) {
                if (road.contains(destinationVertex)) {
                    return road;
                }
            }
        }
        return null;
    }

    /**
     * Adds an edge between the specified source and destination vertices with the given weight and description.
     *
     * @param sourceVertex the source vertex of the edge
     * @param destinationVertex the destination vertex of the edge
     * @param weight the weight of the edge
     * @param description the description of the edge
     * @return the road that was added
     * @throws IllegalArgumentException if either vertex is not present in the graph
     * @throws NullPointerException if either vertex is null
     */
    @Override
    public Road addEdge(Town sourceVertex, Town destinationVertex, int weight, String description) {
        if (!containsVertex(sourceVertex) || !containsVertex(destinationVertex)) {
            throw new IllegalArgumentException("Source or destination vertex not found in the graph");
        }
        if (sourceVertex == null || destinationVertex == null) {
            throw new NullPointerException("Source or destination vertex is null");
        }

        Road newRoad = new Road(sourceVertex, destinationVertex, weight, description);
        adjacencyMap.get(sourceVertex).add(newRoad);
        adjacencyMap.get(destinationVertex).add(newRoad);

        return newRoad;
    }

    /**
     * Adds a vertex to the graph.
     *
     * @param v the vertex to add
     * @return true if the vertex was added, false if it was already present
     * @throws NullPointerException if the vertex is null
     */
    @Override
    public boolean addVertex(Town v) {
        if (v == null) throw new NullPointerException("Vertex is null");
        if (containsVertex(v)) return false;
        adjacencyMap.put(v, new HashSet<>());
        return true;
    }

    /**
     * Checks if there is an edge between the specified source and destination vertices.
     *
     * @param sourceVertex the source vertex of the edge
     * @param destinationVertex the destination vertex of the edge
     * @return true if there is an edge between the vertices, false otherwise
     */
    @Override
    public boolean containsEdge(Town sourceVertex, Town destinationVertex) {
        if (sourceVertex == null || destinationVertex == null) return false;
        return getEdge(sourceVertex, destinationVertex) != null;
    }

    /**
     * Checks if the specified vertex is present in the graph.
     *
     * @param v the vertex to check
     * @return true if the vertex is present, false otherwise
     */
    @Override
    public boolean containsVertex(Town v) {
        return v != null && adjacencyMap.containsKey(v);
    }

    /**
     * Returns a set of all edges in the graph.
     *
     * @return a set of all roads in the graph
     */
    @Override
    public Set<Road> edgeSet() {
        Set<Road> allEdges = new HashSet<>();
        for (Set<Road> roads : adjacencyMap.values()) {
            allEdges.addAll(roads);
        }
        return allEdges;
    }

    /**
     * Returns a set of all edges connected to the specified vertex.
     *
     * @param vertex the vertex whose edges to return
     * @return a set of all roads connected to the vertex
     * @throws NullPointerException if the vertex is null
     * @throws IllegalArgumentException if the vertex is not present in the graph
     */
    @Override
    public Set<Road> edgesOf(Town vertex) {
        if (vertex == null) throw new NullPointerException("Vertex is null");
        if (!containsVertex(vertex)) throw new IllegalArgumentException("Vertex not found in the graph");
        return adjacencyMap.get(vertex);
    }

    /**
     * Removes the edge between the specified source and destination vertices with the given weight and description.
     *
     * @param sourceVertex the source vertex of the edge
     * @param destinationVertex the destination vertex of the edge
     * @param weight the weight of the edge
     * @param description the description of the edge
     * @return the removed road, or null if no such edge exists
     */
    @Override
    public Road removeEdge(Town sourceVertex, Town destinationVertex, int weight, String description) {
        Road roadToRemove = getEdge(sourceVertex, destinationVertex);
        if (roadToRemove != null && roadToRemove.getWeight() == weight && 
            (description == null || roadToRemove.getName().equals(description))) {
            adjacencyMap.get(sourceVertex).remove(roadToRemove);
            adjacencyMap.get(destinationVertex).remove(roadToRemove);
            return roadToRemove;
        }
        return null;
    }

    /**
     * Removes the specified vertex and all edges connected to it from the graph.
     *
     * @param v the vertex to remove
     * @return true if the vertex was removed, false otherwise
     */
    @Override
    public boolean removeVertex(Town v) {
        if (!containsVertex(v)) return false;
        for (Road road : adjacencyMap.get(v)) {
            Town oppositeTown = road.getSource().equals(v) ? road.getDestination() : road.getSource();
            adjacencyMap.get(oppositeTown).remove(road);
        }
        adjacencyMap.remove(v);
        return true;
    }

    /**
     * Returns a set of all vertices in the graph.
     *
     * @return a set of all towns in the graph
     */
    @Override
    public Set<Town> vertexSet() {
        return adjacencyMap.keySet();
    }

    /**
     * Returns a list of strings representing the shortest path from the source vertex to the destination vertex.
     *
     * @param sourceVertex the starting vertex of the path
     * @param destinationVertex the ending vertex of the path
     * @return a list of strings describing the path from the source to the destination
     */
    @Override
    public ArrayList<String> shortestPath(Town sourceVertex, Town destinationVertex) {
        dijkstraShortestPath(sourceVertex);

        ArrayList<String> path = new ArrayList<>();
        Town current = destinationVertex;
        while (current != null && !current.equals(sourceVertex)) {
            Town previous = previousTowns.get(current);
            if (previous == null) break;
            Road road = getEdge(previous, current);
            path.add(previous + " via " + road.getName() + " to " + current + " " + road.getWeight() + " mi");
            current = previous;
        }

        Collections.reverse(path);
        return path;
    }

    private Map<Town, Integer> distances;
    private Map<Town, Town> previousTowns;

    /**
     * Computes the shortest paths from the specified source vertex to all other vertices using Dijkstra's algorithm.
     *
     * @param sourceVertex the starting vertex for the shortest path calculation
     */
    @Override
    public void dijkstraShortestPath(Town sourceVertex) {
        distances = new HashMap<>();
        previousTowns = new HashMap<>();
        PriorityQueue<Town> pq = new PriorityQueue<>(Comparator.comparingInt(distances::get));

        for (Town town : vertexSet()) {
            if (town.equals(sourceVertex)) {
                distances.put(town, 0);
            } else {
                distances.put(town, Integer.MAX_VALUE);
            }
            pq.add(town);
        }

        while (!pq.isEmpty()) {
            Town current = pq.poll();
            for (Road road : edgesOf(current)) {
                Town neighbor = road.getDestination().equals(current) ? road.getSource() : road.getDestination();
                int newDist = distances.get(current) + road.getWeight();
                if (newDist < distances.get(neighbor)) {
                    pq.remove(neighbor);
                    distances.put(neighbor, newDist);
                    previousTowns.put(neighbor, current);
                    pq.add(neighbor);
                }
            }
        }
    }
}
