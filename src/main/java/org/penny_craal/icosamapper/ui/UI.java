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
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.util.Iterator;
import java.util.List;

import javax.swing.*;
import javax.swing.event.EventListenerList;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.penny_craal.icosamapper.map.Layer;
import org.penny_craal.icosamapper.map.Path;
import org.penny_craal.icosamapper.map.layerrenderers.LayerRenderer;
import org.penny_craal.icosamapper.map.Map;
import org.penny_craal.icosamapper.ui.events.*;

/**
 * The user interface for IcosaMapper.
 * @author Ville Jokela
 * @author James Pearce
 */
@SuppressWarnings("serial")
public class UI extends JFrame implements IMEventSource {
    private Map map;
    private String layerName;

    private EventListenerList listenerList;
    private int renderDepth;
      ////////////////////
     // sub-components //
    ////////////////////
    private StatusBar statusBar;
    private MenuBar menuBar;
    // these two go into the splitPane
    private LayerPanel layerPanel;
    private JPanel toolsPanel;
    // these two go into toolsPanel
    private PaintPanel paintPanel;
    private LayerManagementPanel layerManagementPanel;
    private Listener listener;
    private LayerRendererConfigurationDialog lrConfDialog;

    public UI(Map map, byte colour, PaintBar.Tool tool, int opSize) {
        this.map = map;
        this.layerName = map.getLayerNames().get(0);
        Layer layer = map.getLayer(layerName);
        LayerRenderer lr = layer.getLayerRenderer();
        lrConfDialog = null;

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
        listener = new Listener();
        listenerList = new EventListenerList();
        renderDepth = 2;
        statusBar = new StatusBar(renderDepth);
        menuBar = new MenuBar();
        menuBar.addIMEventListener(listener);
        layerPanel = new LayerPanel(layer, renderDepth, opSize);
        layerPanel.addIMEventListener(listener);
        toolsPanel = new JPanel();
        toolsPanel.setLayout(new BorderLayout());
        paintPanel = new PaintPanel(opSize, tool, colour, lr);
        paintPanel.addIMEventListener(listener);
        layerManagementPanel = new LayerManagementPanel(layerName, lr.getType());
        layerManagementPanel.addIMEventListener(listener);
        layerManagementPanel.getLayerListModel().addAll(map.getLayerNames());
        
        toolsPanel.add(paintPanel,             BorderLayout.PAGE_START);
        toolsPanel.add(layerManagementPanel,   BorderLayout.CENTER);
        toolsPanel.setPreferredSize(toolsPanel.getMinimumSize());   // leave as much space as possible for layerPanel
        
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, false, toolsPanel, layerPanel);
        
        setTitle("IcosaMapper");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(listener);
        setLayout(new BorderLayout());
        
        setJMenuBar(menuBar);
        add(splitPane,  BorderLayout.CENTER);
        add(statusBar,  BorderLayout.PAGE_END);

        pack();
        // JFrame doesn't seem to automatically adopt the contentPane's minimum size, so we'll call it explicitly
        // HACK: for some reason the minimum height is still about 18 pixels short. dunno why, CBA to find out ATM
        setMinimumSize(new Dimension(getMinimumSize().width, getMinimumSize().height + 18));
        
        setExtendedState(UI.MAXIMIZED_BOTH);
        setVisible(true);
    }

    public void setMap(Map map) {
        this.map = map;
        this.layerName = map.getLayerNames().get(0);
        Layer layer = map.getLayer(map.getLayerNames().get(0));
        layerPanel.setLayer(layer);
        layerPanel.repaint();
        paintPanel.setLayerRenderer(layer.getLayerRenderer());
    }

    public void refresh(byte colour, String layerName, PaintBar.Tool tool, int opSize, boolean layerRendererChanged, boolean mapChanges) {
        layerPanel.setOpSize(opSize);
        if (!this.layerName.equals(layerName)) {
            layerPanel.setLayer(map.getLayer(layerName));
            layerPanel.repaint();
            layerManagementPanel.layerRendererChanged(map.getLayer(layerName).getLayerRenderer());
            if (lrConfDialog != null) {
                lrConfDialog.setLayerRenderer(layerName, map.getLayer(layerName).getLayerRenderer());
            }
        } else if (mapChanges) {
            layerPanel.repaint();
        }
        if (layerRendererChanged && lrConfDialog != null) {
            lrConfDialog.setLayerRenderer(layerName, map.getLayer(layerName).getLayerRenderer());
        }
        this.layerName = layerName;
        paintPanel.setTool(tool);
        paintPanel.setOpSize(opSize);
        paintPanel.setColour(colour);
        paintPanel.setLayerRenderer(map.getLayer(layerName).getLayerRenderer());
        // remove any layers not in the map
        LayerListModel llm = layerManagementPanel.getLayerListModel();
        List<String> layerNames = map.getLayerNames();
        for (String l: llm.getLayers()) {
            if (!layerNames.contains(l)) {
                llm.removeElement(l);
            }
        }
        // add any layers not in the display list
        for (String l: layerNames) {
            if (!llm.getLayers().contains(l)) {
                llm.addElement(l);
            }
        }
    }

    public boolean confirmExit() {
        return JOptionPane.showConfirmDialog(this, "Really quit? Any unsaved changes will be lost.", "Quit IcosaMapper?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION;
    }

    public boolean confirmNewMap() {
        return JOptionPane.showConfirmDialog(this, "Really make new map? Any unsaved changes to current map will be lost.", "New map?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION;
    }

    public String renameLayer(String layer) {
        return (String) JOptionPane.showInputDialog(this, "New name for layer", "Rename layer", JOptionPane.QUESTION_MESSAGE, null, null, layer);
    }

    public void askToSelectLayer() {
        JOptionPane.showMessageDialog(this, "Please select a layer.", "No layer selected", JOptionPane.INFORMATION_MESSAGE);
    }

    public boolean confirmLayerRemoval(String name) {
        return JOptionPane.showConfirmDialog(this, "Do you want to remove layer \"" + name + "\"?", "Remove layer", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION;
    }

    public File chooseSaveFile(File file) {
        JFileChooser chooser = new JFileChooser();
        if (file != null) {
            chooser.setCurrentDirectory(file);
        }
        FileNameExtensionFilter filter = new FileNameExtensionFilter("IcosaMapper map files", "icosamap");
        chooser.setFileFilter(filter);
        if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            File newFile = chooser.getSelectedFile();
            if (!newFile.getAbsolutePath().endsWith(".icosamap")) {
                newFile = new File(newFile.getAbsoluteFile() + ".icosamap");
            }
            return newFile;
        } else {
            return null;
        }
    }

    public File chooseOpenFile(File file) {
        JFileChooser chooser = new JFileChooser();
        if (file != null) {
            chooser.setCurrentDirectory(file);
        }
        FileNameExtensionFilter filter = new FileNameExtensionFilter("IcosaMapper map files", "icosamap");
        chooser.setFileFilter(filter);
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            return chooser.getSelectedFile();
        } else {
            return null;
        }
    }

    public void openConfigureLayerRendererDialog() {
        if (lrConfDialog == null) {
            lrConfDialog = new LayerRendererConfigurationDialog(this, layerName, map.getLayer(layerName).getLayerRenderer());
            lrConfDialog.addIMEventListener(listener);
            lrConfDialog.addWindowListener(listener);
        }
    }

    public void zoomIn(Path path) {
        layerPanel.setZoom(path);
    }

    public void zoomOut() {
        layerPanel.zoomOut();
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

    private class Listener implements IMEventListener, WindowListener {
        // for IMEventListener
        @Override
        public void handleEvent(IMEvent ime) {
            fireEvent(ime); // just pass on the event
        }

        // the rest are for WindowListener

        @Override
        public void windowOpened(WindowEvent windowEvent) {
        }

        @Override
        public void windowClosing(WindowEvent windowEvent) {
            if (windowEvent.getSource().equals(UI.this)) {
                fireEvent(new Exit(UI.this));
            } else if (windowEvent.getSource().equals(lrConfDialog)) {
                lrConfDialog = null;
            } else {
                throw new RuntimeException("unrecognized event source for WindowEvent: " + windowEvent);
            }
        }

        @Override
        public void windowClosed(WindowEvent windowEvent) {
        }

        @Override
        public void windowIconified(WindowEvent windowEvent) {
        }

        @Override
        public void windowDeiconified(WindowEvent windowEvent) {
        }

        @Override
        public void windowActivated(WindowEvent windowEvent) {
        }

        @Override
        public void windowDeactivated(WindowEvent windowEvent) {
        }
    }
}