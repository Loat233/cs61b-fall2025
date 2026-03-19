package gameengine;

import java.awt.*;

public class Palette {
    private static final Color red = new Color(255, 0, 0); // 0: red
    private static final Color green = new Color(0, 255, 0); // 1: green
    private static final Color blue = new Color(0, 0, 255); // 2: blue
    private static final Color black = new Color(0, 0, 0); // 3: black;
    private static final Color yellow = new Color(255, 255, 0); // 4: yellow;

    public Color pickColor(int i) {
        return switch (i) {
            case 0 -> red;
            case 1 -> green;
            case 2 -> blue;
            case 3 -> black;
            case 4 -> yellow;
            default -> black;
        };
    }
}
