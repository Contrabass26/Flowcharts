import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Window extends JFrame implements ActionListener {

    // New, open and save buttons
    JButton new_btn = new JButton("New");
    JButton open_btn = new JButton("Open");
    JButton save_btn = new JButton("Save");
    JButton add_btn = new JButton("Add element");
    // Drawing panel
    Drawing draw_pnl = new Drawing();

    public Window() {
        // Window setup
        super();
        setSize(500, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());
        Constraints c = new Constraints();
        // New button
        c.new_btn();
        new_btn.addActionListener(this);
        add(new_btn, c);
        // Open button
        c.open_btn();
        open_btn.addActionListener(this);
        add(open_btn, c);
        // Save button
        c.save_btn();
        save_btn.addActionListener(this);
        add(save_btn, c);
        // Add button
        c.add_btn();
        add_btn.addActionListener(this);
        add(add_btn, c);
        // Drawing panel
        c.draw_pnl();
        add(draw_pnl, c);
        // Finalise setup
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == new_btn) {
            System.out.println("New");
        } else if (e.getSource() == open_btn) {
            System.out.println("Open");
        } else if (e.getSource() == save_btn) {
            System.out.println("Save");
        } else if (e.getSource() == add_btn) {
            draw_pnl.draw_list = !(draw_pnl.draw_list);
            draw_pnl.repaint();
        }
    }
}
