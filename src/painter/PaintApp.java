package painter;

import gui.Window;

import java.awt.*;

public final class PaintApp {
    private final Window window;
    private final ToolBox toolbox;
    private final Canvas canvas;

    public PaintApp() {
        window = new Window("PaintApp", new Dimension(1280, 720));
        window.setLayout(new GridBagLayout());
        window.setResizable(false);

        var gc = new GridBagConstraints();
        gc.fill = GridBagConstraints.BOTH;
        Container container = window.getContentPane();

        canvas = new Canvas();
        canvas.setBackground(Color.WHITE);
        gc.gridx = 0;
        gc.gridy = 0;
        gc.weightx = 3;
        gc.weighty = 100;
        container.add(canvas, gc);

        toolbox = new ToolBox(canvas);
        gc.gridx = 1;
        gc.weightx = 1;
        container.add(toolbox, gc);
    }

    public void start() {
        window.setVisible(true);
    }
}
