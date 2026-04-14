package suika;

import javax.swing.*;
import java.awt.*;

public class GameFrame extends JPanel {

    private final Input input = new Input();
    private final Pointer pointer = new Pointer(250, 25, 100, 100, 25, 54);

    private final Physics physics;

    private final Runnable onGameOver;
    private final Timer gameTimer;

    private int highScore;

    public GameFrame(Runnable onGameOver){
        this.onGameOver = onGameOver;

        physics = new Physics(this::handleGameOver);

        setFocusable(true);
        addMouseMotionListener(input);
        addMouseListener(input);

        highScore = ScoreManager.loadHighScore();

        gameTimer = new Timer(16, e -> {
            pointer.setX(physics.getClampedPointerX(input.getMouseX()));

            physics.update(input, pointer.getX(), pointer.getY());
            repaint();
        });
        gameTimer.start();
    }

    private void handleGameOver(){
        if(gameTimer.isRunning()){
            gameTimer.stop();
        }

        int currentScore = physics.getScore();
        if(currentScore > highScore){
            ScoreManager.saveHighScore(currentScore);
            highScore = currentScore;
        }

        if(onGameOver != null){
            onGameOver.run();
        }
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(ResourcesHandler.mainmenu, 0, 0, 1400, 1000, null);
        g.drawRect(
                physics.getPlayLeft(),
                physics.getPlayTop(),
                physics.getPlayRight() - physics.getPlayLeft(),
                physics.getPlayBottom() - physics.getPlayTop()
        );

        int nextSize = physics.getNextOrder() * 26;
        Image nextSprite = ResourcesHandler.getFruitImage(physics.getNextOrder());
        g.drawImage(nextSprite, pointer.getX() - nextSize / 2, pointer.getY(), nextSize, nextSize, null);

        for(Fruit fruit : physics.getFruits()){
            g.drawImage(fruit.getSprite(), fruit.getX(), fruit.getY(), fruit.getWidth(), fruit.getHeight(), null);
        }

        g.setFont(ResourcesHandler.customFont.deriveFont(36f));
        g.setColor(Color.WHITE);
        int currentScore = physics.getScore();
        g.drawString("Score: " + currentScore, 40, 460);
        g.drawString("High Score: " + highScore, 40, 500);
    }
}