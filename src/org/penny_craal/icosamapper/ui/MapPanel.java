package org.penny_craal.icosamapper.ui;

import java.awt.Color;
import java.awt.Dimension;
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
    
    public MapPanel() {
        layer = null;
        //Dimension d = new Dimension(400,10000);
        //setPreferredSize(d);
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
        Graphics2D g2 = (Graphics2D) g;
        System.out.println("" + getWidth() + ", " + getHeight());
        int width = (int) (getWidth()/5.5);
        int height = getHeight()/3;
        
        // triangles with point up
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 5; j++) {
                Polygon p = new Polygon();
                p.addPoint(width*j + width/2*i + width/2, height*i);        // point
                p.addPoint(width*j + width/2*i, height*i + height);         // left side
                p.addPoint(width*j + width/2*i + width, height*i + height); // right side
                g2.setColor(Color.BLACK);
                g2.draw(p);
                g2.fillPolygon(p);
            }
        }
        
        // triangles with point down
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 5; j++) {
                Polygon p = new Polygon();
                p.addPoint(width*j + width/2*i + width/2, height*i + 2*height);        // point
                p.addPoint(width*j + width/2*i, height*i + height);         // left side
                p.addPoint(width*j + width/2*i + width, height*i + height); // right side
                g2.setColor(Color.RED);
                g2.draw(p);
                g2.fillPolygon(p);
            }
        }
    }
    
    private void paintIcosahedron(int heigth, int width, int depth) {
        
    }
}