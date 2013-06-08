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
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;

/**
 *
 * @author Ville Jokela
 */
@SuppressWarnings("serial")
public class LayerList extends JPanel {
    private JList<String> layerList;
    private ButtonBar buttonBar;
    
    private final static Map<String, String> layerButtons = new HashMap<String, String>() {{
        put("new",         "create new layer");
        put("duplicate",   "duplicate layer");
        put("rename",      "rename layer");
        put("properties",  "edit layer's properties");
        put("underlay",    "create an underlay");
        put("delete",      "delete layer");
    }};
    
    public LayerList () {
        layerList = new JList<>();
        layerList.setBorder(new BevelBorder(BevelBorder.LOWERED));
        buttonBar = new ButtonBar(layerButtons);
        
        setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED), "Layers"));
        setLayout(new BorderLayout());
        
        add(buttonBar, BorderLayout.LINE_END);
        add(layerList, BorderLayout.CENTER);
    }
}