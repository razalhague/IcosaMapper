package org.penny_craal.icosamapper.map;

import java.io.Serializable;

/**
 *
 * @author Ville Jokela
 */
public class Layer implements Serializable {
    private String name;
    private LayerRenderer lr;
    private Icosahedron ih;
    
    public Layer(String name, LayerRenderer lr, byte init) {
        this.name = name;
        this.lr = lr;
        ih = new Icosahedron(init);
    }
    
    public String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }
    
    public boolean isValidPath(AccessPath ap) {
        return ih.isValidPath(ap);
    }

    public byte access(AccessPath ap) throws BadPathException {
        return ih.access(ap);
    }

    public void subdivide(AccessPath ap) throws BadPathException {
        ih.subdivide(ap);
    }

    public void setAtPath(AccessPath ap, byte val) throws BadPathException {
        ih.setAtPath(ap, val);
    }

    public byte getMeanValue() {
        return ih.getMeanValue();
    }
    
    public int[] renderAtDepth(int depth) {
        return lr.renderArray(ih.renderAtDepth(depth));
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{L: { name: ");
        sb.append(name);
        sb.append(", LR: ");
        sb.append(lr.getType());
        sb.append(", ");
        sb.append(ih.toString());
        sb.append(" }}");
        return sb.toString();
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