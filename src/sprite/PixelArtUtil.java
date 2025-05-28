package sprite;

import java.awt.*;

public class PixelArtUtil {
    /**
     * The scale factor for pixel art. This is used to scale images and snap coordinates.
     * It should be set to a value that matches the pixel art style, typically 3 for a 3x scale.
     */
    private static final int PIXEL_SCALE = 3;

    /**
     * Scales an image to the pixel art scale.
     * @param image the image to scale
     * @return the scaled image
     */
    public static Image scale(Image image) {
        return image.getScaledInstance(PIXEL_SCALE * image.getWidth(null), PIXEL_SCALE * image.getHeight(null), Image.SCALE_FAST);
    }

    /**
     * Snaps a coordinate to the nearest pixel art grid.
     * @param coord the coordinate to snap
     * @return the snapped coordinate
     */
    public static int snap(int coord) {
        return (coord / PIXEL_SCALE) * PIXEL_SCALE;
    }
}
