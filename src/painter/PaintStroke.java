package painter;

import java.awt.*;
import java.util.ArrayList;
import java.util.Scanner;

public class PaintStroke {
    private final Point position;
    private final Dimension size;
    private final Color color;

    public static String serialize(ArrayList<PaintStroke> strokeList) {
        var sb = new StringBuilder();
        for (var stroke : strokeList) {
            // Serialize stroke position
            Point pos = stroke.getPosition();
            sb.append(String.format("%d,%d:", pos.x, pos.y));

            // Serialize brush size
            Dimension size = stroke.getSize();
            sb.append(String.format("%d,%d:", size.width, size.height));

            // Serialize brush color
            Color color = stroke.getColor();
            sb.append(String.format("%d,%d,%d;", color.getRed(), color.getGreen(), color.getBlue()));
        }

        return sb.toString();
    }

    public static ArrayList<PaintStroke> deserialize(String payload) throws NumberFormatException {
        var strokeList = new ArrayList<PaintStroke>();
        var scanner = new Scanner(payload);
        scanner.useDelimiter(";");

        while (scanner.hasNext()) {
            String serialized = scanner.next();
            String[] components = serialized.split(":");
            String[] posComponent = components[0].split(",");
            String[] sizeComponent = components[1].split(",");
            String[] colorComponent = components[2].split(",");

            // point components
            int x = Integer.parseInt(posComponent[0]);
            int y = Integer.parseInt(posComponent[1]);
            var point = new Point(x, y);

            // size components
            int width = Integer.parseInt(sizeComponent[0]);
            int height = Integer.parseInt(sizeComponent[1]);
            var dim = new Dimension(width, height);

            // color components
            int r = Integer.parseInt(colorComponent[0]);
            int g = Integer.parseInt(colorComponent[1]);
            int b = Integer.parseInt(colorComponent[2]);
            var color = new Color(r, g, b);
            strokeList.add(new PaintStroke(point, dim, color));
        }

        scanner.close();
        return strokeList;
    }

    public PaintStroke(Point position, Dimension size, Color color) {
        this.position = position;
        this.size = size;
        this.color = color;
    }

    public Point getPosition() {
        return position;
    }

    public Dimension getSize() {
        return size;
    }

    public Color getColor() {
        return color;
    }
}
