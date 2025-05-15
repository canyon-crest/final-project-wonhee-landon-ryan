import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class GamePanel extends JPanel implements KeyListener {
    private Player player;
    private ArrayList<Block> blocks = new ArrayList<>();
    private boolean[] keys = new boolean[256];

    private final int WIDTH = 800, HEIGHT = 600;

    public GamePanel() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setFocusable(true);
        addKeyListener(this);

        player = new Player(100, 500, 40, 60);
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
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Background
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        // Player
        player.draw(g);

        // Blocks
        for (Block b : blocks) {
            b.draw(g);
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
