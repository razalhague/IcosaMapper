/* IcosaMapper - an rpg map editor based on equilateral triangles that form an icosahedron
 * Copyright (C) 2013  Ville Jokela, James Pearce
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * contact me <ville.jokela@penny-craal.org>
 */

package org.penny_craal.icosamapper.ui;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 *
 * @author Ville Jokela
 */
@SuppressWarnings("serial")
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