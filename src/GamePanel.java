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
        player = new Player(100, 400, 40, 80);
        blocks.add(new Block(0, 560, 800, 40)); // Ground
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
        	player.getRect().y = HEIGHT - player.getRect().height; // Move to bottom
            generateRandomPlatforms();
        }
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

        // Add 5–8 random platforms
        int numPlatforms = 5 + (int)(Math.random() * 4); // 5 to 8
        for (int i = 0; i < numPlatforms; i++) {
            int platformWidth = 100 + (int)(Math.random() * 100); // 100-200 px wide
            int platformHeight = 20;
            int x = (int)(Math.random() * (WIDTH - platformWidth));
            int y = 100 + (int)(Math.random() * (HEIGHT - 200)); // keep it inside visible area

            blocks.add(new Block(x, y, platformWidth, platformHeight));
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


