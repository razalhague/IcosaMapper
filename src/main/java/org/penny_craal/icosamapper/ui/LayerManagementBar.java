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
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.penny_craal.icosamapper.map.GreyscaleLR;
import org.penny_craal.icosamapper.ui.events.DeleteLayer;
import org.penny_craal.icosamapper.ui.events.DuplicateLayer;
import org.penny_craal.icosamapper.ui.events.IMEvent;
import org.penny_craal.icosamapper.ui.events.IMEventHelper;
import org.penny_craal.icosamapper.ui.events.IMEventListener;
import org.penny_craal.icosamapper.ui.events.IMEventSource;
import org.penny_craal.icosamapper.ui.events.LayerProperties;
import org.penny_craal.icosamapper.ui.events.NewLayer;
import org.penny_craal.icosamapper.ui.events.RenameLayer;

/**
 *
 * @author Ville Jokela
 */
@SuppressWarnings("serial")
public class LayerManagementBar extends JPanel implements IMEventSource {
    private final LayerList ll;
    
    private final static List<Button> layerButtons = new ArrayList<Button>() {{
        add(new Button(Tool.NEW,        "create new layer"));
        add(new Button(Tool.DUPLICATE,  "duplicate layer"));
        add(new Button(Tool.RENAME,     "rename layer"));
        add(new Button(Tool.PROPERTIES, "edit layer's properties"));
        add(new Button(Tool.UNDERLAY,   "create an underlay"));
        add(new Button(Tool.DELETE,     "delete layer"));
    }};
    
    public LayerManagementBar(LayerList ll) {
        this.ll = ll;
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
        PROPERTIES  ("properties"),
        UNDERLAY    ("underlay"),
        DELETE      ("delete"),
        ;
        public final String toolName;
        
        private Tool(String name) {
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
            String cmd = ae.getActionCommand();
            if (cmd.equals(Tool.NEW.toolName)) {
                String name = JOptionPane.showInputDialog("Enter name for the new layer");
                if (name == null)
                    return;
                name = name.trim();
                if (name.isEmpty()) {
                    JOptionPane.showMessageDialog(LayerManagementBar.this, "Name must not be empty");
                } else {
                    fireEvent(new NewLayer(LayerManagementBar.this, name));
                }
            } else if (cmd.equals(Tool.DUPLICATE.toolName)) {
                String selected = ll.getSelectedValue();
                if (selected == null) {
                    JOptionPane.showMessageDialog(LayerManagementBar.this, "Select a layer to duplicate");
                    return;
                }
                String name = JOptionPane.showInputDialog("Enter name for the new layer");
                if (name == null)
                    return;
                name = name.trim();
                if (name.isEmpty()) {
                    JOptionPane.showMessageDialog(LayerManagementBar.this, "Name must not be empty");
                } else {
                    fireEvent(new DuplicateLayer(LayerManagementBar.this, selected, name));
                }
            } else if (cmd.equals(Tool.RENAME.toolName)) {
                String selected = ll.getSelectedValue();
                if (selected == null) {
                    JOptionPane.showMessageDialog(LayerManagementBar.this, "Select a layer to rename");
                    return;
                }
                String name = JOptionPane.showInputDialog("Enter new name for the layer");
                if (name == null)
                    return;
                name = name.trim();
                if (name.isEmpty()) {
                    JOptionPane.showMessageDialog(LayerManagementBar.this, "Name must not be empty");
                } else {
                    fireEvent(new RenameLayer(LayerManagementBar.this, selected, name));
                }
            } else if   (cmd.equals(Tool.PROPERTIES.toolName)) {
                // TODO: LayerRenderer selector
                String selected = ll.getSelectedValue();
                if (selected == null) {
                    JOptionPane.showMessageDialog(LayerManagementBar.this, "Select a layer to edit");
                    return;
                }
                fireEvent(new LayerProperties(LayerManagementBar.this, ll.getSelectedValue(), new GreyscaleLR()));
            } else if   (cmd.equals(Tool.UNDERLAY.toolName)) {
                // disabled at the moment
            } else if   (cmd.equals(Tool.DELETE.toolName)) {
                String selected = ll.getSelectedValue();
                if (selected == null) {
                    JOptionPane.showMessageDialog(LayerManagementBar.this, "Select a layer to delete");
                    return;
                }
                fireEvent(new DeleteLayer(LayerManagementBar.this, ll.getSelectedValue()));
            }
        }
    }
}