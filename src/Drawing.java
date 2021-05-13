import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

public class Drawing extends JPanel implements MouseMotionListener, MouseListener {

    // Drawing
    static final double OFFSET = 0.2; // For parallelograms
    static final int[] ARC_SIZE = {50, 50}; // For rounded rectangles
    static final int[] DEFAULT_SIZE = {150, 50}; // Shape size at default zoom level
    public static int[] shape_size = DEFAULT_SIZE;
    static final int[] LIST_POS = {400, 0};
    static final int[] LIST_SIZE = {90, 70};
    
    // Colours
    static Color element_text = new Color(255, 255, 255);
    static Color terminal = new Color(255, 0, 0);
    static Color line = new Color(0, 0, 0);
    static Color io = new Color(0, 0, 255);
    static Color process = new Color(255, 0, 255);
    static Color decision = new Color(255, 255, 0);
    static Color[] list_background = new Color[]{new Color(179, 179, 179), new Color(141, 141, 141), new Color(50, 50, 50)};
    static Color list_text = new Color(0, 0, 0);
    
    List<Element> elements = new ArrayList<>(); // List of flowchart elements
    int selected_element = -1;
    int[] selected_coords = new int[2];
    boolean draw_list = false; // Whether to draw the list of elements to add
    int selected_item = -1;
    // Possible elements
    String[] to_write = {
            "Terminal",
            "Input/output",
            "Process",
            "Decision"
    };

    public Drawing() {
        elements.add(new Element(0, "Start", new int[]{20, 20}));
        elements.add(new Element(1, "Test", new int[]{20, 150}));
        elements.get(0).connect(elements.get(1));
        this.addMouseMotionListener(this);
        this.addMouseListener(this);
    }

    public void paint(Graphics graphics) {
        Graphics2D g = (Graphics2D) graphics;
        for (Element e : elements) {
            // Shape
            switch (e.type) {
                case 0 -> {
                    g.setColor(terminal);
                    rounded_rect(g, e.position);
                }
                case 1 -> {
                    g.setColor(io);
                    parallelogram(g, e.position);
                }
                case 2 -> {
                    g.setColor(process);
                    g.fillRect(e.get_pos(0), e.get_pos(1), shape_size[0], shape_size[1]);
                }
                case 3 -> {
                    g.setColor(decision);
                    diamond(g, e.position);
                }
            }
            g.setColor(element_text);
            g.drawString(e.text, e.get_pos(0) + shape_size[0] / 2, e.get_pos(1) + shape_size[1] / 2);
            // Connecting lines
            for (Element f : e.connections) {
                // Set colour and thickness
                g.setColor(line);
                g.setStroke(new BasicStroke(2));
                // Calculate angle of line
                int x = f.get_pos(0) - e.get_pos(0);
                int y = f.get_pos(1) - e.get_pos(1);
                double angle = Math.toDegrees(Math.atan(x / (y * 1.0)));
                int nearest = (int) (Math.abs(Math.round(angle / 90) * 90));
                // Calculate where line should connect to each shape
                switch (nearest) {
                    case 0 -> {
                        if (y > 0) {
                            // e is above f
                            g.drawLine(e.bottom()[0], e.bottom()[1], f.top()[0], f.top()[1]);
                        } else {
                            // e is below f
                            g.drawLine(e.top()[0], e.top()[1], f.bottom()[0], f.bottom()[1]);
                        }
                    }
                    case 90 -> {
                        if (x > 0) {
                            // e is to the left of f
                            g.drawLine(e.right()[0], e.right()[1], f.left()[0], f.left()[1]);
                        } else {
                            // e is to the right of f
                            g.drawLine(e.left()[0], e.left()[1], f.right()[0], f.right()[1]);
                        }
                    }
                }
            }
        }
        // Draw list of elements (if applicable)
        if (draw_list) {
            int spacing = LIST_SIZE[1] / to_write.length;
            int y = LIST_POS[1] + spacing;
            g.setColor(list_text);
            for (int i = 0; i < to_write.length; i++) {
                String str = to_write[i];
                // Draw background
                g.setColor(list_background[i % 2]); // Alternate background colour
                if (i == selected_item) g.setColor(list_background[2]);
                g.fillRect(LIST_POS[0], LIST_POS[1] + y - spacing, LIST_SIZE[0], spacing);
                // Draw text
                g.setColor(list_text);
                g.drawString(str, LIST_POS[0] + 5, y - 5);
                // Increase y coordinate for next time
                y += spacing;
            }
        }
    }

    public void parallelogram(Graphics g, int[] start) {
        int[] x_points = {
                (int) (start[0] + shape_size[0] * OFFSET / 2),
                (int) (start[0] + shape_size[0] + shape_size[0] * OFFSET / 2),
                (int) (start[0] + shape_size[0] - shape_size[0] * OFFSET / 2),
                (int) (start[0] - shape_size[0] * OFFSET / 2)
        };
        int[] y_points = {
                start[1],
                start[1],
                start[1] + shape_size[1],
                start[1] + shape_size[1]
        };
        g.fillPolygon(x_points, y_points, 4);
    }

    public void rounded_rect(Graphics g, int[] start) {
        g.fillRoundRect(start[0], start[1], shape_size[0], shape_size[1], ARC_SIZE[0], ARC_SIZE[0]);
    }

    public void diamond(Graphics g, int[] start) {
        int[] x_points = {
                start[0] + shape_size[0] / 2,
                start[0] + shape_size[0],
                start[0] + shape_size[0] / 2,
                start[0]
        };
        int[] y_points = {
                start[1],
                start[1] + shape_size[1] / 2,
                start[1] + shape_size[1],
                start[1] + shape_size[1] / 2
        };
        g.fillPolygon(x_points, y_points, 4);
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // Move an element if one is selected
        if (selected_element != -1) {
            elements.get(selected_element).position = new int[]{e.getX() - selected_coords[0], e.getY() - selected_coords[1]};
            repaint();
        }
        // Check that mouse coordinates are within bounds of list
        if (draw_list && e.getX() >= LIST_POS[0] && e.getX() <= LIST_POS[0] + LIST_SIZE[0] && e.getY() >= LIST_POS[1] && e.getY() <= LIST_POS[1] + LIST_SIZE[1]) {// Get y coordinate within list
            int y = e.getY() - LIST_POS[1];
            // Calculate list item spacing
            int spacing = LIST_SIZE[1] / to_write.length;
            // Check if mouse is on each item
            for (int i = 0; i < to_write.length; i++) {
                if (y < spacing * (i + 1)) {
                    // The mouse was at index i in the list
                    selected_item = i;
                    repaint();
                    break;
                }
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // Check whether mouse coordinates are within bounds of list
        if (draw_list && e.getX() >= LIST_POS[0] && e.getX() <= LIST_POS[0] + LIST_SIZE[0] && e.getY() >= LIST_POS[1] && e.getY() <= LIST_POS[1] + LIST_SIZE[1]) {
            if (selected_item != -1) {
                // Add new element
                elements.add(new Element(selected_item, "New element", new int[]{300, 300}));
                draw_list = false;
                selected_item = -1;
                repaint();
            }
        } else {
            // Check whether each flowchart element has been clicked
            for (int i = 0; i < elements.size(); i++) {
                Element el = elements.get(i);
                if (e.getX() >= el.position[0] && e.getX() <= el.position[0] + shape_size[0] && e.getY() >= el.position[1] && e.getY() <= el.position[1] + shape_size[1]) {
                    // The element was clicked
                    if (selected_element == -1) selected_element = i;
                    else selected_element = -1; selected_coords = new int[2];
                    selected_coords = new int[]{e.getX() - el.position[0], e.getY() - el.position[1]};
                }
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
