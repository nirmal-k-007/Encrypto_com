import java.awt.*;
import javax.swing.*;

public class FlowLayoutExample {
    public static void main(String[] args) {
        JFrame frame = new JFrame("FlowLayout Example");
        frame.setLayout(new FlowLayout());
        Button button = new Button("Click Me");

        frame.add(button);
        frame.add(new Button("Button 2"));
        frame.add(new Button("Button 3"));

        frame.setSize(400, 200);
        frame.getSize();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
