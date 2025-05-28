import javax.swing.*;
import java.awt.*;

public class Game {
    public static void main(String[] args) {
        // Make the game window
        JFrame frame = new JFrame("Hare's Ascent");
        JPanel mainPanel = new JPanel();

        // Set the main panel to use CardLayout for switching between different screens
        CardLayout cardLayout = new CardLayout();
        mainPanel.setLayout(cardLayout);

        GamePanel gamePanel = new GamePanel(mainPanel, cardLayout);

        mainPanel.add(new TitleScreenPanel(mainPanel, cardLayout, gamePanel), "title");
        mainPanel.add(gamePanel, "game");
        mainPanel.add(new WinPanel(mainPanel, cardLayout, gamePanel), "win");

        // Set up the frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(mainPanel);
        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // Show the title screen
        cardLayout.show(mainPanel, "title");
    }
}
