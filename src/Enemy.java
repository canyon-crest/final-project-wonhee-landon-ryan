import java.awt.*;

public class Enemy {
    private static final double SPEED = 120.0;

    private final Rectangle platform;
    private final Rectangle rect;
    private boolean direction = true; // true for right, false for left

    public Enemy(Rectangle platform, int x, int y, int width, int height) {
        this.platform = platform;
        rect = new Rectangle(x, y, width, height);
    }

    public void update(double dt) {
        if (direction) {
            if (rect.x + rect.width >= platform.x + platform.width) {
                direction = false; // Change direction to left
            }
        } else {
            if (rect.x <= platform.x) {
                direction = true; // Change direction to right
            }
        }

        if (direction) {
            rect.x += (int) (SPEED * dt); // Move right
        } else {
            rect.x -= (int) (SPEED * dt); // Move left
        }
    }

    public void draw(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect(rect.x, rect.y, rect.width, rect.height);
    }

    public Rectangle getRect() {
        return rect;
    }
}


