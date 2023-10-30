import java.awt.BorderLayout;
import java.awt.Color;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Window extends JFrame {
    public Window(int x, int y) {
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setTitle("Chess");
        this.setResizable(false);
        ChessBoard component = new ChessBoard(x, y);
        this.add(component, BorderLayout.CENTER);
        
        this.setLocation(200, 50);
        this.pack();
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        try {
            component.KnightTour(x, y, 1);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}