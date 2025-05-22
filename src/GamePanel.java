import sprite.Animation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class GamePanel extends JPanel implements KeyListener {
    private Background background;
    private Player player;
    private ArrayList<ArrayList<Block>> levels = new ArrayList<>();
    private int currentLevel = 0;
    private boolean[] keys = new boolean[256];
    private long lastUpdate;

    public static final int WIDTH = 800, HEIGHT = 600;

    public GamePanel() {
    	    setPreferredSize(new Dimension(WIDTH, HEIGHT));
    	    setFocusable(true);
    	    addKeyListener(this);

    	    player = new Player(100, 500, 40, 80);
    	    levels.add(generateRandomPlatformLevel());
    	    background = new Background();
    }


    public void startGameLoop() {
        lastUpdate = System.nanoTime();
        Timer timer = new Timer(16, e -> {
            update();
            repaint();
        });
        timer.start();
    }

    

    private void update() {
        ArrayList<Block> currentBlocks = levels.get(currentLevel);
        long now = System.nanoTime();
        double dt = (double) (now - lastUpdate) / 1e9;
        lastUpdate = now; 
        player.update(keys, currentBlocks, dt);

        // GOING UP
        if (player.getRect().y < 0) {
            currentLevel++;

            // Create a new level above if it doesn't exist
            if (currentLevel >= levels.size()) {
                levels.add(generateRandomPlatformLevel());
            }

            placePlayerAtBottomSafely();
        }

        // FALLING DOWN
        if (player.getRect().y > HEIGHT) {
            if (currentLevel > 0) {
                currentLevel--;
                placePlayerAtTopSafely();
            } else {
                // You're on level 0: reset position to ground
                player.getRect().y = HEIGHT - player.getRect().height - 50;
            }
        }
    }

    
    private void placePlayerAtBottomSafely() {
        int safeY = HEIGHT - player.getRect().height - 50;
        player.getRect().setLocation(player.getRect().x, safeY);
    }
    
    private void placePlayerAtTopSafely() {
        int safeY = 10;
        player.getRect().setLocation(player.getRect().x, safeY);
    }

    private boolean checkCollision1(ArrayList<Block> blocks) {
        for (Block b : blocks) {
            if (player.getRect().intersects(b.getRect())) {
                return true;
            }
        }
        return false;
    }





    private boolean checkCollision(ArrayList<Block> arrayList) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Animation.update();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        // Background
        background.draw(g);

        // Player
        player.draw(g);

        // Blocks
        for (Block b : levels.get(currentLevel)) {
            b.draw(g);
        }
    }
    
    private ArrayList<Block> generateRandomPlatformLevel() {
        ArrayList<Block> newBlocks = new ArrayList<>();

        // Always add a floor
        if(currentLevel == 0) {
        	newBlocks.add(new Block(0, HEIGHT - 40, WIDTH, 40));
        }
        int rows = 6;
        int cols = 6;
        int verticalSpacing = (HEIGHT - 200) / rows;
        int horizontalSpacing = WIDTH / cols;

        for (int i = 0; i < rows; i++) {
            if (Math.random() < 0.8) {
                int platformWidth = 100 + (int)(Math.random() * 50);
                int platformHeight = 20;
                int col = (int)(Math.random() * cols);
                int x = col * horizontalSpacing + (horizontalSpacing - platformWidth) / 2;
                int y = 80 + i * verticalSpacing;

                newBlocks.add(new Block(x, y, platformWidth, platformHeight));
            }
        }

        return newBlocks;
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
