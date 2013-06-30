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

import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author Ville Jokela
 */
@SuppressWarnings("serial")
public class PaintBar extends JPanel {
    private ButtonGroup bg;
    private Tool tool;
            
    private final static List<Button> paintButtons = new ArrayList<Button>() {{
        add(new Button(Tool.DRAW,       "draw on the map"));
        add(new Button(Tool.FILL,       "fill an area"));
        add(new Button(Tool.DIVIDE,     "divide a triangle"));
        add(new Button(Tool.UNITE,      "unite a triangle"));
        add(new Button(Tool.ZOOM_IN,    "zoom in to a triangle"));
        add(new Button(Tool.ZOOM_OUT,   "zoom out"));
    }};
    
    public PaintBar() {
        bg = new ButtonGroup();
        tool = null;
        
        setLayout(new GridLayout(0,2));
        
        Listener listener = new Listener();
        for (Button b: paintButtons) {
            JToggleButton button = new JToggleButton(new ImageIcon(getClass().getResource("/gfx/" + b.tool.toolName + ".png")));
            button.setToolTipText(b.tooltip);
            button.addActionListener(listener);
            button.setActionCommand(b.tool.toolName);
            button.setMargin(new Insets(2, 2, 2, 2));
            bg.add(button);
            add(button);
        }
    }
    
    public Tool getTool() {
        return tool;
    }
    
    public enum Tool {
        DRAW        ("draw"),
        FILL        ("fill"),
        DIVIDE      ("divide"),
        UNITE       ("unite"),
        ZOOM_IN     ("zoom-in"),
        ZOOM_OUT    ("zoom-out"),
        ;
        public final String toolName;
        private Tool(String toolName) {
            this.toolName = toolName;
        }
    }
    
    private static final class Button {
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
    
    public void addChangeListener(ChangeListener cl) {
        listenerList.add(ChangeListener.class, cl);
    }
    
    public void removeChangeListener(ChangeListener cl) {
        listenerList.remove(ChangeListener.class, cl);
    }
    
    protected void fireStateChanged() {
        ChangeEvent ce = new ChangeEvent(this);
        for (ChangeListener cl: listenerList.getListeners(ChangeListener.class))
            cl.stateChanged(ce);
    }
    
    private class Listener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent ae) {
            for (Button b: paintButtons)
                if (b.tool.toolName.equals(ae.getActionCommand()))
                    tool = b.tool;
            fireStateChanged();
        }
    }
}