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
import java.io.File;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import org.penny_craal.icosamapper.map.layerrenderers.Greyscale;
import org.penny_craal.icosamapper.map.InvalidPathException;
import org.penny_craal.icosamapper.map.Layer;
import org.penny_craal.icosamapper.map.layerrenderers.LayerRenderer;
import org.penny_craal.icosamapper.map.Map;
import org.penny_craal.icosamapper.map.Path;
import org.penny_craal.icosamapper.map.layerrenderers.SingleColour;
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
    private File mapFile = null;

    private static final byte defaultColour = 0;
    private static final int defaultOpSize = 1;
    private static final PaintBar.Tool defaultTool = PaintBar.Tool.DRAW;
    private static final String defaultNewLayerName = "New Layer";
    private static final String copyOfPrefix = "Copy of ";
    private static final LayerRenderer defaultLayerRenderer = new Greyscale();

    private IcosaMapper() {
        newMap();
        ui = new UI(map, defaultColour, defaultTool, defaultOpSize);
        ui.addIMEventListener(this);
    }

    private void newMap() {
        map = new Map();
        map.addLayer(LayerPanel.createTestLayer());
        layerName = map.getLayerNames().get(0);
        colour = defaultColour;
        tool = defaultTool;
        opSize = defaultOpSize;
        mapFile = null;
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
        boolean mapChanges = false;
        boolean layerRendererChanged = false;
        System.out.println(ime);
        // TODO: put each case into its own method to clean things up?
        switch (ime.type) {
            case about:
                // TODO: popup an about dialog
                break;
            case colourSelected:
                colour = ((ColourSelected) ime).value;
                break;
            case deleteLayer:
                DeleteLayer dl = (DeleteLayer) ime;
                if (ui.confirmLayerRemoval(dl.name)) {
                    hasUnsavedChanges = true;
                    map.removeLayer(dl.name);
                }
                break;
            case duplicateLayer:
                Layer newLayer = map.getLayer(layerName).copy();
                newLayer.setName(getNewLayerName(copyOfPrefix + newLayer.getName()));
                map.addLayer(newLayer);
                hasUnsavedChanges = true;
                break;
            case exit:
                if (!hasUnsavedChanges || ui.confirmExit()) {
                    System.exit(0);
                }
                break;
            case layerActionWithoutLayer:
                ui.askToSelectLayer();
                break;
            case layerRendererChanged:
                LayerRendererChanged lrc = (LayerRendererChanged) ime;
                mapChanges = true;
                layerRendererChanged = true;
                if (!map.getLayer(layerName).getLayerRenderer().getType().equals(lrc.lr)) {
                    LayerRenderer lr;
                    switch (lrc.lr) {
                        case SingleColour.type:
                            lr = new SingleColour();
                            break;
                        case Greyscale.type:
                            lr = new Greyscale();
                            break;
                        default:
                            throw new RuntimeException("unrecognized layer renderer type: " + lrc.lr);
                    }
                    map.getLayer(layerName).setLayerRenderer(lr);
                } else {
                    System.out.println("new LR type same as old LR type, ignoring");
                }
                break;
            case layerSelected:
                layerName = ((LayerSelected) ime).layerName;
                break;
            case newLayer:
                map.addLayer(new Layer(getNewLayerName(defaultNewLayerName), defaultLayerRenderer, defaultColour));
                hasUnsavedChanges = true;
                break;
            case newMap:
                if (!hasUnsavedChanges || ui.confirmNewMap()) {
                    newMap();
                    ui.setMap(map);
                    hasUnsavedChanges = false;
                }
                break;
            case openMap:
                File file = ui.chooseOpenFile(mapFile);
                if (file != null) {
                    System.out.println("opening file from: " + file.getAbsolutePath());
                    MapDAO dao = new FileMapDAO(file);
                    try {
                        map = dao.load(new JavaMapSerializer());
                        layerName = map.getLayerNames().get(0);
                        ui.setMap(map);
                        mapFile = file;
                        hasUnsavedChanges = false;
                        mapChanges = true;
                    } catch (DAException e) {
                        throw new RuntimeException("could not open file", e);
                    }
                }
                break;
            case opSizeSelected:
                opSize = ((OpSizeSelected) ime).opSize;
                break;
            case interact:
                Interact interact = ((Interact) ime);
                mapChanges = interact(interact.path, interact.isPrimary);
                if (mapChanges) {
                    hasUnsavedChanges = true;
                }
                break;
            case renameLayer:
                RenameLayer rl = (RenameLayer) ime;
                String newName = ui.renameLayer(rl.layer);
                if (newName != null) {
                    map.renameLayer(rl.layer, newName);
                    hasUnsavedChanges = true;
                    if (rl.layer.equals(layerName)) {
                        layerName = newName;
                    }
                }
                break;
            case saveMap:
                if (mapFile != null) {
                    save(mapFile);
                } else {
                    saveAs();
                }
                break;
            case saveMapAs:
                saveAs();
                break;
            case toolSelected:
                tool = ((ToolSelected) ime).tool;
                break;
            case underlayLayer:
                // TODO: underlay layer
                break;
            case configureLayerRenderer:
                ui.openConfigureLayerRendererDialog();
                break;
            case layerRendererReconfigured:
                mapChanges = true;
                hasUnsavedChanges = true;
                break;
            default:
                throw new RuntimeException("unrecognized event type");
        }
        ui.refresh(colour, layerName, tool, opSize, layerRendererChanged, mapChanges);
    }

    private String getNewLayerName(String baseName) {
        String newLayerName = null;
        List<String> layerNames = map.getLayerNames();
        if (!layerNames.contains(baseName)) {
            newLayerName = baseName;
        } else {
            for (int i = 2; newLayerName == null && i < Integer.MAX_VALUE; i++) {
                String potentialNewLayerName = baseName + " (" + i + ")";
                if (!layerNames.contains(potentialNewLayerName)) {
                    newLayerName = potentialNewLayerName;
                }
            }
        }

        return newLayerName;
    }

    private void saveAs() {
        File file = ui.chooseSaveFile(mapFile);
        if (file != null) {
            System.out.println("saving to: " + file.getAbsolutePath());
            save(file);
        }
    }

    private void save(File file) {
        MapDAO dao = new FileMapDAO(file);
        try {
            dao.save(map, new JavaMapSerializer());
            mapFile = file;
            hasUnsavedChanges = false;
        } catch (DAException e) {
            throw new RuntimeException("could not save file", e);
        }
    }

    private boolean interact(Path path, boolean isPrimary) {
        if ((tool == PaintBar.Tool.DRAW || tool == PaintBar.Tool.FILL) && !isPrimary) {
            colour = map.getElement(layerName, path);
            return false;
        }
        switch (tool) {
            case DRAW:
                try {
                    map.getLayer(layerName).setElement(path, colour);
                } catch (InvalidPathException e) {
                    throw new RuntimeException("could not paint element " + path + ", does not exist", e);
                }
                return true;
            case FILL:
                // TODO: fill tool
                return true;
            case DIVIDE:
                if (isPrimary) {
                    try {
                        map.divide(layerName, path);
                    } catch (InvalidPathException e) {
                        throw new RuntimeException("could not divide element " + path + ", does not exist", e);
                    }
                } else {
                    try {
                        map.unite(layerName, path);
                    } catch (InvalidPathException e) {
                        throw new RuntimeException("could not unite element " + path + ", does not exist", e);
                    }
                }
                return true;
            case ZOOM:
                if (isPrimary) {
                    if (map.getLayer(layerName).isValidPath(path)) {
                        ui.zoomIn(path);
                    } else {
                        throw new RuntimeException("trying to zoom into an invalid path");
                    }
                } else {
                    ui.zoomOut();
                }
                return false;
            default:
                throw new RuntimeException("unrecognized tool: " + tool);
        }
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