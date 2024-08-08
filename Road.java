import java.util.Objects;

/**
 * Represents a road connecting two towns with a specific weight and name. Implements {@link Comparable}
 * to compare roads based on their names.
 * 
 * @author JLi
 */
public class Road implements Comparable<Road>{
	private Town source;
	private Town destination;
	private int weight;
	private String name;
	
	/**
     * Constructs a road with the specified source and destination towns, weight, and name.
     *
     * @param source the source town of the road
     * @param destination the destination town of the road
     * @param weight the weight of the road
     * @param name the name of the road
     */
	public Road(Town source, Town destination, int weight, String name) {
		this.source = source;
		this.destination = destination;
		this.weight = weight;
		this.name = name;
	}
	
	/**
     * Constructs a road with the specified source and destination towns and name. The weight is set to 1.
     *
     * @param source the source town of the road
     * @param destination the destination town of the road
     * @param name the name of the road
     */
	public Road(Town source, Town destination, String name) {
		this(source, destination, 1, name);
	}
	
	/**
     * Returns the source town of this road.
     *
     * @return the source town
     */
	public Town getSource() {
		return source;
	}
	
	/**
     * Returns the destination town of this road.
     *
     * @return the destination town
     */
	public Town getDestination() {
		return destination;
	}
	
	/**
     * Returns the weight of this road.
     *
     * @return the weight of the road
     */
	public int getWeight() {
		return weight;
	}
	
	/**
     * Returns the name of this road.
     *
     * @return the name of the road
     */
	public String getName() {
		return name;
	}
	
	/**
     * Compares this road with another road based on their names.
     *
     * @param o the road to be compared
     * @return a negative integer, zero, or a positive integer as this road's name is less than,
     *         equal to, or greater than the specified road's name
     */
	@Override 
	public int compareTo(Road o) {
		return this.name.compareTo(o.name);
	}
	
	/**
     * Checks if this road contains the specified town as either the source or destination.
     *
     * @param town the town to check
     * @return true if the road contains the specified town, false otherwise
     */
	public boolean contains(Town town) {
        return source.equals(town) || destination.equals(town);
    }
	
	/**
     * Indicates whether some other object is "equal to" this road.
     *
     * @param obj the object to compare with
     * @return true if this road is equal to the specified object
     */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		Road road = (Road) obj;
		return (source.equals(road.source) && destination.equals(road.destination)) ||
	               (source.equals(road.destination) && destination.equals(road.source));
	}
	
	/**
     * Returns a string representation of this road.
     *
     * @return a string describing the road
     */
	@Override
	public String toString() {
		return name + "[" + source + " to " + destination + ", " + weight + " miles]";
	}
	
	/**
     * Returns a hash code value for this road.
     *
     * @return a hash code value for this road
     */
	@Override
    public int hashCode() {
        return Objects.hash(source, destination, weight, name);
    }
}
