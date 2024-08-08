import java.util.Objects;

/**
 * Represents a town with a name. Implements {@link Comparable<Town>} to allow comparison based on the town's name.
 * 
 * @author JLi
 */
public class Town implements Comparable<Town> {
	private String name;
	
	/**
     * Constructs a new Town with the specified name.
     *
     * @param name the name of the town
     */
	public Town(String name) {
		this.name = name;
	}
	
	/**
     * Constructs a new Town as a copy of the specified town.
     *
     * @param templateTown the town to copy
     */
	public Town(Town templateTown) {
		this.name = templateTown.name;
	}
	
	/**
     * Retrieves the name of the town.
     *
     * @return the name of the town
     */
	public String getName() {
		return name;
	}
	
	/**
     * Sets the name of the town.
     *
     * @param name the new name of the town
     */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
     * Compares this town to another town based on their names.
     *
     * @param o the town to compare to
     * @return a negative integer, zero, or a positive integer as this town's name is less than, equal to, or greater than the specified town's name
     * @throws NullPointerException if the specified town is null
     */
	public int compareTo(Town o) {
		if (o == null) {
	        throw new NullPointerException("The object to compare to is null");
	    }
	    return this.name.compareTo(o.name);
	}
	
	/**
     * Checks if this town is equal to another object. Two towns are considered equal if their names are equal.
     *
     * @param obj the object to compare to
     * @return true if this town is equal to the specified object, false otherwise
     */
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		Town town = (Town) obj;
		return Objects.equals(name, town.name);
	}
	
	/**
     * Returns the hash code of this town. The hash code is based on the town's name.
     *
     * @return the hash code of this town
     */
	@Override
	public int hashCode() {
		return Objects.hash(name);
	}
	
	/**
     * Returns a string representation of this town.
     *
     * @return the name of the town
     */
	@Override
	public String toString() {
		return name;
	}
}
