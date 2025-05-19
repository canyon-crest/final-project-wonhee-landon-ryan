package sprite;

import java.awt.*;

public class Sprite {
    public Animation animation;

    public Sprite(Animation animation) {
        this.animation = animation;
    }

    public void draw(Graphics g, int x, int y) {
        Image image = this.animation.getFrame();
        g.drawImage(image, PixelArtUtil.snap(x - image.getWidth(null) / 2), PixelArtUtil.snap(y - image.getHeight(null) / 2), null);
    }
}
