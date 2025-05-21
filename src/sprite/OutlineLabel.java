package sprite;

import javax.swing.*;
import java.awt.*;

public class OutlineLabel extends JLabel {
    private Color outlineColor;
    private int outlineWidth; // use int for pixel-perfect offset

    public OutlineLabel(String text, Font font, Color textColor, Color outlineColor, float outlineWidth) {
        super(text);
        setFont(font);
        setForeground(textColor);
        this.outlineColor = outlineColor;
        this.outlineWidth = Math.max(1, Math.round(outlineWidth)); // round to int at least 1 pixel
        setOpaque(false);
        int pad = this.outlineWidth;
        setBorder(BorderFactory.createEmptyBorder(pad, pad, pad, pad));
    }

    @Override
    protected void paintComponent(Graphics g) {
        String text = getText();
        if (text == null || text.isEmpty()) {
            super.paintComponent(g);
            return;
        }

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setFont(getFont());

        // Disable antialiasing for pixelated effect
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);

        FontMetrics fm = g2.getFontMetrics();
        int x = getInsets().left;
        int y = getInsets().top + fm.getAscent();

        // Draw outline by drawing the text multiple times shifted in 8 directions
        g2.setColor(outlineColor);
        for (int dx = -outlineWidth; dx <= outlineWidth; dx++) {
            for (int dy = -outlineWidth; dy <= outlineWidth; dy++) {
                if (dx == 0 && dy == 0) continue;
                g2.drawString(text, x + dx, y + dy);
            }
        }

        // Draw main text on top
        g2.setColor(getForeground());
        g2.drawString(text, x, y);

        g2.dispose();
    }
}
