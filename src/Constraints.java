import java.awt.*;

public class Constraints extends GridBagConstraints {


    public void new_btn() {
        gridx = 1;
        gridy = 1;
        gridwidth = 1;
        weightx = 1;
        weighty = 0;
        anchor = PAGE_START;
        insets = new Insets(0, 0, 0, 0);
        fill = HORIZONTAL;
    }

    public void open_btn() {
        gridx = 2;
        gridy = 1;
        gridwidth = 1;
        weightx = 1;
        weighty = 0;
        anchor = PAGE_START;
        insets = new Insets(0, 0, 0, 0);
        fill = HORIZONTAL;
    }

    public void save_btn() {
        gridx = 3;
        gridy = 1;
        gridwidth = 1;
        weightx = 1;
        weighty = 0;
        anchor = PAGE_START;
        insets = new Insets(0, 0, 0, 0);
        fill = HORIZONTAL;
    }

    public void add_btn() {
        gridx = 4;
        gridy = 1;
        gridwidth = 1;
        weightx = 1;
        weighty = 0;
        anchor = PAGE_START;
        insets = new Insets(0, 0, 0, 0);
        fill = HORIZONTAL;
    }

    public void draw_pnl() {
        gridx = 1;
        gridy = 2;
        gridwidth = 4;
        weightx = 1;
        weighty = 1;
        anchor = PAGE_START;
        insets = new Insets(0, 0, 0, 0);
        fill = BOTH;
    }
}
