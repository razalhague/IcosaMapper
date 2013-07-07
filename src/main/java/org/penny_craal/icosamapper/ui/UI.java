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
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.EventListenerList;

import org.penny_craal.icosamapper.map.Map;
import org.penny_craal.icosamapper.ui.events.IMEvent;
import org.penny_craal.icosamapper.ui.events.IMEventHelper;
import org.penny_craal.icosamapper.ui.events.IMEventListener;
import org.penny_craal.icosamapper.ui.events.IMEventSource;

/**
 * The user interface for IcosaMapper.
 * @author Ville Jokela
 * @author James Pearce
 */
@SuppressWarnings("serial")
public class UI extends JFrame implements IMEventSource {
    private EventListenerList listenerList;
    private Map map;
    private int renderDepth;
    
    private StatusBar statusBar;
    private MenuBar menuBar;
    // these two go into the splitPane
    private LayerPanel layerPanel;
    private JPanel toolsPanel;
    // these two go into toolsPanel
    private PaintPanel paintPanel;
    private LayerManagementPanel layerManagementPanel;

    public UI() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (
                ClassNotFoundException |
                InstantiationException |
                IllegalAccessException |
                UnsupportedLookAndFeelException ex
        ) {
            // falls back to java default LAF (Metal)
        }
        Listener listener = new Listener();
        listenerList = new EventListenerList();
        map = new Map();
        map.addLayer(LayerPanel.createTestLayer());
        renderDepth = 2;
        menuBar = new MenuBar();
        statusBar = new StatusBar(renderDepth);
        layerPanel = new LayerPanel(map.getLayer("test-layer"), renderDepth);
        layerPanel.addIMEventListener(listener);
        toolsPanel = new JPanel();
        toolsPanel.setLayout(new BorderLayout());
        paintPanel = new PaintPanel();
        paintPanel.addIMEventListener(listener);
        layerManagementPanel = new LayerManagementPanel();
        layerManagementPanel.addIMEventListener(listener);
        layerManagementPanel.getLayerListModel().addAll(map.getLayerNames());
        
        toolsPanel.add(paintPanel,             BorderLayout.PAGE_START);
        toolsPanel.add(layerManagementPanel,   BorderLayout.CENTER);
        toolsPanel.setPreferredSize(toolsPanel.getMinimumSize());   // leave as much space as possible for layerPanel
        
        setTitle("IcosaMapper");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, false, toolsPanel, layerPanel);
        setJMenuBar(menuBar);
        add(splitPane,  BorderLayout.CENTER);
        add(statusBar,  BorderLayout.PAGE_END);
        
        pack();
        // JFrame doesn't seem to automatically adopt the contentPane's minimum size, so we'll call it explicitly
        // HACK: for some reason the minimum height is still about 18 pixels short
        setMinimumSize(new Dimension(getMinimumSize().width, getMinimumSize().height + 18));
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
            fireEvent(ime); // just pass on the event
        }
    }
}