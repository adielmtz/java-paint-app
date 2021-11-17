package painter;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            var paint = new PaintApp();
            paint.start();
        }
        catch (Exception e) {
            System.err.printf("Exception setting up look n' feel: %s%n", e.getMessage());
            e.printStackTrace();
        }
    }
}
