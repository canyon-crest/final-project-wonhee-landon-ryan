import sprite.Animation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class GamePanel extends JPanel implements KeyListener {
    private Background background;
    private Player player;
    private ArrayList<Block> blocks = new ArrayList<>();
    private boolean[] keys = new boolean[256];

    public static final int WIDTH = 800, HEIGHT = 600;

    public GamePanel() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setFocusable(true);
        addKeyListener(this);

        background = new Background();
        player = new Player(100, 500, 40, 80);
        generateRandomPlatforms(); 
    }


    public void startGameLoop() {
        Timer timer = new Timer(16, e -> {
            update();
            repaint();
        });
        timer.start();
    }

    private void update() {
        player.update(keys, blocks);

        // Check for level transition
        if (player.getRect().y < 0) {
            generateRandomPlatforms();
            placePlayerAtBottomSafely();
        }
    }
    
    private void placePlayerAtBottomSafely() {
        int safeY = HEIGHT - player.getRect().height - 50;

        // Try a few horizontal positions to avoid spawning inside a block
        for (int attempt = 0; attempt < 10; attempt++) {
            int x = 50 + (int)(Math.random() * (WIDTH - 100));
            player.getRect().setLocation(x, safeY);

            boolean collision = false;
            for (Block b : blocks) {
                if (player.getRect().intersects(b.getRect())) {
                    collision = true;
                    break;
                }
            }
            if (!collision) {
                player.resetVerticalVelocity();
                return;
            }
        }

        // If all positions collide, fallback
        player.getRect().setLocation(100, safeY);
        player.resetVerticalVelocity();
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Animation.update();

        // Background
        background.draw(g);

        // Player
        player.draw(g);

        // Blocks
        for (Block b : blocks) {
            b.draw(g);
        }
    }
    
    private void generateRandomPlatforms() {
        blocks.clear();

        // Always add a floor
        blocks.add(new Block(0, HEIGHT - 40, WIDTH, 40));

        int rows = 6;
        int cols = 6;
        int verticalSpacing = (HEIGHT - 200) / rows;
        int horizontalSpacing = WIDTH / cols;

        for (int i = 0; i < rows; i++) {
            // Random chance to generate a platform in this row
            if (Math.random() < 0.8) { // 80% chance per row
                int platformWidth = 100 + (int)(Math.random() * 50); // 100-150 px wide
                int platformHeight = 20;

                // Select a column zone and center within it
                int col = (int)(Math.random() * cols);
                int x = col * horizontalSpacing + (horizontalSpacing - platformWidth) / 2;

                // Place vertically with some offset
                int y = 80 + i * verticalSpacing;

                blocks.add(new Block(x, y, platformWidth, platformHeight));
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode()] = true;
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            player.jump();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keys[e.getKeyCode()] = false;
    }

    @Override
    public void keyTyped(KeyEvent e) {}
    
    
}


