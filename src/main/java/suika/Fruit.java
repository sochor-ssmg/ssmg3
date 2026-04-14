package suika;

import java.awt.Image;

public class Fruit extends Entity {

    private int order;
    private Image sprite;
    private float velocityX;
    private float velocityY;

    public Fruit(int x, int y, int width, int height, Image sprite, int order){
        super(x, y, width, height);
        this.sprite = sprite;
        this.order = order;
        this.velocityX = 0f;
        this.velocityY = 0f;
    }

    public int getCenterX(){
        return getX()+getWidth()/2;
    }

    public int getCenterY(){
        return getY()+getHeight()/2;
    }

    public int getRadius(){
        return getWidth()/2;
    }

    public Image getSprite(){
        return sprite;
    }

    public void setSprite(Image sprite){
        this.sprite=sprite;
    }

    public float getVelocityX(){
        return velocityX;
    }

    public void setVelocityX(float velocityX){
        this.velocityX=velocityX;
    }

    public float getVelocityY(){
        return velocityY;
    }

    public void setVelocityY(float velocityY){
        this.velocityY = velocityY;
    }

    public int getOrder(){
        return order;
    }

    public void setOrder(int order){
        this.order = order;
    }
}