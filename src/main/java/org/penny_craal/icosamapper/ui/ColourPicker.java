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

import org.penny_craal.icosamapper.map.LayerRenderer;

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
    
    public ColourPicker(LayerRenderer lr) {
        this.lr = lr;
        
        spinner = new JSpinner(new SpinnerNumberModel(0, 0, 255, 1));//(initial value, minimum value, maximum value, step)
        slider = new JSlider();
        slider.setOrientation(JSlider.VERTICAL);
        colour = new JPanel();
        colour.setBackground(new Color(lr.renderByte((byte) 0)));
        
        setLayout(new BorderLayout());
        
        add(spinner, BorderLayout.PAGE_END);
        add(slider, BorderLayout.LINE_END);
        add(colour, BorderLayout.CENTER);
        
    }
}