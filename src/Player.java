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

    private static final double GRAVITY = 2880;
    private static final double JUMP_STRENGTH = -1500;
    private static final double MOVE_SPEED = 400;

    private Rectangle rect;
    private double velocityX = 0;
    private double velocityY = 0;
    private boolean onGround = false;
    private long lastJumpTime = 0;

    private Sprite sprite;

    public Player(int x, int y, int width, int height) {
        rect = new Rectangle(x, y, width, height);
        sprite = new Sprite(WALK_ANIMATION);
    }

    public void update(boolean[] keys, ArrayList<Block> blocks, double dt) {
        if (keys[KeyEvent.VK_SPACE]) {
            jump();
        }

        // Handle horizontal movement
        velocityX = 0;
        if (keys[KeyEvent.VK_A]) {
            velocityX -= MOVE_SPEED;
        }
        if (keys[KeyEvent.VK_D]) {
            velocityX += MOVE_SPEED;
        }

        // Update animations
        if (velocityX > 0.1) {
            sprite.animation = WALK_ANIMATION;
            sprite.isFlipped = false;
        } else if (velocityX < -0.1) {
            sprite.animation = WALK_ANIMATION;
            sprite.isFlipped = true;
        } else {
            sprite.animation = IDLE_ANIMATION;
        }

        // Apply gravity
        velocityY += GRAVITY * dt;
        rect.x += (int) Math.round(velocityX * dt);
        rect.y += (int) Math.round(velocityY * dt);

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
        if (System.currentTimeMillis() - lastJumpTime < 50) {
            return; // Prevent double jumps too quickly
        }
        lastJumpTime = System.currentTimeMillis();

        if (onGround) {
            velocityY = JUMP_STRENGTH;
        }
    }


    public void draw(Graphics g) {
        sprite.draw(g, rect.x + rect.width / 2, rect.y);
    }
    
    public Rectangle getRect() {
        return rect;
    }

}
