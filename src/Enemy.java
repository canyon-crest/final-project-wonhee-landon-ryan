import sprite.Animation;
import sprite.Sprite;
import sprite.SpriteSheet;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Enemy {
    /// Animation for the enemy character
    private static final SpriteSheet SPRITE_SHEET;
    private static final Animation IDLE_ANIMATION;
    private static final Animation WALK_ANIMATION;

    static {
        // Load the sprite sheet and animations
        try {
            // https://brullov.itch.io/generic-char-asset
            SPRITE_SHEET = SpriteSheet.fromImage(ImageIO.read(new File("./assets/character/purple/char_purple_2.png")), 56, 56);
            IDLE_ANIMATION = Animation.fromSheet(SPRITE_SHEET, 0, 0, 1);
            WALK_ANIMATION = Animation.fromSheet(SPRITE_SHEET, 0, 0, 10);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Speed of the enemy movement in pixels per second
    private static final double SPEED = 120.0;

    // The platform on which the enemy moves
    private final Rectangle platform;

    // The rectangle representing the enemy's position and size
    private final Rectangle rect;

    // The sprite representing the enemy's appearance
    private final Sprite sprite;
    private boolean direction = true; // true for right, false for left

    /**
     * Constructor for the Enemy class.
     * @param platform the platform on which the enemy moves
     * @param x x coordinate of the enemy's position
     * @param y y coordinate of the enemy's position
     * @param width width of the enemy
     * @param height height of the enemy
     */
    public Enemy(Rectangle platform, int x, int y, int width, int height) {
        this.platform = platform;
        rect = new Rectangle(x, y, width, height);
        sprite = new Sprite(WALK_ANIMATION);
    }

    /**
     * Updates the enemy's position based on the elapsed time.
     * The enemy moves back and forth along the platform.
     * @param dt the elapsed time in seconds
     */
    public void update(double dt) {
        // Update the enemy's direction and position
        if (direction) {
            if (rect.x + rect.width >= platform.x + platform.width) {
                direction = false; // Change direction to left
            }
        } else {
            if (rect.x <= platform.x) {
                direction = true; // Change direction to right
            }
        }

        if (direction) {
            rect.x += (int) (SPEED * dt); // Move right
            sprite.isFlipped = false;
        } else {
            rect.x -= (int) (SPEED * dt); // Move left
            sprite.isFlipped = true;
        }
    }

    /**
     * Draws the enemy on the screen.
     * @param g the Graphics object used for drawing
     */
    public void draw(Graphics g) {
        sprite.draw(g, rect.x + rect.width / 2, rect.y);
    }

    /**
     * Returns the rectangle representing the enemy's position and size.
     * @return the rectangle of the enemy
     */
    public Rectangle getRect() {
        return rect;
    }
}


