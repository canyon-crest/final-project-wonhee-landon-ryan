package sprite;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SpriteSheet {
    public static SpriteSheet fromImage(BufferedImage src, int width, int height) {
        int nRows = src.getHeight() / height;
        int nCols = src.getWidth() / width;

        Image[][] result = new Image[nRows][nCols];
        for (int row = 0; row < nRows; row++) {
            for (int col = 0; col < nCols; col++) {
                result[row][col] = PixelArtUtil.scale(src.getSubimage(col * width, row * height, width, height));
            }
        }

        return new SpriteSheet(result, width, height);
    }

    public final Image[][] images;
    public final int width, height;
    public final int nRows, nCols;

    public SpriteSheet(Image[][] images, int width, int height) {
        this.images = images;
        this.width = width;
        this.height = height;
        this.nRows = images.length;
        this.nCols = images[0].length;
    }
}
