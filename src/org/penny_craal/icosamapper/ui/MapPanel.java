package org.penny_craal.icosamapper.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import javax.swing.JPanel;
import org.penny_craal.icosamapper.map.AccessPath;
import org.penny_craal.icosamapper.map.Layer;

/**
 *
 * @author Ville Jokela
 */
public class MapPanel extends JPanel {
    private Layer layer;
    private AccessPath zoom;
    private int drawDepth;
    
    public MapPanel(Layer layer, int drawDepth) {
        this.layer = layer;
        this.drawDepth = drawDepth;
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
    
    public void setDrawDepth(int drawDepth) {
        this.drawDepth = drawDepth;
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        if (layer == null) {
            return;
        }
        int width = (int) (getWidth()/5.5);
        int height = getHeight()/3;
        int[] values = layer.renderAtDepth(drawDepth);
        int rangeStart = 0;
        int rangeInc = values.length/20;
        
        // triangles with point up
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 5; j++, rangeStart += rangeInc) {
                paintTriangle(g2d, values, rangeStart, rangeStart + rangeInc, drawDepth-1, width*j + width/2*i, height*i, width, height, true);
            }
        }
        
        // triangles with point down
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 5; j++, rangeStart += rangeInc) {
                paintTriangle(g2d, values, rangeStart, rangeStart + rangeInc, drawDepth-1, width*j + width/2*i, height*(i+1), width, height, false);
            }
        }
    }

    private void paintTriangle(Graphics2D g2d, int[] values, int rangeStart, int rangeEnd, int depth, int x, int y, int width, int height, boolean isPointUp) {
        if (depth == 0) {
            Polygon p = new Polygon();
            p.addPoint(x, y + (isPointUp ? height : 0));            // left side
            p.addPoint(x + width, y + (isPointUp ? height : 0));    // right side
            p.addPoint(x + width/2, y + (isPointUp ? 0 : height));  // point
            g2d.setColor(new Color(values[rangeStart]));
            g2d.draw(p);
            g2d.fillPolygon(p);
        } else {
            int rangeInc = (rangeEnd - rangeStart)/9;
            if (isPointUp) {
                paintTriangle(g2d, values, rangeStart, rangeStart+rangeInc, depth-1, x + width/3, y, width/3, height/3, true);
                rangeStart += rangeInc;
                for (int i = 0; i < 3; i++, rangeStart += rangeInc) {
                    paintTriangle(g2d, values, rangeStart, rangeStart+rangeInc, depth-1, x + (width/6)*(i+1), y + height/3, width/3, height/3, i%2 == 0);
                }
                for (int i = 0; i < 5; i++, rangeStart += rangeInc) {
                    paintTriangle(g2d, values, rangeStart, rangeStart+rangeInc, depth-1, x + (width/6)*i, y + height/3*2, width/3, height/3, i%2 == 0);
                }
            } else /* !isPointUp */ {
                for (int i = 0; i < 5; i++, rangeStart += rangeInc) {
                    paintTriangle(g2d, values, rangeStart, rangeStart+rangeInc, depth-1, x + (width/6)*i, y, width/3, height/3, i%2 != 0);
                }
                for (int i = 0; i < 3; i++, rangeStart += rangeInc) {
                    paintTriangle(g2d, values, rangeStart, rangeStart+rangeInc, depth-1, x + (width/6)*(i+1), y + height/3, width/3, height/3, i%2 != 0);
                }
                paintTriangle(g2d, values, rangeStart, rangeStart+rangeInc, depth-1, x + width/3, y + height/3*2, width/3, height/3, false);
                rangeStart += rangeInc;
            }
        }
    }
}