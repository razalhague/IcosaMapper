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

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.List;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;

import org.penny_craal.icosamapper.map.layerrenderers.Greyscale;
import org.penny_craal.icosamapper.map.InvalidPathException;
import org.penny_craal.icosamapper.map.Layer;
import org.penny_craal.icosamapper.map.Path;
import org.penny_craal.icosamapper.ui.events.*;
import org.penny_craal.icosamapper.ui.events.Interact;

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
    private int opSize;
    private Insets insets;
    private Path mouseLocation;

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
    public LayerPanel(Layer layer, int drawDepth, int opSize, Insets insets) {
        this.layer = layer;
        this.drawDepth = drawDepth;
        this.insets = insets;
        this.opSize = opSize;
        zoom = null;
        mouseLocation = null;
        Listener listener = new Listener();
        addMouseListener(listener);
        addMouseMotionListener(listener);
        
        updateMinimumSize();
        
        setPreferredSize(new Dimension(insets.left + 1100 + insets.right, insets.top + 600 + insets.bottom));
    }
    
    public LayerPanel(Layer layer, int drawDepth, int opSize) {
        this(layer, drawDepth, opSize, DEFAULT_INSETS);
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
        if (zoom != null && !layer.isValidPath(zoom)) {
            zoom = null;
        }
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

    public void setOpSize(int opSize) {
        this.opSize = opSize;
    }

    public void setZoom(Path zoom) {
        System.out.println("zoom set to: " + zoom);
        this.zoom = zoom;
        mouseLocation = null;
        repaint();
    }

    public void zoomOut() {
        mouseLocation = null;
        if (zoom == null) {
            throw new RuntimeException("trying to zoom out when not zoomed in");
        } else {
            zoom = zoom.clipped();
        }
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        if (layer == null) {
            return;
        }

        boolean excepted;
        do {
            excepted = false;
            try {
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
                            (getWidth() - (insets.left + insets.right)) / 5.5,
                            (getHeight() - (insets.top + insets.bottom)) / 3.0,
                            topIsSkewedLeft
                    );
                } else /* zoom != null */ {
                    int[] values = layer.renderArray(zoom, drawDepth);
                    paintTriangle(
                            g2d,
                            values,
                            0,
                            values.length,
                            drawDepth,
                            insets.left,
                            insets.top,
                            (getWidth() - (insets.left + insets.right)),
                            (getHeight() - (insets.top + insets.bottom)),
                            isPointUp(zoom)
                    );
                }
            } catch (InvalidPathException e) {
                excepted = true;
                if (zoom != null) {
                    zoom = zoom.clipped();
                } else {
                    throw new RuntimeException("these exceptions should only rise from the zoom != null branch so this should never happen", e);
                }
            }
        } while (excepted);

        paintTarget(g2d);
    }

    private boolean isPointUp(Path zoom) {
        boolean pointUp = zoom.first() < 10;    // determines the icosahedron part
        while (zoom.length() != 1) {
            zoom = zoom.rest();
            pointUp = childIsPointUp(zoom.first(), pointUp);
        }

        return pointUp;
    }

    private boolean childIsPointUp(byte child, boolean isPointUp) {
        if (isPointUp) {
            return !(child == 2 || child == 5 || child == 7);
        } else {
            return child == 1 || child == 3 || child == 6;
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
            paintSingleTriangle(g2d, x, y, width, height, isPointUp, new Color(values[rangeStart]), true);
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

    private void paintTarget(Graphics2D g2d) {
        if (mouseLocation == null) {
            return;
        }
        Dimension drawArea = getDrawingArea();
        byte value;
        if (zoom == null) {
            value = layer.getElement(mouseLocation);
        } else {
            value = layer.getElement(zoom.append(mouseLocation));
        }
        Color elementColor = new Color(layer.getLayerRenderer().renderByte(value));
        Color color = contrastColor(elementColor);
        if (zoom != null) {
            paintTarget(g2d, mouseLocation, insets.left, insets.top, drawArea.getWidth(), drawArea.getHeight(), isPointUp(zoom), color);
        } else {
            double triangleWidth = drawArea.width / 5.5;
            double triangleHeight = drawArea.height / 3;
            double x, y;
            boolean pointUp;
            if (mouseLocation.first() < 5) {
                x = insets.left + ((mouseLocation.first() % 5) * triangleWidth) + ((topIsSkewedLeft ? 0 : 0.5) * triangleWidth);
                y = insets.top;
                pointUp = true;
            } else if (mouseLocation.first() < 10) {
                x = insets.left + ((mouseLocation.first() % 5) * triangleWidth) + ((topIsSkewedLeft ? 0.5 : 0) * triangleWidth);
                y = insets.top + triangleHeight;
                pointUp = true;
            } else if (mouseLocation.first() < 15) {
                x = insets.left + ((mouseLocation.first() % 5) * triangleWidth) + ((topIsSkewedLeft ? 0 : 0.5) * triangleWidth);
                y = insets.top + triangleHeight;
                pointUp = false;
            } else if (mouseLocation.first() < 20) {
                x = insets.left + ((mouseLocation.first() % 5) * triangleWidth) + ((topIsSkewedLeft ? 0.5 : 0) * triangleWidth);
                y = insets.top + (2 * triangleHeight);
                pointUp = false;
            } else {
                throw new RuntimeException("nonsensical path value: " + mouseLocation);
            }
            if (mouseLocation.length() == 1) {
                paintSingleTriangle(g2d, x, y, triangleWidth, triangleHeight, pointUp, color, false);
            } else {
                paintTarget(g2d, mouseLocation.rest(), x, y, triangleWidth, triangleHeight, pointUp, color);
            }
        }
    }

    private void paintTarget(Graphics2D g2d, Path path, double x, double y, double width, double height, boolean isPointUp, Color color) {
        double newHeight = height/3, newWidth = width/3;
        double newX, newY;
        if (isPointUp) {
            if (path.first() == 0) {
                newX = x + newWidth;
                newY = y;
            } else if (path.first() < 4) {
                newX = x + (0.5*newWidth) + ((path.first() - 1) * 0.5 * newWidth);
                newY = y + newHeight;
            } else if (path.first() < 9) {
                newX = x + ((path.first() - 4) * 0.5 * newWidth);
                newY = y + (newHeight * 2);
            } else {
                throw new RuntimeException("nonsensical path value: " + mouseLocation);
            }
        } else {
            if (path.first() < 5) {
                newX = x + (path.first() * 0.5 * newWidth);
                newY = y;
            } else if (path.first() < 8) {
                newX = x + (0.5*newWidth) + ((path.first() - 5) * 0.5 * newWidth);
                newY = y + newHeight;
            } else if (path.first() == 8) {
                newX = x + newWidth;
                newY = y + (newHeight * 2);
            } else {
                throw new RuntimeException("nonsensical path value: " + mouseLocation);
            }
        }
        if (path.length() == 1) {
            paintSingleTriangle(g2d, newX, newY, newWidth, newHeight, childIsPointUp(path.first(), isPointUp), color, false);
        } else {
            paintTarget(
                    g2d,
                    path.rest(),
                    newX,
                    newY,
                    newWidth,
                    newHeight,
                    childIsPointUp(path.first(), isPointUp),
                    color);
        }
    }

    private void paintSingleTriangle(Graphics2D g2d, double x, double y, double width, double height, boolean isPointUp, Color color, boolean fillTriangle) {
        Polygon p = new Polygon();
        p.addPoint((int) x,                 (int) (y + (isPointUp ? height : 0)));  // left side
        p.addPoint((int) (x + width),       (int) (y + (isPointUp ? height : 0)));  // right side
        p.addPoint((int) (x + width/2),     (int) (y + (isPointUp ? 0 : height)));  // point
        g2d.setColor(color);
        if (fillTriangle) {
            g2d.fillPolygon(p);
        } else {
            g2d.setStroke(new BasicStroke(2));
            g2d.draw(p);
        }
    }

    private Color contrastColor(Color color) {
        int avg = (color.getBlue() + color.getGreen() + color.getRed()) / 3;
        if (avg > 128) {
            return Color.black;
        } else {
            return Color.white;
        }
    }

    public static Layer createTestLayer() {
        Layer layer = new Layer("test-layer", new Greyscale(), (byte) 0);
        
        try {
            for (int i = 0; i < 20; i++) {
                byte[] api = {(byte) i};
                layer.divide(new Path(api));
                for (int j = 0; j < 9; j++) {
                    byte[] apj = {(byte) i, (byte) j};
                    layer.setElement(new Path(apj), (byte) (256.0/20*i + 256.0/20/9*j));
                }
            }
        } catch (InvalidPathException ex) {
            throw new RuntimeException(ex);
        }
        
        return layer;
    }
    
    /**
     * Checks if the point is withing the drawing area.
     * @param inRelationToPanel
     * @return 
     */
    private boolean withinDrawingArea(Point inRelationToPanel) {
        Dimension panelSize = getSize();
        if (inRelationToPanel.x < insets.left) {
            return false;
        } else if (inRelationToPanel.x >= (panelSize.width - insets.right)) {
            return false;
        } else if (inRelationToPanel.y < insets.top) {
            return false;
        } else if (inRelationToPanel.y >= (panelSize.height - insets.bottom)) {
            return false;
        } else {
            return true;
        }
    }

    private Dimension getDrawingArea() {
        Dimension panelSize = getSize();
        return new Dimension(panelSize.width - (insets.left + insets.right), panelSize.height - (insets.top + insets.bottom));
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

    private Path getPathFromMouseLocation(Point inRelationToPanel) {
        if (!withinDrawingArea(inRelationToPanel)) {
            return null;
        }
        Path path;
        Dimension mapSize = getDrawingArea();
        Point relMap = new Point(inRelationToPanel.x - insets.left, inRelationToPanel.y - insets.top);
        if (zoom == null) {
            path = resolvePathFromRelCoordsInArray(
                    relMap.getX() / mapSize.getWidth(),
                    relMap.getY() / mapSize.getHeight(),
                    topIsSkewedLeft,
                    childFromIcosahedronSRPC,
                    new LinkedList<Byte>(),
                    opSize
            );
        } else /* zoom != null */ {
            path = resolvePathFromRelCoordsInArray(
                    relMap.getX() / mapSize.getWidth(),
                    relMap.getY() / mapSize.getHeight(),
                    isPointUp(zoom),
                    childFromTrianglePRPC,
                    new LinkedList<Byte>(),
                    opSize
            );
        }

        return path;
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
            mouseLocation = getPathFromMouseLocation(me.getPoint());
            if (mouseLocation == null) {
                return;
            }
            Path fullPath;
            if (zoom == null) {
                fullPath = mouseLocation;
            } else {
                fullPath = zoom.append(mouseLocation);
            }
            boolean isPrimary;
            if (SwingUtilities.isLeftMouseButton(me)) {
                isPrimary = true;
            } else if (SwingUtilities.isRightMouseButton(me)) {
                isPrimary = false;
            } else {
                return;
            }
            fireEvent(new Interact(LayerPanel.this, fullPath, isPrimary));
            repaint();
        }

        @Override
        public void mouseEntered(MouseEvent me) {
            mouseLocation = getPathFromMouseLocation(me.getPoint());
            repaint();
        }

        @Override
        public void mouseExited(MouseEvent me) {
            mouseLocation = null;
            repaint();
        }

        @Override
        public void mouseDragged(MouseEvent me) {
            mouseClicked(me);
        }

        @Override
        public void mouseMoved(MouseEvent me) {
            mouseLocation = getPathFromMouseLocation(me.getPoint());
            repaint();
        }
    }
}