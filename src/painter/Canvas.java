package painter;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class Canvas extends JPanel implements MouseListener, MouseMotionListener {
    private ArrayList<PaintStroke> strokes = new ArrayList<>();
    private Color currentBrushColor;
    private Dimension currentBrushSize;

    public Canvas() {
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (currentBrushColor != null && currentBrushSize != null) {
            Point mousePos = e.getPoint();
            int x = mousePos.x - currentBrushSize.width / 2;
            int y = mousePos.y - currentBrushSize.height / 2;
            var stroke = new PaintStroke(new Point(x, y), currentBrushSize, currentBrushColor);

            strokes.add(stroke);
            repaint();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // Empty. Do Nothing.
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        var g2d = (Graphics2D) g;

        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, getWidth(), getHeight());

        for (PaintStroke stroke : strokes) {
            Point pos = stroke.getPosition();
            Dimension size = stroke.getSize();

            g2d.setColor(stroke.getColor());
            g2d.fillOval(pos.x, pos.y, size.width, size.height);
        }
    }

    public void clearCanvas() {
        strokes.clear();
        repaint();
    }

    public Color getCurrentBrushColor() {
        return currentBrushColor;
    }

    public void setCurrentBrushColor(Color color) {
        currentBrushColor = color;
    }

    public Dimension getCurrentBrushSize() {
        return currentBrushSize;
    }

    public void setCurrentBrushSize(Dimension size) {
        currentBrushSize = size;
    }

    public ArrayList<PaintStroke> getStrokes() {
        return strokes;
    }

    public void saveImage(File destination) throws IOException {
        int w = getWidth();
        int h = getHeight();
        var image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        var g2d = (Graphics2D) image.getGraphics();

        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, w, h);

        for (PaintStroke stroke : strokes) {
            Point pos = stroke.getPosition();
            Dimension size = stroke.getSize();
            g2d.setColor(stroke.getColor());
            g2d.fillOval(pos.x, pos.y, size.width, size.height);
        }

        ImageIO.write(image, "PNG", destination);
    }

    public void saveCanvas(File destination) throws IOException {
        String serialized = PaintStroke.serialize(strokes);
        destination.createNewFile();

        var out = new FileOutputStream(destination);
        out.write(serialized.getBytes());
        out.flush();
        out.close();
    }

    public void loadCanvas(File source) throws IOException {
        var in = new FileInputStream(source);
        byte[] bytes = in.readAllBytes();
        in.close();

        strokes = PaintStroke.deserialize(new String(bytes));
        repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        mouseDragged(e);
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
