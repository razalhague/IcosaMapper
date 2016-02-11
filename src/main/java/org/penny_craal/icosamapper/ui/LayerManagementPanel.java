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

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.EtchedBorder;

import org.penny_craal.icosamapper.map.layerrenderers.Greyscale;
import org.penny_craal.icosamapper.map.layerrenderers.LayerRenderer;
import org.penny_craal.icosamapper.map.layerrenderers.SingleColour;
import org.penny_craal.icosamapper.ui.events.*;

/**
 * Panel for managing layers.
 * @author Ville Jokela
 */
@SuppressWarnings("serial")
public class LayerManagementPanel extends JPanel implements IMEventSource {
    private LayerList layerList;
    private LayerManagementBar layerManagementBar;
    private JComboBox<String> rendererCombo;
    private JButton rendererButton;
    private String layerName;
    private String layerRendererName;

    private static final String[] layerRenderers = {
            Greyscale.type,
            SingleColour.type,
    };

    public LayerManagementPanel(String layerName, String layerRendererName) {
        this.layerName = layerName;
        this.layerRendererName = layerRendererName;
        Listener listener = new Listener();
        layerList = new LayerList(new LayerListModel());
        layerList.addIMEventListener(listener);
        JScrollPane scrollPane = new JScrollPane(layerList);
        layerManagementBar = new LayerManagementBar();
        layerManagementBar.addActionListener(listener);

        JPanel rendererPanel = new JPanel(new BorderLayout());
        rendererCombo = new JComboBox<>(layerRenderers);
        rendererButton = new JButton(new ImageIcon(getClass().getResource("/gfx/gear.png")));
        rendererButton.setToolTipText("Configure layer renderer");
        rendererButton.addActionListener(listener);
        rendererButton.setMargin(new Insets(2, 2, 2, 2));
        rendererPanel.add(rendererCombo, BorderLayout.CENTER);
        rendererPanel.add(rendererButton, BorderLayout.LINE_END);
        rendererCombo.addActionListener(listener);

        setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED), "Layers"));
        setLayout(new BorderLayout());

        add(rendererPanel,      BorderLayout.PAGE_START);
        add(layerManagementBar, BorderLayout.LINE_END);
        add(scrollPane,         BorderLayout.CENTER);
    }
    
    public LayerListModel getLayerListModel() {
        return (LayerListModel) layerList.getModel();
    }

    public void layerRendererChanged(LayerRenderer lr) {
        String type = lr.getType();

        for (int i = 0; i < layerRenderers.length; i++) {
            if (layerRenderers[i].equals(type)) {
                rendererCombo.setSelectedIndex(i);
                return;
            }
        }

        throw new RuntimeException("unexpected LayerRenderer type: " + type);
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

        // for LayerManagementBar & lr-combo box
        @Override
        public void actionPerformed(ActionEvent ae) {
            String cmd = ae.getActionCommand();
            if (ae.getSource().equals(layerManagementBar)) {
                if (cmd.equals(LayerManagementBar.Tool.NEW.toolName)) {
                    fireEvent(new NewLayer(LayerManagementPanel.this));
                } else {
                    if (layerList.getSelectedValue() == null) {
                        fireEvent(new LayerActionWithoutLayer(LayerManagementPanel.this));
                    } else if (cmd.equals(LayerManagementBar.Tool.DUPLICATE.toolName)) {
                        fireEvent(new DuplicateLayer(LayerManagementPanel.this, layerList.getSelectedValue()));
                    } else if (cmd.equals(LayerManagementBar.Tool.RENAME.toolName)) {
                        fireEvent(new RenameLayer(LayerManagementPanel.this, layerList.getSelectedValue()));
                    } else if (cmd.equals(LayerManagementBar.Tool.UNDERLAY.toolName)) {
                        fireEvent(new UnderlayLayer(LayerManagementPanel.this, layerList.getSelectedValue()));
                    } else if (cmd.equals(LayerManagementBar.Tool.DELETE.toolName)) {
                        fireEvent(new DeleteLayer(LayerManagementPanel.this, layerList.getSelectedValue()));
                    }
                }
            } else if (ae.getSource().equals(rendererCombo)) {
                String newLR = (String) rendererCombo.getSelectedItem();
                if (!newLR.equals(layerRendererName)) {
                    layerRendererName = newLR;
                    fireEvent(new LayerRendererChanged(LayerManagementPanel.this, layerName, newLR));
                }
            } else if (ae.getSource().equals(rendererButton)) {
                fireEvent(new ConfigureLayerRenderer(LayerManagementPanel.this, layerName));
            } else {
                throw new RuntimeException("unrecognized action source in action event: " + ae);
            }
        }
    }
}