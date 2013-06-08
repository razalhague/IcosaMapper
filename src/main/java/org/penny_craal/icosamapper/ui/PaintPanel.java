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
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EtchedBorder;

import org.penny_craal.icosamapper.map.GreyscaleLR;

/**
 *
 * @author Ville Jokela
 */
@SuppressWarnings("serial")
public class PaintPanel extends JPanel {
    private JPanel opSize;
    private JSpinner opSizeSpinner;
    private ButtonBar buttonBar;
    private ColourPicker colourPicker;
    
    private final static Map<String, String> paintButtons = new HashMap<String, String>() {{
        put("draw",       "draw on the map");
        put("fill",       "fill an area");
        put("subdivide",  "divide a triangle");
        put("unite",      "unite a triangle");
        put("zoom-in",    "zoom in to a triangle");
        put("zoom-out",   "zoom out");
    }};
    
    public PaintPanel() {
        opSize = new JPanel();
        opSize.add(new JLabel("Operating size"));
        opSizeSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 10, 1));
        opSize.add(opSizeSpinner);
        buttonBar = new ButtonBar(paintButtons);
        colourPicker = new ColourPicker(new GreyscaleLR());
        
        setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED), "Paint"));
        setLayout(new BorderLayout());
        
        add(colourPicker, BorderLayout.CENTER);
        add(buttonBar, BorderLayout.LINE_END);
        add(opSize, BorderLayout.PAGE_END);
    }
}