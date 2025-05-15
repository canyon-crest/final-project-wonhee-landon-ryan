import sprite.Sprite;
import sprite.Animation;
import sprite.SpriteSheet;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Player {
    private static final SpriteSheet SPRITE_SHEET;
    private static final Animation IDLE_ANIMATION;
    private static final Animation WALK_ANIMATION;

    static {
        try {
            SPRITE_SHEET = SpriteSheet.fromImage(ImageIO.read(new File("./generic_char_v0.2/png/red/char_red_2.png")), 56, 56);
            IDLE_ANIMATION = Animation.fromSheet(SPRITE_SHEET, 0, 0, 1);
            WALK_ANIMATION = Animation.fromSheet(SPRITE_SHEET, 0, 0, 10);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Rectangle rect;
    private double velocityY = 0;
    private final double GRAVITY = 0.8;
    private final double JUMP_STRENGTH = -20;
    private final int MOVE_SPEED = 5;
    private boolean onGround = false;

    private Sprite sprite;

    public Player(int x, int y, int width, int height) {
        rect = new Rectangle(x, y, width, height);
        sprite = new Sprite(WALK_ANIMATION);
    }

    public void update(boolean[] keys, ArrayList<Block> blocks) {
        sprite.animation = IDLE_ANIMATION;
        if (keys[KeyEvent.VK_A]) rect.x -= MOVE_SPEED;
        if (keys[KeyEvent.VK_D]) rect.x += MOVE_SPEED;
        if (keys[KeyEvent.VK_A] || keys[KeyEvent.VK_D]) sprite.animation = WALK_ANIMATION;

        // Apply gravity
        velocityY += GRAVITY;
        rect.y += velocityY;

        // Simple collision with ground
        onGround = false;
        for (Block block : blocks) {
            if (rect.intersects(block.getRect())) {
                Rectangle intersect = rect.intersection(block.getRect());

                if (intersect.height < intersect.width) {
                    if (velocityY > 0) {
                        rect.y -= intersect.height;
                        onGround = true;
                        velocityY = 0;
                    } else {
                        rect.y += intersect.height;
                        velocityY = 0;
                    }
                } else {
                    if (rect.x < block.getRect().x) {
                        rect.x -= intersect.width;
                    } else {
                        rect.x += intersect.width;
                    }
                }
            }
        }
    }

    public void jump() {
        if (onGround) {
            velocityY = JUMP_STRENGTH;
        }
    }

    public void draw(Graphics g) {
        sprite.draw(g, rect.x, rect.y);
    }
}
