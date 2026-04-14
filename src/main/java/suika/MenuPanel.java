package suika;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class MenuPanel extends JPanel {

    Random random = new Random();
    private final Image background = ResourcesHandler.mainmenu;

    private final Image[] letters = {
            ResourcesHandler.s, ResourcesHandler.e, ResourcesHandler.s, ResourcesHandler.a,
            ResourcesHandler.m, ResourcesHandler.e, ResourcesHandler.g, ResourcesHandler.a
    };

    private final int[] letterPositions = {120, 60, 20, 0, 0, 20, 60, 120};
    private double[] letterAngles = new double[8];
    private double[] letterSpeeds = new double[8];

    private int swingHeight = 15;
    private int currentHighScore;

    public MenuPanel(Runnable onStart) {
        setLayout(new BorderLayout());

        for(int i = 0; i < letters.length; i++) {
            double startAngle = random.nextDouble() * 6.28;
            letterAngles[i] = startAngle;
            double randomSpeed = 0.03 + (random.nextDouble() * 0.03);
            letterSpeeds[i] = randomSpeed;
        }

        JButton startButton = new JButton("START");
        startButton.setFont(ResourcesHandler.customFont.deriveFont(48f));
        startButton.setPreferredSize(new Dimension(250, 80));
        startButton.setOpaque(false);
        startButton.setContentAreaFilled(false);
        startButton.setBorderPainted(false);
        startButton.setFocusPainted(false);
        startButton.setForeground(new Color(255, 215, 0));
        startButton.addActionListener(e -> {
            if(onStart != null){
                onStart.run();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.add(startButton);
        add(buttonPanel, BorderLayout.SOUTH);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 150, 0));

        currentHighScore = ScoreManager.loadHighScore();

        Timer timer = new Timer(30, e -> {
            for(int i = 0; i < letters.length; i++) {
                double currentAngle = letterAngles[i];
                double speed = letterSpeeds[i];
                double nextAngle = currentAngle + speed;
                letterAngles[i] = nextAngle;
            }
            repaint();
        });
        timer.start();
    }

    public void updateHighscoreDisplay(int highscore) {
        currentHighScore = highscore;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, 1400, 1000, null);

        int startX = 60;
        int startY = 100;
        int spacing = 160;

        for(int i = 0; i < letters.length; i++){
            double sineValue = Math.sin(letterAngles[i]);
            double smoothMove = sineValue * swingHeight;
            int offset = (int)smoothMove;
            int x = startX + (i * spacing);
            int y = startY + letterPositions[i] + offset;
            g.drawImage(letters[i], x, y, 150, 150, null);
        }

        g.setFont(ResourcesHandler.customFont.deriveFont(80f));
        g.setColor(new Color(255, 215, 0));

        String highText = "High Score: " + currentHighScore;
        FontMetrics fm = g.getFontMetrics(g.getFont());
        int x = (1400 - fm.stringWidth(highText)) / 2;

        g.drawString(highText, x, 600);
    }
}