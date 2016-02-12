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

package org.penny_craal.icosamapper.map;

import java.io.Serializable;

import org.penny_craal.icosamapper.map.layerrenderers.LayerRenderer;

/**
 * A layer in the map. 
 * @author Ville Jokela
 */
public class Layer implements TriangleContainer, Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private LayerRenderer lr;
    private ArrayIcosahedron ih;
    
    public Layer(String name, LayerRenderer lr, byte init) {
        this.name = name;
        this.lr = lr;
        ih = new ArrayIcosahedron(init);
    }
    
    public String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }
    
    @Override
    public boolean isValidPath(Path p) {
        return ih.isValidPath(p);
    }

    @Override
    public byte getElement(Path p) throws InvalidPathException {
        return ih.getElement(p);
    }

    @Override
    public void divide(Path p) throws InvalidPathException {
        ih.divide(p);
    }
    
    @Override
    public void unite(Path p) throws InvalidPathException {
        ih.unite(p);
    }

    @Override
    public void setElement(Path p, byte val) throws InvalidPathException {
        ih.setElement(p, val);
    }

    @Override
    public byte getMeanValue() {
        return ih.getMeanValue();
    }
    
    @Override
    public byte[] render(int depth) {
        return ih.render(depth);
    }

    public byte[] render(Path zoom, int depth) throws InvalidPathException {
        return ih.render(zoom, depth);
    }

    public int[] renderArray(int depth) {
        return lr.renderArray(render(depth));
    }

    public int[] renderArray(Path zoom, int depth) throws InvalidPathException {
        return lr.renderArray(render(zoom, depth));
    }

    @Override
    public int getSize() {
        return 1;
    }

    public LayerRenderer getLayerRenderer() {
        return lr;
    }

    public void setLayerRenderer(LayerRenderer layerRenderer) {
        this.lr = layerRenderer;
    }

    @Override
    public String toString() {
        return "{ name: " + name + ", LR: " + lr.getType() + ", " + ih.toString() + " }";
    }
    
    @Override
    public boolean equals(Object other) {
        if (other instanceof Layer) {
            Layer that = (Layer) other;
            return this.name.equals(that.name) && this.lr.equals(that.lr) && this.ih.equals(that.ih);
        } else {
            return false;
        }
    }
    
    @Override
    public int hashCode() {
        int result = 89;
        
        result = 37 * result + name.hashCode();
        result = 37 * result + lr.hashCode();
        result = 37 * result + ih.hashCode();
        
        return result;
    }
}