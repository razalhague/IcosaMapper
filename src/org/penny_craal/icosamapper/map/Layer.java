package org.penny_craal.icosamapper.map;

import java.io.Serializable;

/**
 *
 * @author Ville Jokela
 */
public class Layer implements Serializable, TriangleContainer {
    private String name;
    private LayerRenderer lr;
    private Icosahedron ih;
    
    public Layer(String name, LayerRenderer lr) {
        this.name = name;
        this.lr = lr;
    }
    
    public String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }
    
    @Override
    public boolean isValidPath(AccessPath ap) {
        return ih.isValidPath(ap);
    }

    @Override
    public byte access(AccessPath ap) throws BadPathException {
        return ih.access(ap);
    }

    @Override
    public void subdivide(AccessPath ap) throws BadPathException {
        ih.subdivide(ap);
    }

    @Override
    public void setAtPath(AccessPath ap, byte val) throws BadPathException {
        ih.setAtPath(ap, val);
    }

    @Override
    public byte getMeanValue() {
        return ih.getMeanValue();
    }
}