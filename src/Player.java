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
            // https://brullov.itch.io/generic-char-asset
            SPRITE_SHEET = SpriteSheet.fromImage(ImageIO.read(new File("./assets/character/red/char_red_2.png")), 56, 56);
            IDLE_ANIMATION = Animation.fromSheet(SPRITE_SHEET, 0, 0, 1);
            WALK_ANIMATION = Animation.fromSheet(SPRITE_SHEET, 0, 0, 10);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Rectangle rect;
    private double velocityY = 0;
    private final double GRAVITY = 0.8;
    private final double JUMP_STRENGTH = -25;
    private final int MOVE_SPEED = 5;
    private boolean onGround = false;

    private Sprite sprite;

    public Player(int x, int y, int width, int height) {
        rect = new Rectangle(x, y, width, height);
        sprite = new Sprite(WALK_ANIMATION);
    }

    public void update(boolean[] keys, ArrayList<Block> blocks) {
        sprite.animation = IDLE_ANIMATION;
        if (keys[KeyEvent.VK_A] || keys[KeyEvent.VK_D]) sprite.animation = WALK_ANIMATION;
        if (keys[KeyEvent.VK_A]) sprite.isFlipped = true;
        if (keys[KeyEvent.VK_D]) sprite.isFlipped = false;

        // Handle horizontal movement
        if (keys[KeyEvent.VK_A]) {
            rect.x -= MOVE_SPEED;
        }
        if (keys[KeyEvent.VK_D]) {
            rect.x += MOVE_SPEED;
        }

        // Apply gravity
        velocityY += GRAVITY;
        rect.y += velocityY;

        // Check and resolve vertical collisions
        for (Block block : blocks) {
            if (rect.intersects(block.getRect())) {
                Rectangle intersect = rect.intersection(block.getRect());

                if (intersect.height < intersect.width) {
                    if (velocityY > 0) {
                        // Falling and hitting the top of a block
                        rect.y -= intersect.height;
                        velocityY = 0;
                    } else if (velocityY < 0) {
                        // Jumping and hitting the bottom of a block
                        rect.y += intersect.height;
                        velocityY = 0;
                    }
                } else {
                    // Horizontal collision
                    if (rect.x < block.getRect().x) {
                        rect.x -= intersect.width;
                    } else {
                        rect.x += intersect.width;
                    }
                }
            }
        }

        // Ground detection (check 1px below the player)
        onGround = false;
        Rectangle groundCheck = new Rectangle(rect.x, rect.y + 1, rect.width, rect.height);
        for (Block block : blocks) {
            if (groundCheck.intersects(block.getRect())) {
                onGround = true;
                break;
            }
        }
    }


    public void jump() {
        if (onGround) {
            velocityY = JUMP_STRENGTH;
        }
    }
    
    public void resetVerticalVelocity() {
        velocityY = 0;
    }


    public void draw(Graphics g) {
        sprite.draw(g, rect.x, rect.y);
    }
    
    public Rectangle getRect() {
        return rect;
    }

}
