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

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EtchedBorder;

import org.penny_craal.icosamapper.ui.events.DeleteLayer;
import org.penny_craal.icosamapper.ui.events.DuplicateLayer;
import org.penny_craal.icosamapper.ui.events.IMEvent;
import org.penny_craal.icosamapper.ui.events.IMEventHelper;
import org.penny_craal.icosamapper.ui.events.IMEventListener;
import org.penny_craal.icosamapper.ui.events.IMEventSource;
import org.penny_craal.icosamapper.ui.events.LayerActionWithoutLayer;
import org.penny_craal.icosamapper.ui.events.LayerProperties;
import org.penny_craal.icosamapper.ui.events.NewLayer;
import org.penny_craal.icosamapper.ui.events.RenameLayer;
import org.penny_craal.icosamapper.ui.events.UnderlayLayer;

/**
 * Panel for managing layers.
 * @author Ville Jokela
 */
@SuppressWarnings("serial")
public class LayerManagementPanel extends JPanel implements IMEventSource {
    private LayerList layerList;
    private LayerManagementBar layerManagementBar;
    
    public LayerManagementPanel() {
        Listener listener = new Listener();
        layerList = new LayerList(new LayerListModel());
        layerList.addIMEventListener(listener);
        JScrollPane scrollPane = new JScrollPane(layerList);
        layerManagementBar = new LayerManagementBar();
        layerManagementBar.addActionListener(listener);
        
        setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED), "Layers"));
        setLayout(new BorderLayout());
        
        add(layerManagementBar, BorderLayout.LINE_END);
        add(scrollPane,         BorderLayout.CENTER);
    }
    
    public LayerListModel getLayerListModel() {
        return (LayerListModel) layerList.getModel();
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
    
    private class Listener implements IMEventListener, ActionListener {
        // for LayerList
        @Override
        public void handleEvent(IMEvent ime) {
            fireEvent(ime); // just pass on the event
        }

        // for LayerManagementBar
        @Override
        public void actionPerformed(ActionEvent ae) {
            String cmd = ae.getActionCommand();
            if (cmd.equals(LayerManagementBar.Tool.NEW.toolName)) {
                fireEvent(new NewLayer(LayerManagementPanel.this));
            } else {
                if (layerList.getSelectedValue() == null) {
                    fireEvent(new LayerActionWithoutLayer(LayerManagementPanel.this));
                } else if (cmd.equals(LayerManagementBar.Tool.DUPLICATE.toolName)) {
                    fireEvent(new DuplicateLayer(LayerManagementPanel.this, layerList.getSelectedValue()));
                } else if (cmd.equals(LayerManagementBar.Tool.RENAME.toolName)) {
                    fireEvent(new RenameLayer(LayerManagementPanel.this, layerList.getSelectedValue()));
                } else if (cmd.equals(LayerManagementBar.Tool.PROPERTIES.toolName)) {
                    fireEvent(new LayerProperties(LayerManagementPanel.this, layerList.getSelectedValue()));
                } else if (cmd.equals(LayerManagementBar.Tool.UNDERLAY.toolName)) {
                    fireEvent(new UnderlayLayer(LayerManagementPanel.this, layerList.getSelectedValue()));
                } else if (cmd.equals(LayerManagementBar.Tool.DELETE.toolName)) {
                    fireEvent(new DeleteLayer(LayerManagementPanel.this, layerList.getSelectedValue()));
                }
            }
        }
    }
}