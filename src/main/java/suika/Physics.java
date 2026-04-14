package suika;

import java.awt.Image;
import java.util.ArrayList;
import java.util.Random;

public class Physics {

    private static final int PLAY_LEFT = 375;
    private static final int PLAY_RIGHT = 1025;
    private static final int PLAY_TOP = 100;
    private static final int PLAY_BOTTOM = 900;

    private static final float gravity = 0.5f;
    private static final float maxFallSpeed = 15f;
    private static final float mergeTolerance = 3.0f;
    private static final int collisionPassCount = 6;

    private final ArrayList<Fruit> fruits = new ArrayList<>();
    private final Random random = new Random();

    private final Runnable onGameOver;

    private int nextOrder = random.nextInt(5) + 1;
    private int spawnTimer = 0;
    private boolean gameOverTriggered = false;
    private int score = 0;

    public Physics(Runnable onGameOver){
        this.onGameOver = onGameOver;
    }

    public void update(Input input, int pointerX, int pointerY){

        if(gameOverTriggered){
            return;
        }

        if(spawnTimer > 0){
            spawnTimer--;
        }

        if(input.isLeftClick() && spawnTimer == 0){
            spawnFruit(pointerX, pointerY);
            input.resetLeftClick();
            spawnTimer = 20;
        }

        for(Fruit fruit : fruits){
            applyGravity(fruit);
            moveFruit(fruit);
            keepFruitInsidePlayArea(fruit);
        }

        for(int pass = 0; pass < collisionPassCount; pass++){
            handleFruitPairs();
            keepAllFruitsInsidePlayArea();
        }

        checkForGameOver();
    }

    private void spawnFruit(int pointerX, int pointerY){
        int size = nextOrder * 26;
        Image sprite = ResourcesHandler.getFruitImage(nextOrder);

        Fruit fruit = new Fruit(
                pointerX - size / 2,
                pointerY,
                size,
                size,
                sprite,
                nextOrder
        );

        fruits.add(fruit);
        nextOrder = random.nextInt(5) + 1;
    }

    private void applyGravity(Fruit fruit){
        float newVelocityY = fruit.getVelocityY() + gravity;
        if(newVelocityY > maxFallSpeed){
            newVelocityY = maxFallSpeed;
        }
        fruit.setVelocityY(newVelocityY);
    }

    private void moveFruit(Fruit fruit){
        fruit.setX((int)Math.round(fruit.getX() + fruit.getVelocityX()));
        fruit.setY((int)Math.round(fruit.getY() + fruit.getVelocityY()));
    }

    private void keepFruitInsidePlayArea(Fruit fruit){
        if(fruit.getX() < PLAY_LEFT){
            fruit.setX(PLAY_LEFT);
            fruit.setVelocityX(0f);
        }

        if(fruit.getX() + fruit.getWidth() > PLAY_RIGHT){
            fruit.setX(PLAY_RIGHT - fruit.getWidth());
            fruit.setVelocityX(0f);
        }

        if(fruit.getY() + fruit.getHeight() > PLAY_BOTTOM){
            fruit.setY(PLAY_BOTTOM - fruit.getHeight());
            fruit.setVelocityY(0f);
            fruit.setVelocityX(0f);
        }
    }

    private void keepAllFruitsInsidePlayArea(){
        for(Fruit fruit : fruits){
            keepFruitInsidePlayArea(fruit);
        }
    }

    private void handleFruitPairs(){
        for(int i = 0; i < fruits.size(); i++){
            for(int j = i + 1; j < fruits.size(); j++){
                Fruit fruit1 = fruits.get(i);
                Fruit fruit2 = fruits.get(j);

                if(fruit1.getOrder() == fruit2.getOrder() && fruit1.getOrder() < 10){
                    if(isCloseEnoughToMerge(fruit1, fruit2)){
                        mergeFruitPair(fruit1, fruit2, i, j);
                        return;
                    }
                }

                if(isTouching(fruit1, fruit2)){
                    separateFruitPair(fruit1, fruit2);
                }
            }
        }
    }

    private void mergeFruitPair(Fruit fruit1, Fruit fruit2, int index1, int index2){
        int newOrder = fruit1.getOrder() + 1;
        if(newOrder > 10){
            newOrder = 10;
        }

        int points = (fruit1.getOrder() + fruit2.getOrder()) * 10;
        score += points;

        int newSize = newOrder * 26;
        Image newSprite = ResourcesHandler.getFruitImage(newOrder);

        int centerX = (fruit1.getCenterX() + fruit2.getCenterX()) / 2;
        int centerY = (fruit1.getCenterY() + fruit2.getCenterY()) / 2;

        Fruit mergedFruit = new Fruit(
                centerX - newSize / 2,
                centerY - newSize / 2,
                newSize,
                newSize,
                newSprite,
                newOrder
        );

        fruits.remove(index2);
        fruits.remove(index1);
        fruits.add(mergedFruit);
    }

    private void separateFruitPair(Fruit fruit1, Fruit fruit2){
        Fruit upperFruit;
        Fruit lowerFruit;

        if(fruit1.getCenterY() <= fruit2.getCenterY()){
            upperFruit = fruit1;
            lowerFruit = fruit2;
        } else {
            upperFruit = fruit2;
            lowerFruit = fruit1;
        }

        int centerOffsetX = upperFruit.getCenterX() - lowerFruit.getCenterX();
        int centerOffsetY = upperFruit.getCenterY() - lowerFruit.getCenterY();

        double distanceBetweenCenters = Math.sqrt(
                centerOffsetX * centerOffsetX + centerOffsetY * centerOffsetY
        );
        double minimumAllowedDistance = upperFruit.getRadius() + lowerFruit.getRadius();

        if(distanceBetweenCenters <= 0.0001){
            centerOffsetX = 0;
            centerOffsetY = -1;
            distanceBetweenCenters = 1;
        }

        if(distanceBetweenCenters >= minimumAllowedDistance){
            return;
        }

        double collisionNormalX = centerOffsetX / distanceBetweenCenters;
        double collisionNormalY = centerOffsetY / distanceBetweenCenters;
        double overlapAmount = minimumAllowedDistance - distanceBetweenCenters;

        upperFruit.setX((int)Math.round(upperFruit.getX() + collisionNormalX * overlapAmount));
        upperFruit.setY((int)Math.round(upperFruit.getY() + collisionNormalY * overlapAmount));

        double velocityAlongCollisionNormal =
                upperFruit.getVelocityX() * collisionNormalX +
                        upperFruit.getVelocityY() * collisionNormalY;

        if(velocityAlongCollisionNormal < 0){
            upperFruit.setVelocityX((float)(upperFruit.getVelocityX() - velocityAlongCollisionNormal * collisionNormalX));
            upperFruit.setVelocityY((float)(upperFruit.getVelocityY() - velocityAlongCollisionNormal * collisionNormalY));
        }

        keepFruitInsidePlayArea(upperFruit);
    }

    private boolean isCloseEnoughToMerge(Fruit fruit1, Fruit fruit2){
        int centerOffsetX = fruit1.getCenterX() - fruit2.getCenterX();
        int centerOffsetY = fruit1.getCenterY() - fruit2.getCenterY();

        double distanceBetweenCenters = Math.sqrt(
                centerOffsetX * centerOffsetX + centerOffsetY * centerOffsetY
        );
        double minimumAllowedDistance = fruit1.getRadius() + fruit2.getRadius();

        return distanceBetweenCenters <= minimumAllowedDistance + mergeTolerance;
    }

    private boolean isTouching(Fruit fruit1, Fruit fruit2){
        int centerOffsetX = fruit1.getCenterX() - fruit2.getCenterX();
        int centerOffsetY = fruit1.getCenterY() - fruit2.getCenterY();

        double distanceBetweenCenters = Math.sqrt(
                centerOffsetX * centerOffsetX + centerOffsetY * centerOffsetY
        );
        double minimumAllowedDistance = fruit1.getRadius() + fruit2.getRadius();

        return distanceBetweenCenters <= minimumAllowedDistance;
    }

    private void checkForGameOver(){
        for(Fruit fruit : fruits){
            boolean touchesTopBoundary = fruit.getY() <= PLAY_TOP &&
                    fruit.getY() + fruit.getHeight() >= PLAY_TOP;

            boolean almostStoppedVertically = Math.abs(fruit.getVelocityY()) < 0.1f;

            if(touchesTopBoundary && almostStoppedVertically){
                gameOverTriggered = true;
                if(onGameOver != null){
                    onGameOver.run();
                }
                return;
            }
        }
    }

    public int getClampedPointerX(int pointerX){
        if(pointerX < PLAY_LEFT){
            return PLAY_LEFT;
        }

        if(pointerX > PLAY_RIGHT){
            return PLAY_RIGHT;
        }

        return pointerX;
    }

    public int getPlayLeft(){
        return PLAY_LEFT;
    }

    public int getPlayRight(){
        return PLAY_RIGHT;
    }

    public int getPlayTop(){
        return PLAY_TOP;
    }

    public int getPlayBottom(){
        return PLAY_BOTTOM;
    }

    public int getNextOrder(){
        return nextOrder;
    }

    public ArrayList<Fruit> getFruits(){
        return fruits;
    }

    public int getScore(){
        return score;
    }
}