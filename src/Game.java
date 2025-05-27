import javax.swing.*;
import java.awt.*;

public class Game {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Hare's Ascent");
        JPanel mainPanel = new JPanel();

        CardLayout cardLayout = new CardLayout();
        mainPanel.setLayout(cardLayout);

        GamePanel gamePanel = new GamePanel(mainPanel, cardLayout);

        mainPanel.add(new TitleScreenPanel(mainPanel, cardLayout, gamePanel), "title");
        mainPanel.add(gamePanel, "game");
        mainPanel.add(new WinPanel(mainPanel, cardLayout, gamePanel), "win");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(mainPanel);
        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        cardLayout.show(mainPanel, "title");
    }
}
