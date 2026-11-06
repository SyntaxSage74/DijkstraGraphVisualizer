package dijkstragraphvisualizer.renderer;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import javax.swing.AbstractAction;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

import dijkstragraphvisualizer.core.Dijkstra;
import dijkstragraphvisualizer.core.Graph;

public class DrawingPanel extends JPanel implements MouseListener, MouseMotionListener {
    private int nodeIdx;
    private int distIdx;
    private Node draggedNode;
    private Node startingNode;
    private Node endingNode;
    private Node pathStart, pathEnd;
    private Graph graph;
    private Dijkstra dijkstra;

    public DrawingPanel(Graph graph) {
        this.graph = graph;

        nodeIdx = 0;
        distIdx = 0;
        draggedNode = null;
        startingNode = null;
        endingNode = null;
        pathStart = null;
        pathEnd = null;

        addMouseListener(this);
        addMouseMotionListener(this);

        setFocusable(true);

        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("SPACE"), "step");
        getActionMap().put("step", new AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent ev) {
                if (dijkstra != null) {
                    dijkstra.step();
                    repaint();
                }
            }
        });

        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ESCAPE"), "reset");
        getActionMap().put("reset", new AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent ev) {
                resetAlgorithm();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    
        for(Arc arc : graph.getArcs()) {
            arc.draw(g2d);
        }

        for(Node node : graph.getNodes()) {
            node.draw(g2d);
        }
    }

    private boolean overlapsAny(int x, int y, Node ignored) {
        int minDistance = Node.DIAMETER + 2;
        for (Node other : graph.getNodes()) {
            if (other == ignored) {
                continue;
            }
            double dx = x - other.getX();
            double dy = y - other.getY();
            if (dx * dx + dy * dy < (double) minDistance * minDistance) {
                return true;
            }
        }
        return false;
    }

    private void updateHover(int mx, int my) {
        boolean changed = false;
        boolean found = false;
        for (Node node : graph.getNodes()) {
            boolean hover = !found && node.isSelected(mx, my);
            if (hover) {
                found = true;
            }
            if (node.isHovered() != hover) {
                node.setHovered(hover);
                changed = true;
            }
        }
        if (changed) {
            repaint();
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (!e.isControlDown()) return;

        if(draggedNode == null) return;

        int x = e.getX();
        int y = e.getY();

        if(overlapsAny(x, y, draggedNode)) return;

        draggedNode.setX(x);
        draggedNode.setY(y);
        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        updateHover(e.getX(), e.getY());
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(!e.isShiftDown()) return;

        for (Node node : graph.getNodes()) {
            if (!node.isSelected(e.getX(), e.getY())) {
                continue;
            }

            if(e.isControlDown()) {
                selectPathNode(node);
                return;
            }

            if (startingNode == null) {
                startingNode = node;
            } 
            else if (endingNode == null && node != startingNode) {
                endingNode = node;
            }

            break;
        }

        if(startingNode != null && endingNode != null) {
            graph.insertArc(new Arc(startingNode, endingNode, distIdx));
            distIdx++;

            System.out.println(graph.getAdjacent().entrySet());

            startingNode = null;
            endingNode = null;

            repaint();
        } 
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
        updateHover(-1, -1);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        for(Node node : graph.getNodes()) {
            if(node.isSelected(e.getX(), e.getY())) {
                draggedNode = node;
                return;
            }
        }


        Node newNode = new Node("Node" + nodeIdx, e.getX(), e.getY());
        graph.insertNode(newNode);

        resetAlgorithm();

        nodeIdx++;
        this.repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        draggedNode = null;
        updateHover(e.getX(), e.getY());
    } 

    private void resetAlgorithm() {
        for (Node n : graph.getNodes()) n.setStatus(Node.Status.IDLE);
        dijkstra = null;
        pathStart = null;
        pathEnd = null;
        repaint();
    }

    private void selectPathNode(Node node) {
        if (pathStart == null) {
            pathStart = node;
            node.setStatus(Node.Status.CURRENT);
        } else if (pathEnd == null && node != pathStart) {
            pathEnd = node;
            pathStart.setStatus(Node.Status.IDLE);
            dijkstra = new Dijkstra(graph, pathStart, pathEnd);
        }
        repaint();
    }
    
}
