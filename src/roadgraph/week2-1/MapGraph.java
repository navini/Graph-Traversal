/**
 * @author UCSD MOOC development team and NavD
 * 
 * A class which reprsents a graph of geographic locations
 * Nodes in the graph are intersections between 
 *
 */
package roadgraph;


import java.util.*;
import java.util.function.Consumer;
import geography.GeographicPoint;
import util.GraphLoader;

/**
 * @author UCSD MOOC development team and YOU
 * 
 * A class which represents a graph of geographic locations
 * Nodes in the graph are intersections between 
 *
 */
public class MapGraph {
	
	private Map<GeographicPoint, Intersection> intersectionsByLocation;
	private int numOfStreets = 0;
	/** 
	 * Create a new empty MapGraph 
	 */
	public MapGraph()
	{
		//Maps a GeographicPoint object to the Intersection object at that point
		intersectionsByLocation = new HashMap<GeographicPoint, Intersection>();
	}
	
	/**
	 * Get the number of vertices (road intersections) in the graph
	 * @return The number of vertices in the graph.
	 */
	public int getNumVertices()
	{
		return intersectionsByLocation.size();
	}
	
	/**
	 * Return the intersections, which are the vertices in this graph.
	 * @return The vertices in this graph as GeographicPoints
	 */
	public Set<GeographicPoint> getVertices()
	{
		return new HashSet(intersectionsByLocation.keySet());
	}
	
	/**
	 * Get the number of road segments in the graph
	 * @return The number of edges in the graph.
	 */
	public int getNumEdges()
	{
		return numOfStreets;
	}

	
	
	/** Add a node corresponding to an intersection at a Geographic Point
	 * If the location is already in the graph or null, this method does 
	 * not change the graph.
	 * @param location  The location of the intersection
	 * @return true if a node was added, false if it was not (the node
	 * was already in the graph, or the parameter is null).
	 */
	public boolean addVertex(GeographicPoint location)
	{
		//If the location is already added to the graph or is null, do not add to graph
		if(intersectionsByLocation.containsKey(location) || location == null) {
			return false;
		}
		//Create a new Intersection for the location and add it to the graph
		Intersection newIntersection = new Intersection(location);
		intersectionsByLocation.put(location, newIntersection);
		return true;
	}
	
	/**
	 * Adds a directed edge to the graph from pt1 to pt2.  
	 * Precondition: Both GeographicPoints have already been added to the graph
	 * @param from The starting point of the edge
	 * @param to The ending point of the edge
	 * @param roadName The name of the road
	 * @param roadType The type of the road
	 * @param length The length of the road, in km
	 * @throws IllegalArgumentException If the points have not already been
	 *   added as nodes to the graph, if any of the arguments is null,
	 *   or if the length is less than 0.
	 */
	public void addEdge(GeographicPoint from, GeographicPoint to, String roadName,
			String roadType, double length) throws IllegalArgumentException {
		if(length > 0) {
			if(intersectionsByLocation.containsKey(from)) {
				if(intersectionsByLocation.containsKey(to)) {
					//Increment the number of edges in the graph and add the 'to' Intersection as a neighbor for the 'from' Intersection
					numOfStreets++;
					intersectionsByLocation.get(from).addNeighbor(intersectionsByLocation.get(to));
				} else {
					throw new IllegalArgumentException("'to' location does not exist in the graph");
				}
			} else {
				throw new IllegalArgumentException("'from' location does not exist in the graph");
			}
		} else {
			throw new IllegalArgumentException("Street length should be greater than zero");
		}
	}
	

	/** Find the path from start to goal using breadth first search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest (unweighted)
	 *   path from start to goal (including both start and goal).
	 */
	public List<GeographicPoint> bfs(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
        Consumer<GeographicPoint> temp = (x) -> {};
        return bfs(start, goal, temp);
	}
	
	/** Find the path from start to goal using breadth first search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest (unweighted)
	 *   path from start to goal (including both start and goal).
	 */
	public List<GeographicPoint> bfs(GeographicPoint start, 
			 					     GeographicPoint goal, Consumer<GeographicPoint> nodeSearched)
	{
		//Get the start and goal intersections from the map
		Intersection startIntersection = intersectionsByLocation.get(start);
		Intersection endIntersection = intersectionsByLocation.get(goal);
	    
		//parentMap: Key:child, Value:parent
		Map<Intersection, Intersection> parentMap = new HashMap<Intersection, Intersection>();
	    Set<Intersection> visitedSet = new HashSet<Intersection>();
	    Queue<Intersection> exploringIntersections = new LinkedList<Intersection>();
	    
	    //Add the starting intersection to the visited set and queue
	    exploringIntersections.add(startIntersection);
	    visitedSet.add(startIntersection);
	    
	    Intersection current;
	    
	    while(exploringIntersections.size() > 0) {
	    	//Remove and retrieve the last element from the queue
	    	current = exploringIntersections.poll();
	    	if(current.equals(endIntersection)) {
	    		return getPath(parentMap, startIntersection, endIntersection);
	    	} else {
	    		//Hook for visualization
	    		nodeSearched.accept(current.getLocation());
	    		//Add unvisited neighbors of the current node to the queue, visited set and parent map
	    		for(Intersection i : current.getNeighbors()) {
	    			if(!visitedSet.contains(i)) {
	    				visitedSet.add(i);
	    				exploringIntersections.add(i);
	    				parentMap.put(i, current);
	    			}
	    		}
	    	}
	    }
	    //If reached here, no path was found
		return null;
	}
	

	private List<GeographicPoint> getPath(Map<Intersection, Intersection> parentMap, Intersection start, Intersection end) {
		LinkedList<GeographicPoint> path = new LinkedList<GeographicPoint>();
		Intersection current = end;
		//Backtrack the parentMap till the start intersection is found
		while(!current.equals(start)) {
			path.addFirst(current.getLocation());
			if(parentMap.containsKey(current)) {
				current = parentMap.get(current); //assign parent of current to current
			}
		}
		path.addFirst(start.getLocation());
		return path;
	}

	/** Find the path from start to goal using Dijkstra's algorithm
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> dijkstra(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
		// You do not need to change this method.
        Consumer<GeographicPoint> temp = (x) -> {};
        return dijkstra(start, goal, temp);
	}
	
	/** Find the path from start to goal using Dijkstra's algorithm
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> dijkstra(GeographicPoint start, 
										  GeographicPoint goal, Consumer<GeographicPoint> nodeSearched)
	{
		// TODO: Implement this method in WEEK 3

		// Hook for visualization.  See writeup.
		//nodeSearched.accept(next.getLocation());
		
		return null;
	}

	/** Find the path from start to goal using A-Star search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> aStarSearch(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
        Consumer<GeographicPoint> temp = (x) -> {};
        return aStarSearch(start, goal, temp);
	}
	
	/** Find the path from start to goal using A-Star search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> aStarSearch(GeographicPoint start, 
											 GeographicPoint goal, Consumer<GeographicPoint> nodeSearched)
	{
		// TODO: Implement this method in WEEK 3
		
		// Hook for visualization.  See writeup.
		//nodeSearched.accept(next.getLocation());
		
		return null;
	}

	
	
	public static void main(String[] args)
	{
		System.out.print("Making a new map...");
		MapGraph theMap = new MapGraph();
		System.out.print("DONE. \nLoading the map...");
		GraphLoader.loadRoadMap("data/testdata/simpletest.map", theMap);
		System.out.println("DONE.");
		
		// You can use this method for testing.  
		
		/* Use this code in Week 3 End of Week Quiz
		MapGraph theMap = new MapGraph();
		System.out.print("DONE. \nLoading the map...");
		GraphLoader.loadRoadMap("data/maps/utc.map", theMap);
		System.out.println("DONE.");

		GeographicPoint start = new GeographicPoint(32.8648772, -117.2254046);
		GeographicPoint end = new GeographicPoint(32.8660691, -117.217393);
		
		
		List<GeographicPoint> route = theMap.dijkstra(start,end);
		List<GeographicPoint> route2 = theMap.aStarSearch(start,end);

		*/
		
	}
	
}
