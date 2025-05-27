import sprite.Animation;
import sprite.Sprite;
import sprite.SpriteSheet;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Enemy {
    private static final SpriteSheet SPRITE_SHEET;
    private static final Animation IDLE_ANIMATION;
    private static final Animation WALK_ANIMATION;

    static {
        try {
            // https://brullov.itch.io/generic-char-asset
            SPRITE_SHEET = SpriteSheet.fromImage(ImageIO.read(new File("./assets/character/purple/char_purple_2.png")), 56, 56);
            IDLE_ANIMATION = Animation.fromSheet(SPRITE_SHEET, 0, 0, 1);
            WALK_ANIMATION = Animation.fromSheet(SPRITE_SHEET, 0, 0, 10);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static final double SPEED = 120.0;

    private final Rectangle platform;
    private final Rectangle rect;
    private final Sprite sprite;
    private boolean direction = true; // true for right, false for left

    public Enemy(Rectangle platform, int x, int y, int width, int height) {
        this.platform = platform;
        rect = new Rectangle(x, y, width, height);
        sprite = new Sprite(WALK_ANIMATION);
    }

    public void update(double dt) {
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

    public void draw(Graphics g) {
        sprite.draw(g, rect.x + rect.width / 2, rect.y);
    }

    public Rectangle getRect() {
        return rect;
    }
}


