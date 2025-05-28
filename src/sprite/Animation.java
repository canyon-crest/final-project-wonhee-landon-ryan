package sprite;

import java.awt.*;

public class Animation {
    ///  The index of the current frame in the animation.
    private static int frameIndex;

    /**
     * Creates an animation from a sprite sheet.
     * @param sheet the sprite sheet to use
     * @param row the row in the sprite sheet
     * @param col the starting column in the sprite sheet
     * @param nFrames the number of frames in the animation
     * @return an Animation object containing the frames
     */
    public static Animation fromSheet(SpriteSheet sheet, int row, int col, int nFrames) {
        Image[] frames = new Image[nFrames];
        // Loop through the specified number of frames
        for (int i = 0; i < nFrames; i++) {
            int x = (col + i) % sheet.nCols;
            int y = row + (col + i) / sheet.nCols;
            frames[i] = sheet.images[y][x];
        }
        return new Animation(frames);
    }

    /**
     * Updates the frame index based on the current time.
     * This method should be called periodically to advance the animation.
     */
    public static void update() {
        frameIndex = (int) (System.currentTimeMillis() / 100L);
    }

    ///  The frame images of the animation.
    private final Image[] frames;

    /**
     * Constructs an Animation with the specified frames.
     * @param frames the array of images representing the animation frames
     */
    public Animation(Image[] frames) {
        this.frames = frames;
    }

    /**
     * Returns the current frame of the animation.
     * @return the current frame image
     */
    public Image getFrame() {
        return this.frames[frameIndex % this.frames.length];
    }
}
