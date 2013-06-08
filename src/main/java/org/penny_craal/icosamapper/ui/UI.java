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

/**
 * The user interface for IcosaMapper.
 * @author Ville Jokela
 * @author James Pearce
 */
import java.awt.BorderLayout;

import javax.swing.JFrame;

public class UI extends JFrame {
    private int renderDepth;
    private StatusBar statusBar;
    private MenuBar menuBar;
    private LayerPanel layerPanel;
    private ToolsPanel toolsPanel;

    public UI() {
        renderDepth = 1;
        menuBar = new MenuBar();
        statusBar = new StatusBar(renderDepth);
        layerPanel = new LayerPanel(LayerPanel.createTestLayer(), renderDepth);
        toolsPanel = new ToolsPanel();
        
        setTitle("IcosaMapper");
        setSize(900, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);    // centered on screen
        setLayout(new BorderLayout());
        
        setJMenuBar(menuBar);
        add(statusBar, BorderLayout.PAGE_END);
        add(layerPanel, BorderLayout.CENTER);
        add(toolsPanel, BorderLayout.LINE_START);
    }
}