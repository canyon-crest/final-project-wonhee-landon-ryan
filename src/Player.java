import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Player {
    private Rectangle rect;
    private double velocityY = 0;
    private final double GRAVITY = 0.5;
    private final double JUMP_STRENGTH = -10;
    private final int MOVE_SPEED = 5;
    private boolean onGround = false;

    public Player(int x, int y, int width, int height) {
        rect = new Rectangle(x, y, width, height);
    }

    public void update(boolean[] keys, ArrayList<Block> blocks) {
        if (keys[KeyEvent.VK_A]) rect.x -= MOVE_SPEED;
        if (keys[KeyEvent.VK_D]) rect.x += MOVE_SPEED;

        // Apply gravity
        velocityY += GRAVITY;
        rect.y += velocityY;

        // Simple collision with ground
        onGround = false;
        for (Block block : blocks) {
            if (rect.intersects(block.getRect())) {
                Rectangle intersect = rect.intersection(block.getRect());

                if (intersect.height < intersect.width) {
                    if (velocityY > 0) {
                        rect.y -= intersect.height;
                        onGround = true;
                        velocityY = 0;
                    } else {
                        rect.y += intersect.height;
                        velocityY = 0;
                    }
                } else {
                    if (rect.x < block.getRect().x) {
                        rect.x -= intersect.width;
                    } else {
                        rect.x += intersect.width;
                    }
                }
            }
        }
    }

    public void jump() {
        if (onGround) {
            velocityY = JUMP_STRENGTH;
        }
    }

    public void draw(Graphics g) {
        g.setColor(Color.BLUE);
        g.fillRect(rect.x, rect.y, rect.width, rect.height);
    }
}
