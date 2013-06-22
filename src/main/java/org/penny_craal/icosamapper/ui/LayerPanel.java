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

package org.penny_craal.icosamapper.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Polygon;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;
import javax.swing.event.MouseInputAdapter;

import org.penny_craal.icosamapper.map.GreyscaleLR;
import org.penny_craal.icosamapper.map.InvalidPathException;
import org.penny_craal.icosamapper.map.Layer;
import org.penny_craal.icosamapper.map.Path;
import org.penny_craal.icosamapper.ui.events.IMEvent;
import org.penny_craal.icosamapper.ui.events.IMEventHelper;
import org.penny_craal.icosamapper.ui.events.IMEventListener;
import org.penny_craal.icosamapper.ui.events.IMEventSource;

/**
 * A widget that displays a Layer.
 * @author Ville Jokela
 */
@SuppressWarnings("serial")
public class LayerPanel extends JPanel implements IMEventSource {
    private Layer layer;
    private Path zoom;
    private int drawDepth;
    private Insets insets;
    private static final int MIN_DRAWAREA_SIZE = 100;
    private static final Insets DEFAULT_INSETS = new Insets(8, 8, 8, 8);

    /**
     * Constructs the LayerPanel.
     * @param layer 
     * @param drawDepth 
     */
    public LayerPanel(Layer layer, int drawDepth, Insets insets) {
        this.layer = layer;
        this.drawDepth = drawDepth;
        this.insets = insets;
        zoom = null;
        
        setMinimumSize(new Dimension(
                insets.left + MIN_DRAWAREA_SIZE + insets.right,
                insets.top + MIN_DRAWAREA_SIZE + insets.bottom
        ));
        
        setPreferredSize(new Dimension(1100, 600));
    }
    
    public LayerPanel(Layer layer, int drawDepth) {
        this(layer, drawDepth, DEFAULT_INSETS);
    }
    
    private void updateMinimumSize() {
        setMinimumSize(new Dimension(
                insets.left + MIN_DRAWAREA_SIZE + insets.right,
                insets.top + MIN_DRAWAREA_SIZE + insets.bottom
        ));
    }
    
    public void setMargin(Insets insets) {
        this.insets = insets;
        updateMinimumSize();
    }
    
    public Insets getMargin() {
        return this.insets;
    }
    
    /**
     * Sets the displayed layer.
     * @param layer the layer to be displayed
     */
    public void setLayer(Layer layer) {
        this.layer = layer;
        if (zoom != null && !layer.isValidPath(zoom))
            zoom = null;
    }
    
    /**
     * Gets the layer that is being displayed.
     * @return the layer that is being displayed
     */
    public Layer getLayer() {
        return layer;
    }
    
    /**
     * Clears the panel so that it does not show anything.
     */
    public void clearLayer() {
        layer = null;
    }
    
    /**
     * Sets the depth at which the Layer is displayed.
     * @param drawDepth the draw depth
     */
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
        
        if (zoom == null) {
            int[] values = layer.renderArray(drawDepth);
            paintIcosahedron(
                    g2d,
                    values,
                    0,
                    values.length,
                    drawDepth,
                    insets.left,
                    insets.top,
                    (getWidth() - (insets.left + insets.right))/5.5,
                    (getHeight() - (insets.top + insets.bottom))/3.0,
                    true
            );
        } else /* zoom != null */ {
            // TODO
        }
    }
    
    private void paintIcosahedron(
            Graphics2D g2d,
            int[] values,
            int rangeStart,
            int rangeEnd,
            int depth,
            double x,
            double y,
            double width,
            double height,
            boolean topIsSkewedLeft
    ) {
        int rangeInc = rangeEnd/20;
        
        // triangles with point up
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 5; j++, rangeStart += rangeInc) {   // note increment to rageStart
                paintTriangle(
                        g2d,
                        values,
                        rangeStart,
                        rangeStart + rangeInc,
                        depth-1,
                        x + width*j + width/2*(topIsSkewedLeft ? i : 1-i),
                        y + height*i,
                        width,
                        height,
                        true
                );
            }
        }
        
        // triangles with point down
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 5; j++, rangeStart += rangeInc) {   // note increment to rageStart
                paintTriangle(
                        g2d,
                        values,
                        rangeStart,
                        rangeStart + rangeInc,
                        depth-1,
                        x + width*j + width/2*(topIsSkewedLeft ? i : 1-i),
                        y + height*(i+1),
                        width,
                        height,
                        false
                );
            }
        }
    }

    private void paintTriangle(
            Graphics2D g2d,
            int[] values,
            int rangeStart,
            int rangeEnd,
            int depth,
            double x,
            double y,
            double width,
            double height,
            boolean isPointUp
    ) {
        if (depth == 0) {
            Polygon p = new Polygon();
            p.addPoint((int) x,                 (int) (y + (isPointUp ? height : 0)));  // left side
            p.addPoint((int) (x + width),       (int) (y + (isPointUp ? height : 0)));  // right side
            p.addPoint((int) (x + width/2),     (int) (y + (isPointUp ? 0 : height)));  // point
            g2d.setColor(new Color(values[rangeStart]));
            g2d.draw(p);
            g2d.fillPolygon(p);
        } else {
            int rangeInc = (rangeEnd - rangeStart)/9;
            if (isPointUp) {
                paintTriangle(
                        g2d,
                        values,
                        rangeStart,
                        rangeStart+rangeInc,
                        depth-1,
                        x + width/3,
                        y,
                        width/3,
                        height/3,
                        true
                );
                rangeStart += rangeInc;
                for (int i = 0; i < 3; i++, rangeStart += rangeInc) {
                    paintTriangle(
                            g2d,
                            values,
                            rangeStart,
                            rangeStart+rangeInc,
                            depth-1,
                            x + (width/6)*(i+1),
                            y + height/3,
                            width/3,
                            height/3,
                            i%2 == 0
                    );
                }
                for (int i = 0; i < 5; i++, rangeStart += rangeInc) {
                    paintTriangle(
                            g2d,
                            values,
                            rangeStart,
                            rangeStart+rangeInc,
                            depth-1,
                            x + (width/6)*i,
                            y + height/3*2,
                            width/3,
                            height/3,
                            i%2 == 0
                    );
                }
            } else /* !isPointUp */ {
                for (int i = 0; i < 5; i++, rangeStart += rangeInc) {
                    paintTriangle(
                            g2d,
                            values,
                            rangeStart,
                            rangeStart+rangeInc,
                            depth-1,
                            x + (width/6)*i,
                            y,
                            width/3,
                            height/3,
                            i%2 != 0
                    );
                }
                for (int i = 0; i < 3; i++, rangeStart += rangeInc) {
                    paintTriangle(
                            g2d,
                            values,
                            rangeStart,
                            rangeStart+rangeInc,
                            depth-1,
                            x + (width/6)*(i+1),
                            y + height/3,
                            width/3,
                            height/3,
                            i%2 != 0
                    );
                }
                paintTriangle(
                        g2d,
                        values,
                        rangeStart,
                        rangeStart+rangeInc,
                        depth-1,
                        x + width/3,
                        y + height/3*2,
                        width/3,
                        height/3,
                        false
                );
                rangeStart += rangeInc;
            }
        }
    }
    
    protected static Layer createTestLayer() {
        Layer layer = new Layer("test-layer", new GreyscaleLR(), (byte) 0);
        
        try {
            for (int i = 0; i < 20; i++) {
                byte[] api = {(byte) i};
                layer.divide(new Path(api));
                for (int j = 0; j < 9; j++) {
                    byte[] apj = {(byte) i, (byte) j};
                    layer.setElement(new Path(apj), (byte) (256/20*i + 256/20/9*j));
                }
            }
        } catch (InvalidPathException ex) {
            throw new RuntimeException(ex);
        }
        
        return layer;
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
    
    private class Listener extends MouseInputAdapter {
        @Override
        public void mouseClicked(MouseEvent me) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void mouseEntered(MouseEvent me) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void mouseExited(MouseEvent me) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void mouseDragged(MouseEvent me) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void mouseMoved(MouseEvent me) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }
}