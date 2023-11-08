package Project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

public class PieChart extends JFrame {
    private List<Slice> slices = new ArrayList<>();

    public PieChart(List<String> names, List<Integer> values) {
        if (names.size() != values.size()) {
            throw new IllegalArgumentException("Names and values lists must have the same size");
        }

        setTitle("LangLearn! - Learning Progress - Pie Chart");
        setResizable(true);
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                if (getExtendedState() != JFrame.MAXIMIZED_BOTH) {
                    int padding = 50;
                    setBounds(padding, padding,
                            Toolkit.getDefaultToolkit().getScreenSize().width - 2 * padding,
                            Toolkit.getDefaultToolkit().getScreenSize().height - 2 * padding);
                }
            }
        });

        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                repaint();
            }
        });

        for (int i = 0; i < names.size(); i++) {
            slices.add(new Slice(names.get(i), values.get(i), generateColor(i)));
        }
    }

    private Color generateColor(int index) {
        // This function will generate a color based on the given index.
        // Adjust this to produce the desired sequence of colors.
        float hue = (float) index / 10;
        return Color.getHSBColor(hue, 1.0f, 1.0f);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        drawPieChart(g, slices);
    }

    private void drawPieChart(Graphics g, List<Slice> slices) {
        int totalValue = 0;
        for (Slice slice : slices) {
            totalValue += slice.value;
        }

        int currentValue = 0;
        int startAngle;

        int centerX = this.getWidth() / 2;
        int centerY = this.getHeight() / 2;
        int radius = 300;

        for (Slice slice : slices) {
            startAngle = (int) (currentValue * 360 / totalValue);
            int arcAngle = (int) (slice.value * 360 / totalValue);

            g.setColor(slice.color);
            g.fillArc(centerX - radius, centerY - radius, 2 * radius, 2 * radius, startAngle, arcAngle);
            currentValue += slice.value;
        }

        int legendX = centerX + radius + 30;
        int legendY = centerY - slices.size() * 20 / 2;

        for (Slice slice : slices) {
            g.setColor(slice.color);
            g.fillRect(legendX, legendY, 20, 20);
            g.setColor(Color.BLACK);
            g.drawString(slice.name, legendX + 25, legendY + 15);
            legendY += 20;
        }
    }

    static class Slice {
        String name;
        int value;
        Color color;

        public Slice(String name, int value, Color color) {
            this.name = name;
            this.value = value;
            this.color = color;
        }
    }

//    public static void main(String[] args) {
//        EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                try {
//                    List<String> names = List.of("Red Segment", "Blue Segment", "Green Segment", "fkhdfhjdf");
//                    List<Integer> values = List.of(50, 30, 20, 10);
//                    PieChart frame = new PieChart(names, values);
//                    frame.setVisible(true);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//    }
//    public static void main(String[] args) {
//        List<String> names = List.of("Red Segment", "Blue Segment", "Green Segment", "fkhdfhjdf");
//        List<Integer> values = List.of(50, 30, 20, 10);
//        PieChart frame = new PieChart(names, values);
//        frame.setVisible(true);
//    }
}
