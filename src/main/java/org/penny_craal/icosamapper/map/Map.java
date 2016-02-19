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
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * A map.
 * @author Ville Jokela
 */
public class Map implements Serializable {
    private static final long serialVersionUID = 1L;
    private LinkedHashMap<String, Layer> layers;
    
    /**
     * Constructs an empty map.
     */
    public Map() {
        layers = new LinkedHashMap<>(1);
    }
    
    /**
     * Adds the specified layer to the Map.
     * @param layer     the layer to be added
     */
    public void addLayer(Layer layer) {
        layers.put(layer.getName(), layer);
    }
    
    /**
     * Retrieves the specified layer from the Map.
     * @param layerName the layer to be retrieved
     * @return          the specified layer
     */
    public Layer getLayer(String layerName) {
        return layers.get(layerName);
    }
    
    /**
     * Removes the specified layer from the Map.
     * @param layerName the layer to be removed
     */
    public void removeLayer(String layerName) {
        layers.remove(layerName);
    }
    
    /**
     * Renames a layer in the Map.
     * @param oldName   old name
     * @param newName   new name
     */
    public void renameLayer(String oldName, String newName) {
        Layer layer = layers.get(oldName);
        layer.setName(newName);
        layers.remove(oldName);
        layers.put(newName, layer);
    }
    
    /**
     * Lists the names of layers.
     * @return          list of layer names
     */
    public List<String> getLayerNames() {
        return new ArrayList<>(layers.keySet());
    }
    
    /**
     * Gets an element from the specified location.
     * @param layerName name of the layer
     * @param p         the path to the element
     * @return          the specified element
     */
    public byte getElement(String layerName, Path p) {
        return layers.get(layerName).getElement(p);
    }
    
    /**
     * Sets the element at the specified location to the given value.
     * @param layerName name of the layer
     * @param p         path to the element
     * @param val       value for the element
     * @throws InvalidPathException
     */
    public void setElement(String layerName, Path p, byte val) throws InvalidPathException {
        layers.get(layerName).setElement(p, val);
    }
    
    /**
     * Divides the specified element.
     * @param layerName name of the layer
     * @param p         path to the element
     * @throws InvalidPathException when the given path does not point to an element 
     */
    public void divide(String layerName, Path p) throws InvalidPathException {
        layers.get(layerName).divide(p);
    }
    
    /**
     * Unites the specified element (removes children).
     * @param layerName name of the layer
     * @param p         path to the element
     * @throws InvalidPathException when the given path does not point to an element
     */
    public void unite(String layerName, Path p) throws InvalidPathException {
        layers.get(layerName).unite(p);
    }
    
    /**
     * Renders the values of one layer at the given depth as colours. Elements that do not exist have the colour of
     * their parent.
     * @param layerName the layer to be rendered
     * @param depth     how deeply to render
     * @return          an array depicting the Map at the specified depth
     */
    public int[] renderArray(String layerName, int depth) {
        return layers.get(layerName).renderArray(depth);
    }
    
    @Override
    public boolean equals(Object other) {
        if (other instanceof Map) {
            Map that = (Map) other;
            return this.layers.equals(that.layers);
        } else {
            return false;
        }
    }
    
    @Override
    public int hashCode() {
        return layers.hashCode();
    }
}