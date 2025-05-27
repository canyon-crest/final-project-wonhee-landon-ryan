import sprite.Animation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;

public class GamePanel extends JPanel implements KeyListener {
    private static final int LEVEL_COUNT = 10;

    private final JPanel mainPanel;
    private final CardLayout cardLayout;

    private Background background;
    private Player player;
    private ArrayList<ArrayList<Block>> levels = new ArrayList<>();
    private ArrayList<ArrayList<Enemy>> enemiesPerLevel = new ArrayList<>();
    private int currentLevel = 0;
    private boolean[] keys = new boolean[256];
    private long lastUpdate;

    public static final int WIDTH = 800, HEIGHT = 600;

    public GamePanel(JPanel mainPanel, CardLayout cardLayout) {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setFocusable(true);
        addKeyListener(this);

        player = new Player(100, 500, 40, 80);
        background = new Background();
        levels.add(generateRandomPlatformLevel());
        enemiesPerLevel.add(generateEnemiesForLevel(levels.get(0)));
        this.mainPanel = mainPanel;
        this.cardLayout = cardLayout;
    }

    public void reset() {
        currentLevel = 0;
        levels.clear();
        levels.add(generateRandomPlatformLevel());
        player.getRect().setLocation(100, 500);
        lastUpdate = System.nanoTime();
        Arrays.fill(keys, false);
    }


    public void startGameLoop() {
        lastUpdate = System.nanoTime();
        Timer timer = new Timer(16, e -> {
            if (!isVisible()) {
                ((Timer) e.getSource()).stop();
                reset();
                return;
            }

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

            if (currentLevel == LEVEL_COUNT) {
                // You've reached the top level, show win screen
                cardLayout.show(mainPanel, "win");
                return;
            }

            // Create a new level above if it doesn't exist
            if (currentLevel >= levels.size()) {
                levels.add(generateRandomPlatformLevel());
                enemiesPerLevel.add(generateEnemiesForLevel(levels.get(currentLevel)));

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
        
        
        ArrayList<Enemy> currentEnemies = enemiesPerLevel.get(currentLevel);

        for (Enemy e : currentEnemies) {
            e.update(dt);

            // If colliding with player, push them
            if (e.getRect().intersects(player.getRect())) {
                Rectangle er = e.getRect();
                Rectangle pr = player.getRect();

                if (er.x < pr.x) {
                    pr.x += 3;
                } else if (er.x > pr.x) {
                    pr.x -= 3;
                }
            }
        }

    }
    
    private ArrayList<Enemy> generateEnemiesForLevel(ArrayList<Block> blocks) {
        ArrayList<Enemy> enemies = new ArrayList<>();

        for (Block b : blocks) {
            if (Math.random() < 0.3 && b.getRect().width >= 100 && b.getRect().y < HEIGHT - 100) {
                int enemyWidth = 40;
                int enemyHeight = 80;
                int x = b.getRect().x + (int)(Math.random() * (b.getRect().width - enemyWidth));
                int y = b.getRect().y - enemyHeight; // On top of platform

                enemies.add(new Enemy(b.getRect(), x, y, enemyWidth, enemyHeight));
            }
        }

        return enemies;
    }




    private void placePlayerAtBottomSafely() {
        int safeY = HEIGHT - player.getRect().height - 50;
        player.getRect().setLocation(player.getRect().x, safeY);
    }

    private void placePlayerAtTopSafely() {
        int safeY = 10;
        player.getRect().setLocation(player.getRect().x, safeY);
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Animation.update();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        // Background
        background.draw(g);

        // Enemy
        for (Enemy e : enemiesPerLevel.get(currentLevel)) {
            e.draw(g);
        }

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
        if (currentLevel == 0) {
            newBlocks.add(new Block(0, HEIGHT - 40, WIDTH, 40));
        }
        int rows = 7;
        int cols = 6;
        int verticalSpacing = (HEIGHT - 200) / rows;
        int horizontalSpacing = WIDTH / cols;

        for (int i = 0; i < rows; i++) {
            if (Math.random() < 0.8) {
                int platformWidth = 100 + (int) (Math.random() * 50);
                int platformHeight = 20;
                int col = (int) (Math.random() * cols);
                int x = col * horizontalSpacing + (horizontalSpacing - platformWidth) / 2;
                int y = 80 + i * verticalSpacing;

                // Do not place a platform that is within 100 pixels above another platform
                boolean shouldPlace = true;
                for (Block existingBlock : newBlocks) {
                    if (Math.abs(existingBlock.getRect().y - y) < 100 &&
                        Math.abs(existingBlock.getRect().x - x) < platformWidth + existingBlock.getRect().width) {
                        shouldPlace = false;
                        break;
                    }
                }
                if (!shouldPlace) {
                    continue;
                }

                newBlocks.add(new Block(x, y, platformWidth, platformHeight));
            }
        }

        return newBlocks;
    }


    @Override
    public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keys[e.getKeyCode()] = false;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
}
