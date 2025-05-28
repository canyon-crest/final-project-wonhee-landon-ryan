import sprite.FontUtil;
import sprite.OutlineLabel;

import javax.swing.*;
import java.awt.*;

public class TitleScreenPanel extends BackgroundRenderer {
    /**
     * Constructor for the TitleScreenPanel.
     * @param mainPanel the main panel that contains the card layout
     * @param cardLayout the CardLayout used for switching between panels
     * @param gamePanel the game panel to switch to when starting the game
     */
    public TitleScreenPanel(JPanel mainPanel, CardLayout cardLayout, GamePanel gamePanel) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Set the background color and size
        JLabel titleLabel = new OutlineLabel("Hare's Ascent", FontUtil.FONT.deriveFont(120f), Color.WHITE, Color.BLACK, 6f);
        titleLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);

        // Create a button to start the game
        JButton startButton = new JButton("Start Game");
        startButton.setAlignmentX(JButton.CENTER_ALIGNMENT);
        startButton.setFont(FontUtil.FONT);
        startButton.setPreferredSize(new Dimension(200, 50));
        startButton.setFocusPainted(false);

        startButton.addActionListener(e -> {
            cardLayout.show(mainPanel, "game");
            gamePanel.startGameLoop();

            // Request focus for the game panel to capture key events
            gamePanel.setFocusable(true);
            gamePanel.requestFocusInWindow();
        });

        // Create a vertical box layout to center the title and button
        Box box = Box.createVerticalBox();
        box.setAlignmentX(Component.CENTER_ALIGNMENT);
        box.add(Box.createVerticalGlue());
        box.add(titleLabel);
        box.add(Box.createRigidArea(new Dimension(0, 100)));
        box.add(startButton);
        box.add(Box.createVerticalGlue());
        add(box);
    }
}
