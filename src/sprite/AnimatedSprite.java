package sprite;

import java.awt.*;

public class AnimatedSprite {
    private Animation animation;

    public AnimatedSprite(Animation animation) {
        this.animation = animation;
    }

    public void draw(Graphics g, int x, int y) {
        Image image = this.animation.getFrame();
        g.drawImage(image, x - image.getWidth(null) / 2, y - image.getHeight(null) / 2, null);
    }
}
