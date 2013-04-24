package org.penny_craal.icosamapper.map;

import java.io.Serializable;

/**
 *
 * @author Ville Jokela
 */
abstract public class ArrayTriangleContainer implements TriangleContainer, Serializable {
    private byte[] vals;                // values
    private TriangleContainer[] tris;   // triangles
    private final int size;
    private final String name;
    
    public ArrayTriangleContainer(int size, String name, byte init) {
        if (size != 9 && size != 20) {
            throw new RuntimeException("Container size should be either 9 or 20");
        }
        this.size = size;
        this.name = name;
        
        vals = new byte[size];
        tris = null;
        
        for (int i = 0; i < vals.length; i++) {
            vals[i] = init;
        }
    }

    @Override
    public boolean isValidPath(AccessPath ap) {
        if (ap.length() == 1) {
            return true;
        } else if (tris != null && tris[ap.head()] != null) {
            return tris[ap.head()].isValidPath(ap.rest());
        } else {
            return false;
        }
    }
    
    @Override
    public byte access(AccessPath ap) throws BadPathException {
        if (ap.length() == 1) {
            return vals[ap.head()];
        } else if (tris != null || tris[ap.head()] != null) {
            return tris[ap.head()].access(ap.rest());
        } else {
            throw new BadPathException(ap);
        }
    }
    
    @Override
    public void subdivide(AccessPath ap) throws BadPathException {
        if (ap.length() == 1) {
            if (tris == null) {
                tris = new TriangleContainer[size];
                for (int i = 0; i < tris.length; i++) {
                    tris[i] = null;
                }
                tris[ap.head()] = new ArrayTriangle(vals[ap.head()]);
            } else if (tris[ap.head()] != null) {
                for (int i = 0; i < tris.length; i++) {
                    byte[] path = { (byte)i };
                    tris[ap.head()].subdivide(new AccessPath(path));
                }
            } else {
                tris[ap.head()] = new ArrayTriangle(vals[ap.head()]);
            }
        } else if (tris != null){
            tris[ap.head()].subdivide(ap.rest());
        } else {
            throw new BadPathException(ap);
        }
    }

    @Override
    public void setAtPath(AccessPath ap, byte val) throws BadPathException {
        if (ap.length() == 1) {
            vals[ap.head()] = val;
        } else if (tris != null && tris[ap.head()] != null) {
            tris[ap.head()].setAtPath(ap.rest(), val);
            vals[ap.head()] = tris[ap.head()].getMeanValue();
        } else {
            throw new BadPathException(ap);
        }
    }
    
    @Override
    public byte getMeanValue() {
        int n = 0;
        
        for (byte v: vals) {
            n += v;
        }
        
        return (byte) (n/size);
    }
    
    @Override
    public byte[] renderAtDepth(int depth) {
        if (depth == 1) {
            return vals;
        }
        byte[] values = new byte[getSizeAtDepth(depth)];
        byte[] subVals = null;
        for (int i = 0; i < size; i++) {
            if (tris != null && tris[i] != null) {
                subVals = tris[i].renderAtDepth(depth - 1);
            }
            for (int j = 0; j < values.length/size; j++) {
                if (tris == null || tris[i] == null) {
                    values[values.length/size*i + j] = vals[i];
                } else /* tris[i] != null */ {
                    values[values.length/size*i + j] = subVals[j];
                }
            }
        }
        
        return values;
    }
    
    private int getSizeAtDepth(int depth) {
        // TODO: recursive calculation?
        if (size == 20) {
            return (int) (20 * Math.pow(9, depth-1));
        } else {
            return (int) Math.pow(9, depth);
        }
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append(name);
        sb.append(": { ");
        for (int i = 0; i < vals.length; i++) {
            sb.append(vals[i] & 0xFF);
            if (tris != null) {
                if (tris[i] != null) {
                    sb.append(": ");
                    sb.append(tris[i].toString());
                }
            }
            sb.append(", ");
        }
        sb.delete(sb.length() - 2, sb.length());
        sb.append(" }}");
        return sb.toString();
    }
    
    /* TODO: equality test for when the other object is a TC, but not an ATC */
    @Override
    public boolean equals(final Object other) {
        if (!(other instanceof ArrayTriangleContainer)) {
            return false;
        }
        final ArrayTriangleContainer that = (ArrayTriangleContainer) other;
        if (this.size != that.size || !this.name.equals(that.name)) {
            return false;
        }
        if ((this.tris == null) != (that.tris == null)) {             // either both must be null, or both must be non-null
            return false;
        }
        for (int i = 0; i < this.size; i++) {
            if (this.vals[i] != that.vals[i]) {
                return false;
            }
            if (this.tris != null) {    // it is established higher up that if this.tris is not null, neither is that.tris
                if (this.tris[i] != null && !this.tris[i].equals(that.tris[i])) {
                    return false;
                } else if (this.tris[i] == null && that.tris[i] != null) {
                    return false;
                }
            }
        }
        
        return true;
    }
    
    @Override
    public int hashCode() {
        int result = 89;
        for (byte v: vals) {
            result = 37 * result + (int) v;
        }
        if (tris != null) {
            for (TriangleContainer tc: tris) {
                if (tc != null) {
                    result = 37 * result + tc.hashCode();
                } else {
                    result = 37 * result;
                }
            }
        }
        result = 37 * result + size;
        result = 37 * result + name.hashCode();
        
        return result;
    }
}