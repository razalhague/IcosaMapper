package org.penny_craal.icosamapper.map;

import java.io.Serializable;
import java.util.HashMap;

/**
 *
 * @author Ville Jokela
 */
public class Map implements Serializable {
    java.util.Map<String,Layer> layers;
    
    public Map() {
        layers = new HashMap();
    }
    
    public void addLayer(Layer layer) {
        layers.put(layer.getName(), layer);
    }
    
    public Layer getLayer(String name) {
        return layers.get(name);
    }
    
    public void removeLayer(String name) {
        layers.remove(name);
    }
    
    public void renameLayer(String oldName, String newName) {
        Layer l = layers.get(oldName);
        l.setName(newName);
        layers.remove(oldName);
        layers.put(newName, l);
    }
    
    public byte access(String name, AccessPath ap) throws BadPathException {
        return layers.get(name).access(ap);
    }

    public void subdivide(String name, AccessPath ap) throws BadPathException {
        layers.get(name).subdivide(ap);
    }

    public void setAtPath(String name, AccessPath ap, byte val) throws BadPathException {
        layers.get(name).setAtPath(ap, val);
    }

    public byte getMeanValue(String name) {
        return layers.get(name).getMeanValue();
    }
    
    public int[] renderAtDepth(String layer, int depth) {
        return layers.get(layer).renderAtDepth(depth);
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