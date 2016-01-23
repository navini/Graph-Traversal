package roadgraph;

import java.util.Comparator;

public class IntersectionComparator implements Comparator<Intersection>{
	    
    public int compare(Intersection x, Intersection y){
        // Assume neither string is null. Real code should
        // probably be more robust
        // You could also just return x.length() - y.length(),
        // which would be more efficient.
        if (x.getHeuristic() < y.getHeuristic()) {
            return -1;
        }
        if (x.getHeuristic() > y.getHeuristic()) {
            return 1;
        }
        return 0;
    }
}
