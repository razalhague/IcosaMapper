package org.penny_craal.icosamapper.ui;

import javax.swing.JPanel;
import org.penny_craal.icosamapper.map.Layer;

/**
 *
 * @author Ville Jokela
 */
public class MapPanel extends JPanel {
    private Layer layer;
    
    public MapPanel() {
        layer = null;
    }
    
    public void setLayer(Layer layer) {
        this.layer = layer;
    }
    
    public Layer getLayer() {
        return layer;
    }
    
    public void clearMap() {
        layer = null;
    }
    
    public void update() {
        // TODO
    }
    
    private void drawTriangles() {
        // TODO
    }
}