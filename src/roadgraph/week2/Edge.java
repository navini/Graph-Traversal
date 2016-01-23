package roadgraph;

public class Edge {
	
	private Intersection end;
	private String name = "Not specified", type = "Not specified";
	private double length;
	
	public Edge(Intersection dest, String name, String type, double length) {
		this.setEnd(dest);
		this.setName(name);
		this.setType(type);
		this.setLength(length);
	}
	
	public Edge(Intersection dest, double length) {
		this.setEnd(dest);
		this.setLength(length);
	}

	public Intersection getEnd() {
		return end;
	}

	public void setEnd(Intersection end) {
		this.end = end;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public double getLength() {
		return length;
	}

	public void setLength(double length) {
		this.length = length;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	

}
