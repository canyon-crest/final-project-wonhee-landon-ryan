import java.awt.*;

public class Enemy {
    private Rectangle rect;
    private int speed = 2;

    public Enemy(int x, int y, int width, int height) {
        rect = new Rectangle(x, y, width, height);
    }

    public void update(Player player) {
        if (player.getRect().x < rect.x) {
            rect.x -= speed;
        } else if (player.getRect().x > rect.x) {
            rect.x += speed;
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


