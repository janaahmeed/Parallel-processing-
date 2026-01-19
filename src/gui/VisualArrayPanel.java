package gui;

import javax.swing.*;
import java.awt.*;

public class VisualArrayPanel extends JPanel {

    private int[] array;

    public void setArray(int[] array) {
        this.array = array;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (array == null) return;

        int width = getWidth();
        int height = getHeight();
        int n = array.length;
        int barWidth = Math.max(1, width / n);

        int max = 1;
        for (int x : array) max = Math.max(max, x);

        for (int i = 0; i < n; i++) {
            int barHeight = (int) ((array[i] / (double) max) * (height - 20));
            g.setColor(Color.BLUE);
            g.fillRect(i * barWidth, height - barHeight, barWidth, barHeight);
        }
    }
}
