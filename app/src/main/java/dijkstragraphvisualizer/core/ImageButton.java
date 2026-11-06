package dijkstragraphvisualizer.core;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import dijkstragraphvisualizer.utils.FileLoader;

public class ImageButton extends JButton {
    private final ImageIcon image;
    
    public ImageButton(String filepath) {
        this.image = new ImageIcon(FileLoader.loadFile(filepath));
        this.setIcon(image);
    }
}
