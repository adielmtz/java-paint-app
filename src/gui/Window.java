package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;

public final class Window extends JFrame {
    public Window(String title, Dimension size) {
        super(title);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // Locate window at the center of the screen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(screenSize.width / 2 - size.width / 2, screenSize.height / 2 - size.height / 2);
        setSize(size);
    }

    public void close() {
        dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }
}
