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
	private Set<Edge> edges;
	/**
	 * Create a new intersection
	 */
	public Intersection(GeographicPoint location) {
		this.setLocation(location);
		setEdges(new HashSet<Edge>());
	}
	
	public void addEdge(Edge e) {
		this.edges.add(e);
	}

	public GeographicPoint getLocation() {
		return new GeographicPoint(location.getX(), location.getY());
	}

	public void setLocation(GeographicPoint location) {
		this.location = location;
	}

	public Set<Edge> getEdges() {
		return new HashSet<Edge>(edges);
	}

	private void setEdges(Set<Edge> edges) {
		this.edges = edges;
	}

	@Override
	public boolean equals(Object obj) {
		Intersection i = (Intersection)obj;
		if(i.getLocation().getX() == location.getX() && i.getLocation().getY() == location.getY()) {
			return true;
		}
		return false;
	}
	
	

}
