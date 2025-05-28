import javax.swing.*;
import java.awt.*;

/**
 * A JPanel that renders a background for the game.
 */
public class BackgroundRenderer extends JPanel {
    private final Background background;

    /**
     * Constructor for the BackgroundRenderer.
     * Initializes the background object.
     */
    public BackgroundRenderer() {
        background = new Background();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        background.draw(g);
    }
}
