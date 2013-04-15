package org.penny_craal.icosamapper.ui;

/**
 *
 * @author Ville Jokela & James Pearce
 */
import java.awt.BorderLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;

public class UI extends JFrame {

    public UI() {
        initUI();
    }

    public final void initUI() {
        JPanel panel = new JPanel();
        
        JMenuBar menubar = new JMenuBar();
        JMenu menu = new JMenu("Menu");
        menubar.add(menu);
        setJMenuBar(menubar);
        JToolBar toolbar = new JToolBar();
        toolbar.setOrientation(JToolBar.VERTICAL);

        
        JMenuItem eMenuItem = new JMenuItem("Ville");
        eMenuItem.setToolTipText("Exit application");
        
        menu.add(eMenuItem);

        menubar.add(menu);

        add(toolbar, BorderLayout.WEST);

        setTitle("Simple toolbar");
        setLocationRelativeTo(null);

        
        JButton neww = new JButton(new ImageIcon("gfx/new.png"));
        neww.setToolTipText("Adds a new thing");

        JButton delete = new JButton(new ImageIcon("gfx/delete.png"));
        delete.setToolTipText("Deletes a thing");

        JButton draw = new JButton(new ImageIcon("gfx/draw.png"));

        JButton duplicate = new JButton(new ImageIcon("gfx/duplicate.png"));

        JButton fill = new JButton(new ImageIcon("gfx/fill.png"));

        JButton properties = new JButton(new ImageIcon("gfx/properties.png"));

        JButton rename = new JButton(new ImageIcon("gfx/rename.png"));

        JButton subdivide = new JButton(new ImageIcon("gfx/subdivide.png"));
   
        toolbar.add(neww);
        toolbar.add(delete);
        toolbar.add(draw);
        toolbar.add(duplicate);
        toolbar.add(fill);
        toolbar.add(properties);
        toolbar.add(rename);
        toolbar.add(subdivide);
         
        setTitle("Icosa Mapper");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                UI ex = new UI();
                ex.setVisible(true);
            }
        });
    }
}
