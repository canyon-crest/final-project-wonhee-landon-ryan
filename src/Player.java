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
    // Animation for the player character
    private static final SpriteSheet SPRITE_SHEET;
    private static final Animation IDLE_ANIMATION;
    private static final Animation WALK_ANIMATION;

    // Load the sprite sheet and animations
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

    // Constants for player movement and physics
    private static final double GRAVITY = 2880;
    private static final double JUMP_STRENGTH = -1500;
    private static final double MOVE_SPEED = 400;

    // Player current state
    private Rectangle rect;
    private double velocityX = 0;
    private double velocityY = 0;
    private boolean onGround = false;
    private long lastJumpTime = 0;

    // Player sprite for rendering
    private Sprite sprite;

    /**
     * Constructor for the Player class.
     * @param x x coordinate of the player's position
     * @param y y coordinate of the player's position
     * @param width width of the player
     * @param height height of the player
     */
    public Player(int x, int y, int width, int height) {
        rect = new Rectangle(x, y, width, height);
        sprite = new Sprite(WALK_ANIMATION);
    }

    /**
     * Updates the player's position and state based on input keys and the current game state.
     * @param keys array of boolean values representing the state of keys pressed
     * @param blocks list of blocks in the game for collision detection
     * @param dt elapsed time since the last update in seconds
     */
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

        if (rect.x < 0) {
            rect.x = 0;
        } else if (rect.x + rect.width > GamePanel.WIDTH) {
            rect.x = GamePanel.WIDTH - rect.width;
        }
    }


    /**
     * Makes the player jump if they are on the ground and not jumping too quickly.
     */
    public void jump() {
        if (System.currentTimeMillis() - lastJumpTime < 50) {
            return; // Prevent double jumps too quickly
        }
        lastJumpTime = System.currentTimeMillis();

        if (onGround) {
            velocityY = JUMP_STRENGTH;
        }
    }


    /**
     * Draws the player on the screen.
     * @param g the Graphics object used for drawing
     */
    public void draw(Graphics g) {
        sprite.draw(g, rect.x + rect.width / 2, rect.y);
    }

    /**
     * Returns the rectangle representing the player's position and size.
     * @return the rectangle of the player
     */
    public Rectangle getRect() {
        return rect;
    }

}
