package org.penny_craal.icosamapper.ui;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 *
 * @author Ville
 */
public class MenuBar extends JMenuBar {
    private JMenu file;
    private JMenuItem newM;
    private JMenuItem open;
    private JMenuItem save;
    private JMenuItem saveAs;
    private JMenuItem exit;
    
    private JMenu help;
    private JMenuItem about;
    
    public MenuBar() {
        file = new JMenu("File");
        newM = new JMenuItem("New Map");
        open = new JMenuItem("Open Map...");
        save = new JMenuItem("Save Map");
        saveAs = new JMenuItem("Save Map As...");
        exit = new JMenuItem("Exit");
        exit.setToolTipText("Exit IcosaMapper");
        
        file.add(newM);
        file.add(open);
        file.add(save);
        file.add(saveAs);
        file.add(exit);
        
        help = new JMenu("Help");
        about = new JMenuItem("About");
        
        help.add(about);

        add(file);
        add(help);
    }
}
