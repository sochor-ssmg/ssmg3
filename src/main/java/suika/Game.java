package suika;

import javax.swing.*;
import java.awt.*;

public class Game extends JFrame {

    private static final String CARD_MENU = "menu";
    private static final String CARD_GAME = "game";

    private final CardLayout layout;
    private final JPanel container;

    private GameFrame currentGameFrame;
    private MenuPanel menuPanel;

    public Game(){
         setTitle("sesamega");
        setResizable(false);
        setSize(1400, 1000);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        layout = new CardLayout();
        container = new JPanel(layout);

        menuPanel = new MenuPanel(this::startGame);
        container.add(menuPanel, CARD_MENU);

        add(container);
        layout.show(container, CARD_MENU);
    }

    private void startGame(){
        if(currentGameFrame != null){
            container.remove(currentGameFrame);
        }

        currentGameFrame = new GameFrame(this::showMenu);
        container.add(currentGameFrame, CARD_GAME);
        layout.show(container, CARD_GAME);
        currentGameFrame.requestFocusInWindow();
    }

    private void showMenu(){
        layout.show(container, CARD_MENU);
        int latestHighScore = ScoreManager.loadHighScore();
        menuPanel.updateHighscoreDisplay(latestHighScore);
    }

    public static void main(String[] args){
        SwingUtilities.invokeLater(() -> new Game().setVisible(true));
    }
}