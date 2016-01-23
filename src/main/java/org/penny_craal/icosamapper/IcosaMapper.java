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

package org.penny_craal.icosamapper;

import java.awt.Frame;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import org.penny_craal.icosamapper.map.GreyscaleLR;
import org.penny_craal.icosamapper.map.Layer;
import org.penny_craal.icosamapper.map.Map;
import org.penny_craal.icosamapper.ui.LayerPanel;
import org.penny_craal.icosamapper.ui.PaintBar;
import org.penny_craal.icosamapper.ui.UI;
import org.penny_craal.icosamapper.ui.events.*;

/**
 * The main class for the program.
 * @author Ville Jokela
 * @author James Pearce
 */
public class IcosaMapper implements IMEventListener {
    private UI ui;
    private Map map;
    private String layerName;
    private byte colour;
    private PaintBar.Tool tool;
    private int opSize;
    private boolean hasUnsavedChanges = false;

    private static final byte defaultColour = 0;
    private static final int defaultOpSize = 1;
    private static final PaintBar.Tool defaultTool = PaintBar.Tool.DRAW;

    private IcosaMapper() {
        map = new Map();
        map.addLayer(LayerPanel.createTestLayer());
        layerName = map.getLayerNames().get(0);
        colour = defaultColour;
        tool = defaultTool;
        opSize = defaultOpSize;
        ui = new UI(map, defaultColour, defaultTool, defaultOpSize);
        ui.addIMEventListener(this);
    }

    /**
     * The main method for the program.
     * @param args ignored
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() { @Override public void run() {
                Thread.setDefaultUncaughtExceptionHandler(new Handler());
                IcosaMapper icosaMapper = new IcosaMapper();
        }});
    }

    @Override
    public void handleEvent(IMEvent ime) {
        System.out.println(ime);
        switch (ime.type) {
            case about:
                // TODO: popup an about dialog
                break;
            case colourSelected:
                colour = ((ColourSelected) ime).value;
                break;
            case deleteLayer:
                // TODO: confirm removal
                hasUnsavedChanges = true;
                map.removeLayer(((DeleteLayer) ime).name);
                break;
            case duplicateLayer:
                // TODO: duplicate layer
                hasUnsavedChanges = true;
                break;
            case exit:
                if (!hasUnsavedChanges || ui.confirmExit()) {
                    System.exit(0);
                }
                break;
            case layerActionWithoutLayer:
                // TODO: popup a window telling user to select a layer
                break;
            case layerProperties:
                // TODO: open layer properties window & update hasUnsavedChanges if necessary
                break;
            case layerSelected:
                layerName = ((LayerSelected) ime).layerName;
                break;
            case newLayer:
                // TODO: ask new layer's name, renderer and initial value
                map.addLayer(new Layer("new layer", new GreyscaleLR(), (byte) 0));
                hasUnsavedChanges = true;
                break;
            case newMap:
                // TODO: ask if user really wants to create new map, old map will be discarded
                map = new Map();
                hasUnsavedChanges = false;
                break;
            case openMap:
                // TODO: open dialog for opening a map
                hasUnsavedChanges = false;
                break;
            case opSizeSelected:
                opSize = ((OpSizeSelected) ime).opSize;
                break;
            case paint:
                hasUnsavedChanges = true;
                // TODO: handle all the different tools
                break;
            case renameLayer:
                // TODO: popup window asking for layer's new name
                RenameLayer rl = (RenameLayer) ime;
                map.renameLayer(rl.layer, "new name");
                hasUnsavedChanges = true;
                break;
            case saveMap:
                // TODO: save map
                hasUnsavedChanges = false;
                break;
            case saveMapAs:
                // TODO: open dialog for saving map
                hasUnsavedChanges = false;
                break;
            case toolSelected:
                tool = ((ToolSelected) ime).tool;
                break;
            case underlayLayer:
                // TODO: underlay layer
                break;
            default:
                throw new RuntimeException("unrecognized event type");
        }
        ui.refresh(colour, layerName, tool, opSize);
    }

    private static class Handler implements Thread.UncaughtExceptionHandler {
        @Override
        public void uncaughtException(Thread thread, Throwable thrwbl) {
            Frame[] frames = Frame.getFrames();
            thrwbl.printStackTrace();
            JOptionPane.showMessageDialog(
                    frames[0],
                    thrwbl.getStackTrace(),
                    "Caught " + thrwbl.getClass().getCanonicalName() + " in " + thread.getName()
                        + ": " + thrwbl.getMessage(),
                    JOptionPane.ERROR_MESSAGE
            );
            for (Frame frame: frames) {
                frame.dispose();
            }
        }
    }
}