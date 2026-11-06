package dijkstragraphvisualizer.core;

import java.awt.Component;

import javax.swing.JFrame;

public class Window implements WindowInterface {
    private JFrame frame;

    public Window() {
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Dijkstra Graph Visualizer");
        frame.setSize(1080, 720);
        frame.setLocationRelativeTo(null);
    }

    @Override
    public void show() {
        frame.setVisible(true);
    }

    @Override
    public void addContentPane(Component component, String layout) {
        frame.getContentPane().add(component, layout);
    }

    @Override
    public void add(Component... components) {
        for(Component component : components) {
            frame.add(component);
        }
    }
    
}
