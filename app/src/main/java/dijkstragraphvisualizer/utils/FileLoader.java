package dijkstragraphvisualizer.utils;

import java.net.URL;

public class FileLoader {
    
    public static URL loadFile(String filepath) {
        return FileLoader.class.getClassLoader().getResource(filepath);
    }  

}
