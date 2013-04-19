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
        setJMenuBar(menubar);
        
        
        JMenuItem ville = new JMenuItem("Ville");
        
        
        JMenuItem exit = new JMenuItem("Exit");
        exit.setToolTipText("Exit application");
        
        menubar.add(menu);
        
        menu.add(ville);
        menu.add(exit);

        JToolBar toolbar = new JToolBar();
        toolbar.setOrientation(JToolBar.VERTICAL);
        add(toolbar, BorderLayout.WEST);
        
        JButton neww = new JButton(new ImageIcon("gfx/new.png"));
        neww.setToolTipText("Adds a new thing");

        JButton delete = new JButton(new ImageIcon("gfx/delete.png"));
        delete.setToolTipText("Deletes a thing");

        JButton draw = new JButton(new ImageIcon("gfx/draw.png"));
        draw.setToolTipText("Draws a thing");
        
        JButton duplicate = new JButton(new ImageIcon("gfx/duplicate.png"));
        duplicate.setToolTipText("Dublicates a thing");
        
        JButton fill = new JButton(new ImageIcon("gfx/fill.png"));
        fill.setToolTipText("Fills a thing");

        JButton properties = new JButton(new ImageIcon("gfx/properties.png"));
        properties.setToolTipText("Shows the properties of a thing");

        JButton rename = new JButton(new ImageIcon("gfx/rename.png"));
        rename.setToolTipText("Renames a thing");

        JButton subdivide = new JButton(new ImageIcon("gfx/subdivide.png"));
        subdivide.setToolTipText("Subdivides a thing");
        
        JButton underlay = new JButton(new ImageIcon("gfx/underlay.png"));
        underlay.setToolTipText("to be added");//TODO
        
        JButton unite = new JButton(new ImageIcon("gfx/unite.png"));
        unite.setToolTipText("Unites a thing");
        
        JButton zoomin = new JButton(new ImageIcon("gfx/zoom-in.png"));
        zoomin.setToolTipText("Zooms in");
        
        JButton zoomout = new JButton(new ImageIcon("gfx/zoom-out.png"));
        zoomout.setToolTipText("zooms out");
   
        toolbar.add(neww);
        toolbar.add(delete);
        toolbar.add(draw);
        toolbar.add(duplicate);
        toolbar.add(fill);
        toolbar.add(properties);
        toolbar.add(rename);
        toolbar.add(subdivide);
        toolbar.add(underlay);
        toolbar.add(unite);
        toolbar.add(zoomin);
        toolbar.add(zoomout);
         
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
