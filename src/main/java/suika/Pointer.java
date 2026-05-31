package suika;

import java.awt.*;

public class Pointer extends Entity {

    private int legWidth;
    private int legHeight;

    public Pointer(int x, int y, int width, int height, int legWidth, int legHeight){
        super(x, y, width, height);
        this.legHeight = legHeight;
        this.legWidth = legWidth;
    }
}