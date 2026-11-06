package dijkstragraphvisualizer.core;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import dijkstragraphvisualizer.renderer.Arc;
import dijkstragraphvisualizer.renderer.Node;

public class Graph {
    private Set<Node> nodes = new HashSet<>();
	private Set<Arc> arcs = new HashSet<>();
    private final Map<Node, Integer> adjacent = new HashMap<>();
	
	public void insertNode(Node node) {
		nodes.add(node);
	}
	
	public void insertArc(Arc arc) {
        Node nodeA = arc.getNodeA();
        Node nodeB = arc.getNodeB();
        int dist = arc.getDist();

		nodeA.insertAdjacent(nodeB, dist);
		nodeB.insertAdjacent(nodeA, dist);
		arcs.add(new Arc(nodeA, nodeB, dist));
	}

    public void insertAdjacent(Node other, int dist) {
        adjacent.put(other, dist);
    }

    public Map<Node, Integer> getAdjacent() {
        return adjacent;
    }
	
	public Set<Node> getNodes() {
		return nodes;
	}
	
	public Set<Arc> getArcs() {
		return arcs;
	}
}
