package dijkstragraphvisualizer.renderer;

import java.awt.BasicStroke;
import java.awt.Graphics2D;

import dijkstragraphvisualizer.utils.Colors;

public class Arc implements Renderer {
    private static final int OFFSET = 10;

    private final Node nodeA;
    private final Node nodeB;
    private final int dist;

    public Arc(final Node nodeA, final Node nodeB, final int dist) {
        this.nodeA = nodeA;
        this.nodeB = nodeB;
        this.dist = dist;
    }

    @Override
    public void draw(Graphics2D g2d) {
        int x = (nodeA.getX() + nodeB.getX()) / 2;
        int y = (nodeA.getY() + nodeB.getY()) / 2;

        g2d.setColor(Colors.PALETTE_WHITE);
        g2d.drawString(Integer.toString(dist), x, y - OFFSET);

        g2d.setColor(Colors.PALETTE_LIGHT_DARK);
        g2d.setStroke(new BasicStroke(3.0f));
        g2d.drawLine(nodeA.getX(), nodeA.getY(), nodeB.getX(), nodeB.getY());
    }

    public Node getNodeA() {
        return nodeA;
    }

    public Node getNodeB() {
        return nodeB;
    }

    public int getDist() {
        return dist;
    }
    
}
