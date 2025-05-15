import java.awt.*;

public class Block {
    private Rectangle rect;

    public Block(int x, int y, int width, int height) {
        rect = new Rectangle(x, y, width, height);
    }

    public Rectangle getRect() {
        return rect;
    }

    public void draw(Graphics g) {
        g.setColor(Color.GRAY);
        g.fillRect(rect.x, rect.y, rect.width, rect.height);
    }
}
