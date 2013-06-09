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
import javax.swing.SpinnerNumberModel;
import javax.swing.border.BevelBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.penny_craal.icosamapper.map.LayerRenderer;
import org.penny_craal.icosamapper.map.Util;

import static org.penny_craal.icosamapper.map.Constants.*;

/**
 * A widget for choosing a colour from ones that are used by the LayerRenderer
 * @author Ville Jokela
 * @author James Pearce
 */
@SuppressWarnings("serial")
public class ColourPicker extends JPanel {
    private LayerRenderer lr;
    private JSpinner spinner;
    private JSlider slider;
    private JPanel colour;
    private byte value;
    
    public ColourPicker(LayerRenderer lr, byte value) {
        this.lr = lr;
        this.value = value;
        
        Listener listener = new Listener();
        
        spinner = new JSpinner(new SpinnerNumberModel(Util.toInt(value), MIN_VALUE, MAX_VALUE, 1));//(initial value, minimum value, maximum value, step)
        spinner.addChangeListener(listener);
        slider = new JSlider(MIN_VALUE, MAX_VALUE, Util.toInt(value));
        slider.setOrientation(JSlider.VERTICAL);
        slider.addChangeListener(listener);
        colour = new JPanel();
        colour.setBorder(new BevelBorder(BevelBorder.LOWERED));
        colour.setBackground(new Color(lr.renderByte(value)));
        
        setLayout(new BorderLayout());
        
        add(spinner, BorderLayout.PAGE_END);
        add(slider, BorderLayout.LINE_END);
        add(colour, BorderLayout.CENTER);
    }
    
    public byte getValue() {
        return value;
    }
    
    /**
     * Caps the value between MIN_VALUE and MAX_VALUE.
     * 
     * Shouldn't really be necessary, but better safe than sorry.
     * @param value value to be capped
     * @return      the capped value
     */
    private static int capValue(int value) {
        if (value < MIN_VALUE)
            return MIN_VALUE;
        if (value > MAX_VALUE)
            return MAX_VALUE;
        
        return value;
    }
    
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
    
    private class Listener implements ChangeListener {
        @Override
        public void stateChanged(ChangeEvent ce) {
            if (ce.getSource() == spinner) {
                value = (byte) capValue((Integer) spinner.getValue());
                slider.setValue(Util.toInt(value));
            } else if (ce.getSource() == slider) {
                value = (byte) capValue(slider.getValue());
                spinner.setValue(Util.toInt(value));
            } else {    // should never happen
                throw new RuntimeException("Unrecognized event source");
            }
            colour.setBackground(new Color(lr.renderByte(value)));
            fireStateChanged();
        }
    }
}