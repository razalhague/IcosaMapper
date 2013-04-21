package org.penny_craal.icosamapper.ui;

/**
 *
 * @author Ville Jokela & James Pearce
 */
import java.awt.BorderLayout;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JToolBar;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.BevelBorder;

public class UI extends JFrame {

    public UI() {
        initUI();
    }

    public final void initUI() {
//creating menubar and menus
        JMenuBar menubar = new JMenuBar();
        JMenu file = new JMenu("File");
        JMenu layer = new JMenu("Layers");
        JMenu view = new JMenu("View");
//adding the menubar
        setJMenuBar(menubar);

//creating components for the menus
        JMenuItem newF = new JMenuItem("New");
        
        JMenuItem open = new JMenuItem("Open");
        
        JMenuItem save = new JMenuItem("Save");
        
        JMenuItem saveas = new JMenuItem("Save As");
        
        JMenuItem quit = new JMenuItem("Exit");
        quit.setToolTipText("Exit application");
//adding menus into menubar
        menubar.add(file);
        menubar.add(layer);
        menubar.add(view);
//adding to the file menu components
        file.add(newF);
        file.add(open);
        file.add(save);
        file.add(saveas);
        file.add(quit);
//creating the toolbar
        JToolBar toolbar = new JToolBar();
        toolbar.setOrientation(JToolBar.VERTICAL);
        add(toolbar, BorderLayout.WEST);
//giving every button meaning
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
//adding all the buttons to the toolbar
        toolbar.add(draw);
        toolbar.add(fill);
        toolbar.add(subdivide);
        toolbar.add(unite);
        toolbar.add(zoomin);
        toolbar.add(zoomout);
        
        toolbar.add(neww);
        toolbar.add(duplicate);
        toolbar.add(rename);
        toolbar.add(properties);
        toolbar.add(delete);
        toolbar.add(underlay);
        
        JSpinner sizeS = new JSpinner(new SpinnerNumberModel(1, 1, 5, 1));//(initial value, minimum value, maximum value, step)
        add(sizeS);
        setLocationRelativeTo(toolbar);
        
//creating the main window
        setTitle("Icosa Mapper");
        setSize(800, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        
//Status Bar
        JPanel statusbar = new JPanel();
        statusbar.setBorder(new BevelBorder(BevelBorder.LOWERED));
        add(statusbar, BorderLayout.SOUTH);
        statusbar.setLayout(new BoxLayout(statusbar, BoxLayout.X_AXIS));
        JLabel statusLabel = new JLabel("status");
        statusLabel.setHorizontalAlignment(SwingConstants.LEFT);
        statusbar.add(statusLabel);

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
