import java.awt.*;

public class Block {
    ///  Rectangle representing the block
    private Rectangle rect;

    /**
     * Constructor for Block
     * @param x x coordinate of the block
     * @param y y coordinate of the block
     * @param width width of the block
     * @param height height of the block
     */
    public Block(int x, int y, int width, int height) {
        rect = new Rectangle(x, y, width, height);
    }

    /**
     * Returns the rectangle representing the block
     * @return Rectangle object representing the block
     */
    public Rectangle getRect() {
        return rect;
    }

    /**
     * Draws the block on the given Graphics object
     * @param g Graphics object to draw on
     */
    public void draw(Graphics g) {
        g.setColor(Color.GRAY);
        g.fillRect(rect.x, rect.y, rect.width, rect.height);
    }
}
