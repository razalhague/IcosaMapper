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
import java.awt.Point;
import java.awt.Polygon;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.event.MouseInputAdapter;

import org.penny_craal.icosamapper.map.GreyscaleLR;
import org.penny_craal.icosamapper.map.InvalidPathException;
import org.penny_craal.icosamapper.map.Layer;
import org.penny_craal.icosamapper.map.Path;
import org.penny_craal.icosamapper.ui.events.Draw;
import org.penny_craal.icosamapper.ui.events.IMEvent;
import org.penny_craal.icosamapper.ui.events.IMEventHelper;
import org.penny_craal.icosamapper.ui.events.IMEventListener;
import org.penny_craal.icosamapper.ui.events.IMEventSource;

/**
 * A widget that displays a Layer.
 * 
 * How the 1D arrays in Layer are interpreted:
 * - Icosahedron:
 *   /\    /\    /\    /\    /\
 *  /0 \  /1 \  /2 \  /3 \  /4 \
 * /____\/____\/____\/____\/____\
 * \    /\    /\    /\    /\    /\
 *  \10/5 \11/6 \12/7 \13/8 \14/9 \
 *   \/____\/____\/____\/____\/____\
 *    \    /\    /\    /\    /\    /
 *     \15/  \16/  \17/  \18/  \19/
 *      \/    \/    \/    \/    \/
 * - Triangle:
 *         /\
 *        /0 \
 *       /____\
 *      /\    /\
 *     /1 \2 /3 \
 *    /____\/____\
 *   /\    /\    /\
 *  /4 \5 /6 \7 /8 \
 * /____\/____\/____\
 * 
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
    // camelCase because this might later be made non-static and configurable
    private static final boolean topIsSkewedLeft = true;

    /**
     * Constructs the LayerPanel.
     * @param layer
     * @param drawDepth
     * @param insets 
     */
    public LayerPanel(Layer layer, int drawDepth, Insets insets) {
        this.layer = layer;
        this.drawDepth = drawDepth;
        this.insets = insets;
        zoom = null;
        Listener listener = new Listener();
        addMouseListener(listener);
        addMouseMotionListener(listener);
        
        updateMinimumSize();
        
        setPreferredSize(new Dimension(insets.left + 1100 + insets.right, insets.top + 600 + insets.bottom));
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
                    topIsSkewedLeft
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
            for (int j = 0; j < 5; j++, rangeStart += rangeInc) {   // note increment to rangeStart
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
            for (int j = 0; j < 5; j++, rangeStart += rangeInc) {   // note increment to rangeStart
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
    
    /**
     * Checks if the point is outside the icosahedron's bounding rectangle.
     * @param panelSize
     * @param relPanel
     * @return 
     */
    private boolean outsideIcosaRect(Point relPanel) {
        Dimension panelSize = getSize();
        if (relPanel.x < insets.left)
            return true;
        if (relPanel.x >= (panelSize.width - insets.right))
            return true;
        if (relPanel.y < insets.top)
            return true;
        if (relPanel.y >= (panelSize.height - insets.bottom))
            return true;

        return false;
    }
    
    // [skew:0=right,1=left][row][position:0=upper,1=lower][column]; -1 = no child
    private static final int[][][][] childFromIcosahedronSRPC = new int[][][][] {
        {
            {{  -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1  },
            {   -1, 0,  0,  1,  1,  2,  2,  3,  3,  4,  4   }},
            {{  -1, 10, 10, 11, 11, 12, 12, 13, 13, 14, 14  },
            {   5,  5,  6,  6,  7,  7,  8,  8,  9,  9,  -1  }},
            {{  15, 15, 16, 16, 17, 17, 18, 18, 19, 19, -1  },
            {   -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1  }},
        },
        {
            {{  -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1  },
            {   0,  0,  1,  1,  2,  2,  3,  3,  4,  4,  -1  }},
            {{  10, 10, 11, 11, 12, 12, 13, 13, 14, 14, -1  },
            {   -1, 5,  5,  6,  6,  7,  7,  8,  8,  9,  9   }},
            {{  -1, 15, 15, 16, 16, 17, 17, 18, 18, 19, 19  },
            {   -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1  }},
        },
    };
    
    // [point:0=down,1=up][row][position:0=upper,1=lower][column]; -1 = no child
    private static final int[][][][] childFromTrianglePRPC = new int[][][][] {
        {
            {{  0,  0,  2,  2,  4,  4   },
            {   -1, 1,  1,  3,  3,  -1  }},
            {{  -1, 5,  5,  7,  7,  -1  },
            {   -1, -1, 6,  6,  -1, -1  }},
            {{  -1, -1, 8,  8,  -1, -1  },
            {   -1, -1, -1, -1, -1, -1  }},
        },
        {
            {{  -1, -1, -1, -1, -1, -1  },
            {   -1, -1, 0,  0,  -1, -1  }},
            {{  -1, -1, 2,  2,  -1, -1  },
            {   -1, 1,  1,  3,  3,  -1  }},
            {{  -1, 5,  5,  7,  7,  -1  },
            {   4,  4,  6,  6,  8,  8   }},
        },
    };
    
    /**
     * Resolves a Path based on coordinates and an array.
     * @param x                     x coordinate, from 0 to 1
     * @param y                     y coordinate, from 0 to 1
     * @param topLeftCornerIsSWNE   is the topLeftCorner of the array SWNE or NWSE
     * @param array                 the array describing the children
     * @param pathSoFar             accumulator for the elements of the path that have already been resolved above 
     * @param depth                 how deeply the Path
     * @return                      Path that corresponds to the point
     */
    private Path resolvePathFromRelCoordsInArray(
            double x,
            double y,
            boolean topLeftCornerIsSWNE,
            int[][][][] array,
            List<Byte> pathSoFar,
            int depth
    ) {
        // first we divide the area into rectangles based on the dimensions of the array
        double rectWidth = 1.0 / array[0][0][0].length;
        double rectHeight = 1.0 / array[0].length;
        
        // then we find out which of these rectangles the point is in
        int nRectFromLeft = (int) (x / rectWidth);
        int nRectFromTop = (int) (y / rectHeight);
        
        // then we find out which way that rectangle is bisected, SW-to-NE or NW-to-SE
        // this is a checkerboard pattern, with the value of the upper left corner as !topIsSkewedLeft
        // the first == is being used as XNOR
        boolean rectangleIsBisectedSWNE = topLeftCornerIsSWNE == ((nRectFromLeft + nRectFromTop) % 2 == 0);
        
        // then we calculate the point relative to the rectangle and normalize the coordinates within the rectangle
        double relRectangleX = x % rectWidth;
        double relRectangleY = y % rectHeight;
        double normalizedRelRectangleX = relRectangleX / rectWidth;
        double normalizedRelRectangleY = relRectangleY / rectHeight;
        
        boolean isLower;
        if (rectangleIsBisectedSWNE) {
            isLower = normalizedRelRectangleX + normalizedRelRectangleY >= 1.0;
        } else {
            isLower = normalizedRelRectangleX < normalizedRelRectangleY;
        }
        
        int child = array[topLeftCornerIsSWNE?1:0][nRectFromTop][isLower?1:0][nRectFromLeft];
        if (child == -1) {
            return null;
        } else if (depth == 1) {
            pathSoFar.add((byte) child);
            return new Path(pathSoFar);
        } else {
            pathSoFar.add((byte) child);
            return resolvePathFromRelCoordsInArray(
                    // (rIBSWNE == iL) checks whether the rectangle contains the left or right half of the triangle
                    normalizedRelRectangleX/2 + (rectangleIsBisectedSWNE == isLower? 0: 0.5),
                    normalizedRelRectangleY,
                    isLower,
                    childFromTrianglePRPC,  // NOTE: explicitly pass the triangle-array, since 
                    pathSoFar,
                    depth-1
            );
        }
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
            if (zoom != null)   // won't handle zoom yet
                throw new UnsupportedOperationException("Not supported yet.");
            Dimension panelSize = getSize();
            Point relPanel = me.getPoint();
            if (outsideIcosaRect(relPanel))
                return;
            Dimension mapSize = new Dimension(panelSize.width - (insets.left + insets.right), panelSize.height - (insets.top + insets.bottom));
            Point relMap = new Point(relPanel.x - insets.left, relPanel.y - insets.top);
            Path path = resolvePathFromRelCoordsInArray(
                    relMap.getX() / mapSize.getWidth(),
                    relMap.getY() / mapSize.getHeight(),
                    topIsSkewedLeft,
                    childFromIcosahedronSRPC,
                    new LinkedList<Byte>(),
                    drawDepth
            );
            if (path == null)
                return; // outside of icosahedron
            fireEvent(new Draw(LayerPanel.this, path));
        }

        @Override
        public void mouseEntered(MouseEvent me) {
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void mouseExited(MouseEvent me) {
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void mouseDragged(MouseEvent me) {
            mouseClicked(me);
        }

        @Override
        public void mouseMoved(MouseEvent me) {
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }
}