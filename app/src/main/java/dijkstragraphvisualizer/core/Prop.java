package dijkstragraphvisualizer.core;

import dijkstragraphvisualizer.renderer.Node;

public class Prop {
    private Node previous;
	private int dist;
	private boolean visited;

	public Node getPrev() {
		return previous;
	}

    public void setPrev(Node node) {
		this.previous = node;
	}

	public int getDist() {
		return dist;
	}

	public boolean isVisited() {
		return visited;
	}

	public void setDist(int dist) {
		this.dist = dist;
	}

	public void setVisited(boolean visited) {
		this.visited = visited;
	}
}
