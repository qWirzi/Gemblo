package Gemblo;

import java.awt.*;
import java.awt.geom.Path2D;

/**
 * Helping to draw a single hexagon on the board with the given center coordinates, size, color and text
 */
public class Hexagon {
    private final double x, y; // center coordinates
    private final int size;    // size of the hexagon side
    private final Color color;
    private String text;
    private int angle;

    public Hexagon(double x, double y, int size, Color color, int r, int c, int angle) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.color = color;
        this.text = r + "," + c;
        this.angle = angle;
    }

    public void draw(Graphics2D g2) {
        double[] xPoints = new double[6];
        double[] yPoints = new double[6];

        for (int i = 0; i < 6; i++) {
            xPoints[i] = x + (size * Math.cos(i * Math.PI / 3 + Math.PI / 6)); // Rotate by 60 degrees
            yPoints[i] = y + (size * Math.sin(i * Math.PI / 3 + Math.PI / 6));
        }

        Path2D hexagon = new Path2D.Double();
        hexagon.moveTo(xPoints[0], yPoints[0]);
        for (int i = 1; i < 6; i++) {
            hexagon.lineTo(xPoints[i], yPoints[i]);
        }
        hexagon.closePath();

        g2.setColor(color);
        g2.fill(hexagon);
        g2.setColor(Color.BLACK);
        g2.draw(hexagon);

    }

    public void drawText(Graphics2D g2) {

        // Draw the text in the center of the hexagon
        FontMetrics fm = g2.getFontMetrics();
        int textWidth = fm.stringWidth(text);
        int textHeight = fm.getAscent();
        int textX = (int) (x - textWidth / 2);
        int textY = (int) (y + textHeight / 4); // adjust this to center vertically

        g2.drawString(text, textX, textY);
    }

    public void drawIndex(Graphics2D g2, String index) {

        if (index == "-1") {
            return;
        }

        // Draw the text in the center of the hexagon
        FontMetrics fm = g2.getFontMetrics();
        int textWidth = fm.stringWidth(index);
        int textHeight = fm.getAscent();
        int textX = (int) (x - textWidth / 2);
        int textY = (int) (y + textHeight / 4); // adjust this to center vertically

        g2.drawString(index, textX, textY);
    }

    public void drawDot(Graphics2D g2d) {
        int dotRadius = 2; // Radius of the dot
        g2d.fillOval((int) (x - dotRadius), (int) (y - dotRadius), 2 * dotRadius, 2 * dotRadius);
    }

    public void drawAngle(Graphics2D g2) {
        FontMetrics fm = g2.getFontMetrics();
        int textWidth = fm.stringWidth(Integer.toString(angle));
        int textHeight = fm.getAscent();
        int textX = (int) (x - textWidth / 2);
        int textY = (int) (y + textHeight / 4); // adjust this to center vertically

        g2.drawString(Integer.toString(angle), textX, textY);
    }

    public void setAngle(int newAngle) {
        this.angle = newAngle;
    }
}


