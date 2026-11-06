package dijkstragraphvisualizer.renderer;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.util.HashMap;
import java.util.Map;

import dijkstragraphvisualizer.utils.Colors;

public class Node implements Renderer {
    public static final int RADIUS = 35;
    public static final int DIAMETER = RADIUS * 2;
    private static final int OFFSET = 10;

    public enum Status { IDLE, FRONTIER, CURRENT, VISITED, PATH }

    private Status status = Status.IDLE;
    private String name;
    private int x, y;
    private boolean hovered;
    private HashMap<Node, Integer> adjacents = new HashMap<Node, Integer>();

    public Node(String name, int x, int y) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.hovered = false;
    }
 
    @Override
    public void draw(Graphics2D g2d) {
        Font font = new Font("Sans Serif", Font.BOLD, 12);
		g2d.setFont(font);
		FontMetrics fm = g2d.getFontMetrics();
		
        g2d.setColor(Color.WHITE);
        g2d.drawString(name, x - fm.stringWidth(name) / 2, y - RADIUS - OFFSET);

        g2d.setColor(fillColor());
        g2d.setStroke(new BasicStroke(3.0f));
        g2d.fillOval(x - RADIUS, y - RADIUS, DIAMETER, DIAMETER);

        if(hovered) {
            g2d.setColor(Colors.PALETTE_HOVER_COLOR);
            g2d.setStroke(new BasicStroke(1.5f));
            g2d.drawOval(x - RADIUS, y - RADIUS, DIAMETER, DIAMETER);    
        }
    }

    private Color fillColor() {
        switch (status) {
            case CURRENT:  return new Color(220, 50, 50);   // rosso: nodo in elaborazione
            case VISITED:  return new Color(90, 90, 90);    // grigio: definitivo
            case FRONTIER: return new Color(230, 190, 40);  // giallo: distanza aggiornata
            case PATH:     return new Color(40, 180, 90);   // verde: percorso finale
            default:       return Colors.PALETTE_BLUE;
        }
    }

    public void insertAdjacent(Node node, int dist) {
		adjacents.put(node, dist);
	}

    public Map<Node, Integer> getAdjacent() {
        return adjacents;
    }

    public boolean isSelected(int px, int py) {
        int dx = px - x;
        int dy = py - y;
        return dx * dx + dy * dy <= RADIUS * RADIUS;
    }

    public boolean isHovered() {
        return hovered;
    }

    public void setHovered(boolean hovered) {
        this.hovered = hovered;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getName() {
        return name;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }
}
