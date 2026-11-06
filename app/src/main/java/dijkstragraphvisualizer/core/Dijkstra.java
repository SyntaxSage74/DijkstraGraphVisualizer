package dijkstragraphvisualizer.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import dijkstragraphvisualizer.renderer.Node;

public class Dijkstra {
    private final Graph grafo;
    private final HashMap<Node, Prop> table = new HashMap<>();
    private final Node start, end;
	private Node last = null;      // nodo elaborato allo step precedente
	private boolean finished = false;

    public Dijkstra(Graph grafo, Node start, Node end) {
        this.grafo = grafo;
        this.start = start;
        this.end = end;
        for (Node node : grafo.getNodes()) {
            table.put(node, new Prop());
        }
        table.get(start).setDist(0);
    }

    private Node getShorterDist() {
        Node temp = null;
        int distMin = Integer.MAX_VALUE;
        for (Entry<Node, Prop> e : table.entrySet()) {
            Prop prop = e.getValue();
            if (!prop.isVisited() && prop.getDist() < distMin) {
                temp = e.getKey();
                distMin = prop.getDist();
            }
        }
        return temp;
    }

	/** Esegue un passo. Restituisce false quando l'algoritmo è terminato. */
	public boolean step() {
		if (finished) return false;

		// il nodo dello step precedente diventa "visitato"
		if (last != null) last.setStatus(Node.Status.VISITED);

		Node attuale = getShorterDist();
		if (attuale == null) {            // nessun nodo raggiungibile rimasto
			finished = true;
			return false;
		}

		Prop p = table.get(attuale);
		p.setVisited(true);
		attuale.setStatus(Node.Status.CURRENT);
		last = attuale;

		if (attuale == end) {
			finished = true;
			for (Node n : ricostruisci()) n.setStatus(Node.Status.PATH);
			return false;
		}

		for (Entry<Node, Integer> arco : attuale.getAdjacent().entrySet()) {
			Node vicino = arco.getKey();
			Prop pv = table.get(vicino);
			if (pv == null || pv.isVisited()) continue;

			int nuovaDist = p.getDist() + arco.getValue();
			if (nuovaDist < pv.getDist()) {
				pv.setDist(nuovaDist);
				pv.setPrev(attuale);
				vicino.setStatus(Node.Status.FRONTIER);
			}
		}
		return true;
	}

	public boolean isFinished() { return finished; }

	public void reset() {
		for (Node n : grafo.getNodes()) n.setStatus(Node.Status.IDLE);
	}

	public List<Node> percorso() {
		while (step()) { }
		return ricostruisci();
	}

    private List<Node> ricostruisci() {
        List<Node> path = new ArrayList<>();
        if (table.get(end).getDist() == Integer.MAX_VALUE) {
            return path; // end non raggiungibile
        }
        for (Node n = end; n != null; n = table.get(n).getPrev()) {
            path.add(n);
        }
        Collections.reverse(path);
        return path;
    }
}