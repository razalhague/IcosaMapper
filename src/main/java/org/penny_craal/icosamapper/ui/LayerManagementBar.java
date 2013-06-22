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

import org.penny_craal.icosamapper.ui.events.IMEvent;
import org.penny_craal.icosamapper.ui.events.IMEventHelper;
import org.penny_craal.icosamapper.ui.events.IMEventListener;
import org.penny_craal.icosamapper.ui.events.IMEventSource;

/**
 *
 * @author Ville Jokela
 */
@SuppressWarnings("serial")
public class LayerManagementBar extends JPanel implements IMEventSource {
    private final static List<Button> layerButtons = new ArrayList<Button>() {{
        add(new Button(Tool.NEW,        "new",         "create new layer"));
        add(new Button(Tool.DUPLICATE,  "duplicate",   "duplicate layer"));
        add(new Button(Tool.RENAME,     "rename",      "rename layer"));
        add(new Button(Tool.PROPERTIES, "properties",  "edit layer's properties"));
        add(new Button(Tool.UNDERLAY,   "underlay",    "create an underlay"));
        add(new Button(Tool.DELETE,     "delete",      "delete layer"));
    }};
    public LayerManagementBar() {
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        Listener listener = new Listener();
        for (Button b: layerButtons) {
            JButton button = new JButton(new ImageIcon(getClass().getResource("/gfx/" + b.name + ".png")));
            button.setToolTipText(b.tooltip);
            button.addActionListener(listener);
            button.setActionCommand(b.name);
            button.setMargin(new Insets(2, 2, 2, 2));
            add(button);
        }
        add(Box.createVerticalGlue());
    }
    
    private Button getButtonByName(String name) {
        for (Button b: layerButtons)
            if (b.name.equals(name))
                return b;
        
        return null;
    }
    
    public enum Tool {
        NEW         (0),
        DUPLICATE   (1),
        RENAME      (2),
        PROPERTIES  (3),
        UNDERLAY    (4),
        DELETE      (5),
        ;
        public static final int min_id = 0;
        public static final int max_id = 5;
        public final int id;
        private Tool(int id) { this.id = id; }
    }
    
    protected static final class Button {
        public final Tool tool;
        public final String name;
        public final String tooltip;

        public Button(Tool tool, String name, String tooltip) {
            this.tool = tool;
            this.name = name;
            this.tooltip = tooltip;
        }
    }
    
      ///////////////////
     // Listener crap //
    ///////////////////
    
    @Override
    public void addIMEventListener(IMEventListener imel) {
        IMEventHelper.addListener(listenerList, imel);
    }
    
    @Override
    public void removeIMEventListener(IMEventListener imel) {
        IMEventHelper.removeListener(listenerList, imel);
    }
    
    protected void fireEvent(IMEvent ime) {
        IMEventHelper.fireEvent(listenerList, ime);
    }
    
    private class Listener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent ae) {
            fireEvent(new IMEvent(this, IMEvent.Type.fromString(ae.getActionCommand())));
        }
    }
}