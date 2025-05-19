package sprite;

import java.awt.*;

public class PixelArtUtil {
    private static final int PIXEL_SCALE = 3;

    public static Image scale(Image image) {
        return image.getScaledInstance(PIXEL_SCALE * image.getWidth(null), PIXEL_SCALE * image.getHeight(null), Image.SCALE_FAST);
    }

    public static int snap(int coord) {
        return (coord / PIXEL_SCALE) * PIXEL_SCALE;
    }
}
