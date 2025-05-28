package sprite;

import java.awt.*;

public class Sprite {
    /// The animation associated with this sprite.
    public Animation animation;

    /// Indicates whether the sprite is flipped horizontally.
    public boolean isFlipped = false;

    /**
     * Creates a new Sprite with the specified animation.
     * @param animation the Animation to be used for this Sprite
     */
    public Sprite(Animation animation) {
        this.animation = animation;
    }

    /**
     * Draws the sprite at the specified coordinates.
     * @param g the Graphics context to draw on
     * @param x the x-coordinate where the sprite should be drawn
     * @param y the y-coordinate where the sprite should be drawn
     */
    public void draw(Graphics g, int x, int y) {
        Image image = this.animation.getFrame();
        if (this.isFlipped) {
            g.drawImage(image, PixelArtUtil.snap(x + image.getWidth(null) / 2), PixelArtUtil.snap(y - image.getHeight(null) / 2), -image.getWidth(null), image.getHeight(null), null);
        } else {
            g.drawImage(image, PixelArtUtil.snap(x - image.getWidth(null) / 2), PixelArtUtil.snap(y - image.getHeight(null) / 2), null);
        }
    }
}
