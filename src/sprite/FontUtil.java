package sprite;

import java.awt.*;
import java.io.FileInputStream;

public class FontUtil {
    ///  Font for the game
    public static final Font FONT;

    static {
        // Load the font from the assets directory
        try (FileInputStream fis = new FileInputStream("./assets/font/ByteBounce.ttf")) {
            FONT = Font.createFont(Font.TRUETYPE_FONT, fis).deriveFont(32f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(FONT);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load font", e);
        }
    }
}
