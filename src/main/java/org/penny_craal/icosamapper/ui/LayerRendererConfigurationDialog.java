/* IcosaMapper - an rpg map editor based on equilateral triangles that form an icosahedron
 * Copyright (C) 2013  Ville Jokela
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
import java.util.HashMap;
import java.util.Map;

import javax.swing.*;
import javax.swing.event.EventListenerList;

import org.penny_craal.icosamapper.map.layerrenderers.LayerRenderer;
import org.penny_craal.icosamapper.map.layerrenderers.variabletypes.VariableType;
import org.penny_craal.icosamapper.ui.events.*;

/**
 * @author Ville Jokela
 */
public class LayerRendererConfigurationDialog extends JDialog implements IMEventSource {
    private String layerName;
    private LayerRenderer lr;
    private LayerRendererDisplay layerRendererDisplay;
    private JPanel variablePanel;
    private EventListenerList listenerList = new EventListenerList();
    private Map<String, VariableFactory.VariableComponent> components = new HashMap<>();
    private Listener listener;

    public LayerRendererConfigurationDialog(JFrame frame, String layerName, LayerRenderer lr) {
        super(frame);
        this.layerName = layerName;
        this.lr = lr;
        listener = new Listener();

        setTitle();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        populate();
        setVisible(true);
    }

    private void setTitle() {
        setTitle(layerName + ": " + lr.getType());
    }

    public void populate() {
        if (layerRendererDisplay != null) {
            remove(layerRendererDisplay);
        }
        if (variablePanel != null) {
            remove(variablePanel);
        }
        for (Map.Entry<String, VariableFactory.VariableComponent> entry: components.entrySet()) {
            remove(entry.getValue());
        }
        components.clear();
        layerRendererDisplay = new LayerRendererDisplay(lr);
        variablePanel = makeVariablePanel();
        add(layerRendererDisplay, BorderLayout.CENTER);
        add(variablePanel, BorderLayout.PAGE_END);
        pack();
        setMinimumSize(getPreferredSize());
        repaint();
    }

    public void setLayerRenderer(String layerName, LayerRenderer lr) {
        this.layerName = layerName;
        this.lr = lr;
        setTitle();
        populate();
    }

    private JPanel makeVariablePanel() {
        JPanel variablePanel = new JPanel(new GridBagLayout());
        GridBagConstraints labelConstraints = new GridBagConstraints();
        labelConstraints.weightx = 0;
        labelConstraints.gridx = 0;
        labelConstraints.anchor = GridBagConstraints.LINE_START;
        labelConstraints.insets = new Insets(0,2,0,2);
        GridBagConstraints componentConstraints = new GridBagConstraints();
        componentConstraints.weightx = 1000;
        componentConstraints.gridx = 1;
        componentConstraints.fill = GridBagConstraints.BOTH;

        Map<String, VariableType> variableMap = lr.getVariables();
        int i = 0;

        for (Map.Entry<String, VariableType> entry: variableMap.entrySet()) {
            labelConstraints.gridy = i;
            componentConstraints.gridy = i;
            String variableName = entry.getKey();
            Object initValue = lr.getValue(variableName);
            VariableFactory.VariableComponent vc = VariableFactory.makeComponentFor(variableName, entry.getValue(), initValue);
            vc.addIMEventListener(listener);
            components.put(variableName, vc);
            JLabel name = new JLabel(entry.getKey());
            variablePanel.add(name, labelConstraints);
            variablePanel.add(vc, componentConstraints);
            i++;
        }

        return variablePanel;
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

    private class Listener implements IMEventListener {
        @Override
        public void handleEvent(IMEvent ime) {
            layerRendererDisplay.repaint();
            LayerRendererReconfigured lrr = (LayerRendererReconfigured) ime;
            lr.setVariable(lrr.variableName, components.get(lrr.variableName).getValue());
            fireEvent(ime);
        }
    }
}
