import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.w3c.dom.Text;

public class Window extends JFrame implements ActionListener {
    private JPanel optionPanel;
    private JLabel label1;
    private JLabel label2;
    private JLabel label3;
    private JTextField setX;
    private JTextField setY;
    private JTextField degree;
    private JButton BTKButton;
    private JButton resultButton;
    private JButton WarnsdorffButton; 
    private ChessBoard component;
    private Thread thread;


    public Window(int x, int y) {
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setTitle("Chess");
        this.setResizable(false);
        component = new ChessBoard(x, y, this);
        this.add(component, BorderLayout.CENTER);

        setMenu();
        this.add(optionPanel, BorderLayout.NORTH);

        setX.addActionListener(this);
        setY.addActionListener(this);
        BTKButton.addActionListener(this);
        WarnsdorffButton.addActionListener(this);
        resultButton.addActionListener(this);

        this.setLocation(200, 50);
        this.pack();
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void setMenu() {
        optionPanel = new JPanel();
        optionPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 20));

        label1 = new JLabel("X:");
        label2 = new JLabel("Y:");
        label3 = new JLabel("Degree: ");

        setX = new JTextField(5); 
        setY = new JTextField(5); 
        degree = new JTextField(5);
        degree.setEditable(false);

        BTKButton = new JButton("BTK");
        WarnsdorffButton = new JButton("Warnsdorff");
        resultButton = new JButton("result");


        optionPanel.add(label1);
        optionPanel.add(setX);
        optionPanel.add(label2);
        optionPanel.add(setY);
        optionPanel.add(label3);
        optionPanel.add(degree);
        optionPanel.add(BTKButton); 
        optionPanel.add(WarnsdorffButton);
        optionPanel.add(resultButton);
    }

    public void setDegreeShow(String s) {
        degree.setText(s);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == BTKButton) {
            try {
                int x = Integer.parseInt(setX.getText());
                int y = Integer.parseInt(setY.getText());

                if (thread != null && thread.isAlive()) {
                    System.out.println("end");
                    thread.interrupt();
                }

                this.remove(component);

                component = new ChessBoard(x, y, this);

                this.add(component, BorderLayout.CENTER);

                this.revalidate();
                this.repaint();

                thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            component.KnightTour1(x, y, 1);
                        } catch (InterruptedException err) {
                            err.printStackTrace();
                        }
                    }
                });

                thread.start();

            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(null, "Please enter valid integers for X and Y");
            }
        } else if (e.getSource() == resultButton) {
            try {
                int x = Integer.parseInt(setX.getText());
                int y = Integer.parseInt(setY.getText());

                if (thread != null && thread.isAlive()) {
                    System.out.println("end");
                    thread.interrupt();
                }

                this.remove(component);

                component = new ChessBoard(x, y, this);

                this.add(component, BorderLayout.CENTER);

                this.revalidate();
                this.repaint();

                thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        component.KnightTour2(x, y, 1);
                    }
                });

                thread.start();

            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(null, "Please enter valid integers for X and Y");
            }
        } else if (e.getSource() == WarnsdorffButton) {
            try {
                int x = Integer.parseInt(setX.getText());
                int y = Integer.parseInt(setY.getText());

                if (thread != null && thread.isAlive()) {
                    System.out.println("end");
                    thread.interrupt();
                }

                this.remove(component);

                component = new ChessBoard(x, y, this);

                this.add(component, BorderLayout.CENTER);

                this.revalidate();
                this.repaint();

                thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            component.KnightTour(x, y, 1);
                        } catch (InterruptedException err) {
                            err.printStackTrace();
                        }
                    }
                });

                thread.start();

            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(null, "Please enter valid integers for X and Y");
            }
        }
    }
}