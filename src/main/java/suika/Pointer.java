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

    public void wallBlock(Rectangle leftWall, Rectangle rightWall){
        int minCenter = leftWall.x + leftWall.width;
        int maxCenter = rightWall.x;
        int newX = getX();

        if(newX < minCenter){
            newX = minCenter;
        }

        if(newX > maxCenter){
            newX = maxCenter;
        }

        setX(newX);
    }

    public int getLegWidth(){
        return legWidth;
    }

    public void setLegWidth(int legWidth){
        this.legWidth = legWidth;
    }

    public int getLegHeight(){
        return legHeight;
    }

    public void setLegHeight(int legHeight){
        this.legHeight = legHeight;
    }
}