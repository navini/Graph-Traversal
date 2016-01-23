package roadgraph;

import java.util.*;
import geography.GeographicPoint;

/**
 * @author NavD
 * 
 * A class which represents an intersection(node)
 * in a MapGraph
 *
 */
public class Intersection {
	
	private GeographicPoint location;
	private Set<Intersection> neighbors;
	/**
	 * Create a new intersection
	 */
	public Intersection(GeographicPoint location) {
		this.setLocation(location);
		setNeighbors(new HashSet<Intersection>());
	}
	
	public void addNeighbor(Intersection neighbor) {
		this.neighbors.add(neighbor);
	}

	public GeographicPoint getLocation() {
		return location;
	}

	public void setLocation(GeographicPoint location) {
		this.location = location;
	}

	public Set<Intersection> getNeighbors() {
		return neighbors;
	}

	public void setNeighbors(Set<Intersection> neighbors) {
		this.neighbors = neighbors;
	}

}
