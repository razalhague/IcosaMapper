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

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.penny_craal.icosamapper.map.GreyscaleLR;

import static org.penny_craal.icosamapper.map.Constants.*;
/**
 *
 * @author Ville Jokela
 */
@SuppressWarnings("serial")
public class PaintPanel extends JPanel {
    private JPanel opSize;
    private JSpinner opSizeSpinner;
    private PaintBar paintBar;
    private ColourPicker colourPicker;
    
    public PaintPanel() {
        Listener listener = new Listener();
        opSize = new JPanel();
        opSize.add(new JLabel("Operating size"));
        opSizeSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 10, 1));
        opSizeSpinner.addChangeListener(listener);
        opSize.add(opSizeSpinner);
        paintBar = new PaintBar();
        paintBar.addChangeListener(listener);
        colourPicker = new ColourPicker(new GreyscaleLR(), (byte) MIN_VALUE);
        colourPicker.addChangeListener(listener);
        
        setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED), "Paint"));
        setLayout(new BorderLayout());
        
        add(colourPicker, BorderLayout.CENTER);
        add(paintBar, BorderLayout.LINE_END);
        add(opSize, BorderLayout.PAGE_END);
    }
    
    public byte getValue() {
        return colourPicker.getValue();
    }
    
    public PaintBar.Tool getTool() {
        return paintBar.getTool();
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
            fireStateChanged();
        }
    }
}