package dijkstragraphvisualizer.core;

import java.awt.Component;

public interface WindowInterface {
    void add(Component... components);
    void addContentPane(Component component, String layout);
    void show();
}
