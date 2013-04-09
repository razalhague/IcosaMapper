/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.penny_craal.icosamapper.ui;

/**
 *
 * @author Ville Jokela & James Pearce
 */
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class UI extends JFrame {

    public UI() {
        initUI();
    }

    public final void initUI() {
        setLayout(null);

        JButton neww = new JButton(new ImageIcon("gfx/new.png"));
        neww.setBounds(25, 0, 32, 32);
        neww.setToolTipText("Adds a new thing");

        JButton close = new JButton("Close");
        close.setBounds(25, 32, 32, 32);

        JButton test = new JButton("Test");
        test.setBounds(25, 64, 32, 32);

        JButton place1 = new JButton("Test");
        place1.setBounds(25, 100, 32, 32);

        JButton place2 = new JButton("Test");
        place2.setBounds(25, 125, 32, 32);

        JButton place3 = new JButton("Test");
        place3.setBounds(25, 150, 32, 32);

        JButton place4 = new JButton("Test");
        place4.setBounds(25, 175, 32, 32);

        JButton place5 = new JButton("Test");
        place5.setBounds(25, 25, 32, 32);

        add(neww);
        add(close);
        add(test);
        add(place1);
        add(place2);
        add(place3);
        add(place4);
        add(place5);

        setTitle("Absolute positioning");
        setSize(300, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                UI ex = new UI();
                ex.setVisible(true);
            }
        });
    }
}