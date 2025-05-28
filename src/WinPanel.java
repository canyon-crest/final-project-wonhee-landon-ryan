import sprite.FontUtil;
import sprite.OutlineLabel;

import javax.swing.*;
import java.awt.*;

public class WinPanel extends JPanel {
    private final Background background;

    /**
     * Constructor for the WinPanel.
     * @param mainPanel the main panel that contains the card layout
     * @param cardLayout the CardLayout used for switching between panels
     * @param gamePanel the game panel to switch to when restarting the game
     */
    public WinPanel(JPanel mainPanel, CardLayout cardLayout, GamePanel gamePanel) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Create a label to display the win message
        JLabel winLabel = new OutlineLabel("You Win!", FontUtil.FONT.deriveFont(100f), Color.YELLOW, Color.BLACK, 6f);
        winLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);

        // Create a button to restart the game
        JButton restartButton = new JButton("Play Again");
        restartButton.setAlignmentX(JButton.CENTER_ALIGNMENT);
        restartButton.setFont(FontUtil.FONT);
        restartButton.setPreferredSize(new Dimension(200, 50));
        restartButton.setFocusPainted(false);

        restartButton.addActionListener(e -> {
            cardLayout.show(mainPanel, "game");
            gamePanel.startGameLoop();

            // Request focus for the game panel to capture key events
            gamePanel.setFocusable(true);
            gamePanel.requestFocusInWindow();
        });

        background = new Background();

        // Create a vertical box layout to center the win message and button
        Box box = Box.createVerticalBox();
        box.setAlignmentX(Component.CENTER_ALIGNMENT);
        box.add(Box.createVerticalGlue());
        box.add(winLabel);
        box.add(Box.createRigidArea(new Dimension(0, 100)));
        box.add(restartButton);
        box.add(Box.createVerticalGlue());
        add(box);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        background.draw(g);
    }
}
