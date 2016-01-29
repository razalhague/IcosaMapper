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

import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * Button bar for the layer management panel.
 * @author Ville Jokela
 */
@SuppressWarnings("serial")
public class LayerManagementBar extends JPanel {
    private final static List<Button> layerButtons = new ArrayList<Button>() {{
        add(new Button(Tool.NEW,        "create new layer"));
        add(new Button(Tool.DUPLICATE,  "duplicate layer"));
        add(new Button(Tool.RENAME,     "rename layer"));
        add(new Button(Tool.UNDERLAY,   "create an underlay"));
        add(new Button(Tool.DELETE,     "delete layer"));
    }};
    
    public LayerManagementBar() {
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        Listener listener = new Listener();
        for (Button b: layerButtons) {
            JButton button = new JButton(new ImageIcon(getClass().getResource("/gfx/" + b.tool.toolName + ".png")));
            button.setToolTipText(b.tooltip);
            button.addActionListener(listener);
            button.setActionCommand(b.tool.toolName);
            button.setMargin(new Insets(2, 2, 2, 2));
            add(button);
        }
        add(Box.createVerticalGlue());
    }
    
    public enum Tool {
        NEW         ("new"),
        DUPLICATE   ("duplicate"),
        RENAME      ("rename"),
        UNDERLAY    ("underlay"),
        DELETE      ("delete"),
        ;
        public final String toolName;
        
        Tool(String name) {
            this.toolName = name;
        }
    }
    
    protected static final class Button {
        public final Tool tool;
        public final String tooltip;

        public Button(Tool tool, String tooltip) {
            this.tool = tool;
            this.tooltip = tooltip;
        }
    }
    
      ///////////////////
     // Listener crap //
    ///////////////////
    
    public void addActionListener(ActionListener al) {
        listenerList.add(ActionListener.class, al);
    }
    
    public void removeActionListener(ActionListener al) {
        listenerList.remove(ActionListener.class, al);
    }
    
    public void fireEvent(ActionEvent ae) {
        Object[] listeners = listenerList.getListenerList();     // Guaranteed to return a non-null array
        // Process the listeners last to first, notifying
        // those that are interested in this event
        for (int i = listeners.length - 2; i >= 0; i -= 2) {
            if (listeners[i] == ActionListener.class) {
                ((ActionListener) listeners[i + 1]).actionPerformed(ae);
            }
        }
    }
    
    private class Listener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent ae) {
            ae.setSource(LayerManagementBar.this);
            fireEvent(ae);
        }
    }
}