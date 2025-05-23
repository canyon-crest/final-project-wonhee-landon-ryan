package sprite;

import java.awt.*;

public class Animation {
    private static int frameIndex;

    public static Animation fromSheet(SpriteSheet sheet, int row, int col, int nFrames) {
        Image[] frames = new Image[nFrames];
        for (int i = 0; i < nFrames; i++) {
            int x = (col + i) % sheet.nCols;
            int y = row + (col + i) / sheet.nCols;
            frames[i] = sheet.images[y][x];
        }
        return new Animation(frames);
    }

    public static void update() {
        frameIndex = (int) (System.currentTimeMillis() / 100L);
    }

    private final Image[] frames;

    public Animation(Image[] frames) {
        this.frames = frames;
    }

    public Image getFrame() {
        return this.frames[frameIndex % this.frames.length];
    }
}
