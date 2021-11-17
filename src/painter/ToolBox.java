package painter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

public class ToolBox extends JPanel {
    private final static Dimension DEFAULT_TEXTAREA_SIZE = new Dimension(30, 20);

    private final GridBagConstraints toolboxConstraints;
    private final Canvas canvas;

    private JPanel colorPreview;
    private JTextField redValue;
    private JTextField greenValue;
    private JTextField blueValue;

    private JTextField widthValue;
    private JTextField heightValue;

    public ToolBox(Canvas canvas) {
        setLayout(new GridBagLayout());
        toolboxConstraints = new GridBagConstraints();
        this.canvas = canvas;

        setupColorPicker();
        setupBrushSizePicker();
        setupApplyChangesButton();
        setupActionButtons();
        applyCurrentToolboxSettings();
    }

    public Color getBrushColor() throws NumberFormatException {
        int r = Integer.parseInt(redValue.getText());
        int g = Integer.parseInt(greenValue.getText());
        int b = Integer.parseInt(blueValue.getText());
        return new Color(r, g, b);
    }

    public Dimension getBrushSize() throws NumberFormatException {
        int w = Integer.parseInt(widthValue.getText());
        int h = Integer.parseInt(heightValue.getText());
        return new Dimension(w, h);
    }

    public void applyCurrentToolboxSettings() {
        canvas.setCurrentBrushColor(getBrushColor());
        canvas.setCurrentBrushSize(getBrushSize());
        System.out.println("New settings applied.");
    }

    private void setupColorPicker() {
        var panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        var gc = new GridBagConstraints();

        colorPreview = new JPanel();
        colorPreview.setPreferredSize(new Dimension(50, 50));
        colorPreview.setBackground(Color.BLACK);
        gc.gridx = 2;
        gc.gridy = 1;
        gc.gridheight = 3;
        panel.add(colorPreview, gc);

        var keyListener = new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                // Do Nothing.
            }

            @Override
            public void keyPressed(KeyEvent e) {
                // Do Nothing.
            }

            @Override
            public void keyReleased(KeyEvent e) {
                try {
                    // Update color preview
                    Color brushColor = getBrushColor();
                    colorPreview.setBackground(brushColor);
                }
                catch (NumberFormatException err) {
                    System.err.println("[KeyReleased] Integer Parse error.");
                }
            }
        };

        gc.gridx = 0;
        gc.gridy = 0;
        gc.gridwidth = 2;
        gc.gridheight = 1;
        panel.add(new JLabel("Brush color:"), gc);

        gc.gridwidth = 1;
        gc.gridy = 1;
        panel.add(new JLabel("R"), gc);

        gc.gridy = 2;
        panel.add(new JLabel("G"), gc);

        gc.gridy = 3;
        panel.add(new JLabel("B"), gc);

        redValue = new JTextField("0");
        redValue.setPreferredSize(DEFAULT_TEXTAREA_SIZE);
        redValue.addKeyListener(keyListener);
        gc.gridx = 1;
        gc.gridy = 1;
        panel.add(redValue, gc);

        greenValue = new JTextField("0");
        greenValue.setPreferredSize(DEFAULT_TEXTAREA_SIZE);
        greenValue.addKeyListener(keyListener);
        gc.gridx = 1;
        gc.gridy = 2;
        panel.add(greenValue, gc);

        blueValue = new JTextField("0");
        blueValue.setPreferredSize(DEFAULT_TEXTAREA_SIZE);
        blueValue.addKeyListener(keyListener);
        gc.gridx = 1;
        gc.gridy = 3;
        panel.add(blueValue, gc);

        toolboxConstraints.gridx = 0;
        toolboxConstraints.gridy = 1;
        add(panel, toolboxConstraints);
    }

    private void setupBrushSizePicker() {
        var panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        var gc = new GridBagConstraints();

        gc.gridx = 0;
        gc.gridy = 0;
        gc.gridwidth = 2;
        panel.add(new JLabel("Brush size:"), gc);

        gc.gridwidth = 1;
        gc.gridy = 1;
        panel.add(new JLabel("W"), gc);

        gc.gridy = 2;
        panel.add(new JLabel("H"), gc);

        widthValue = new JTextField("10");
        widthValue.setPreferredSize(DEFAULT_TEXTAREA_SIZE);
        gc.gridx = 1;
        gc.gridy = 1;
        panel.add(widthValue, gc);

        heightValue = new JTextField("10");
        heightValue.setPreferredSize(DEFAULT_TEXTAREA_SIZE);
        gc.gridx = 1;
        gc.gridy = 2;
        panel.add(heightValue, gc);

        toolboxConstraints.gridx = 0;
        toolboxConstraints.gridy = 2;
        add(panel, toolboxConstraints);
    }

    private void setupApplyChangesButton() {
        JButton applyChanges = new JButton("Apply");
        applyChanges.addActionListener(e -> applyCurrentToolboxSettings());

        toolboxConstraints.gridx = 0;
        toolboxConstraints.gridy = 3;
        add(applyChanges, toolboxConstraints);
    }

    private void setupActionButtons() {
        var panel = new JPanel();
        panel.setLayout(new GridLayout(4, 1));

        JButton saveImage = new JButton("Save as PNG");
        saveImage.addActionListener(e -> {
            try {
                var chooser = new JFileChooser("Select the destination file.");
                int selection = chooser.showSaveDialog(new JFrame());

                if (selection == JFileChooser.APPROVE_OPTION) {
                    canvas.saveImage(chooser.getSelectedFile());
                }

                System.out.printf("Image saved as \"%s\"%n", chooser.getSelectedFile().getAbsolutePath());
            }
            catch (IOException err) {
                System.err.printf("Error saving image: %s%n", err.getMessage());
            }
        });
        panel.add(saveImage);

        JButton saveCanvas = new JButton("Save canvas");
        saveCanvas.addActionListener(e -> {
            try {
                var chooser = new JFileChooser("Select the destination file.");
                int selection = chooser.showSaveDialog(new JFrame());

                if (selection == JFileChooser.APPROVE_OPTION) {
                    canvas.saveCanvas(chooser.getSelectedFile());
                }

                System.out.printf("Canvas serialized and saved as \"%s\"%n", chooser.getSelectedFile().getAbsolutePath());
            }
            catch (IOException err) {
                System.err.printf("Error saving canvas: %s%n", err.getMessage());
            }
        });
        panel.add(saveCanvas);

        JButton loadCanvas = new JButton("Load canvas");
        loadCanvas.addActionListener(e -> {
            try {
                var chooser = new JFileChooser("Select a file.");
                int selection = chooser.showOpenDialog(new JFrame());

                if (selection == JFileChooser.APPROVE_OPTION) {
                    canvas.loadCanvas(chooser.getSelectedFile());
                }

                System.out.println("Canvas successfully loaded.");
            }
            catch (IOException err) {
                System.err.printf("[loadCanvas] Error saving image: %s%n", err.getMessage());
            }
        });
        panel.add(loadCanvas);

        JButton eraseCanvas = new JButton("Clear canvas");
        eraseCanvas.addActionListener(e -> {
            canvas.clearCanvas();
            System.out.println("Canvas cleared.");
        });
        panel.add(eraseCanvas);

        // Add to window
        toolboxConstraints.gridx = 0;
        toolboxConstraints.gridy = 4;
        toolboxConstraints.insets = new Insets(20, 0, 0, 0);
        add(panel, toolboxConstraints);
    }
}
