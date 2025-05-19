import sprite.PixelArtUtil;
import sprite.SpriteSheet;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Background {
    private static final Image IMAGE;

    static {
        try {
            IMAGE = PixelArtUtil.scale(ImageIO.read(new File("./assets/background/01_original.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void draw(Graphics g) {
        int width = IMAGE.getWidth(null);
        int height = IMAGE.getHeight(null);
        int x = (GamePanel.WIDTH - width) / 2;
        int y = GamePanel.HEIGHT - height;
        g.drawImage(IMAGE, PixelArtUtil.snap(x), PixelArtUtil.snap(y), width, height, null);
    }
}
