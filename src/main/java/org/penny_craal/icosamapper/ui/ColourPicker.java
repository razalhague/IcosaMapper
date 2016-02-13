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
import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.border.BevelBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.penny_craal.icosamapper.map.Constants;
import org.penny_craal.icosamapper.map.Util;
import org.penny_craal.icosamapper.map.layerrenderers.LayerRenderer;

/**
 * A widget for choosing a colour from ones that are used by the LayerRenderer
 * @author Ville Jokela
 * @author James Pearce
 */
@SuppressWarnings("serial")
public class ColourPicker extends JPanel {
    // TODO: show the whole scale of all available colours somewhere
    private LayerRenderer lr;
    private JSpinner spinner;
    private JSlider slider;
    private JPanel colourContainer;
    private JPanel colour;
    private TriangleValueModel value;
    
    public ColourPicker(LayerRenderer lr, byte initValue) {
        this.lr = lr;
        value = new TriangleValueModel(Util.toInt(initValue), Constants.MIN_VALUE, Constants.MAX_VALUE);
        
        Listener listener = new Listener();
        
        spinner = new JSpinner(this.value.getSpinnerModel());
        slider = new JSlider(this.value.getBoundedRangeModel());
        slider.setOrientation(JSlider.HORIZONTAL);
        value.addChangeListener(listener);
        colour = new JPanel();
        colour.setBackground(new Color(lr.renderByte((byte) value.getValue())));
        colourContainer = new JPanel();
        colourContainer.setLayout(new BorderLayout());
        colourContainer.setBorder(new BevelBorder(BevelBorder.LOWERED));
        colourContainer.add(colour, BorderLayout.CENTER);
        
        setLayout(new BorderLayout());
        
        add(slider,             BorderLayout.PAGE_START);
        add(colourContainer,    BorderLayout.CENTER);
        add(spinner,            BorderLayout.PAGE_END);
    }
    
    public byte getValue() {
        return (byte) value.getValue();
    }

    public void setLayerRenderer(LayerRenderer lr) {
        this.lr = lr;
        colour.setBackground(new Color(lr.renderByte((byte) value.getValue())));
        repaint();
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

    public void setColour(byte colour) {
        value.setValue(Util.toInt(colour));
    }

    private class Listener implements ChangeListener {
        @Override
        public void stateChanged(ChangeEvent ce) {
            colour.setBackground(new Color(lr.renderByte((byte) value.getValue())));
            fireStateChanged();
        }
    }
}