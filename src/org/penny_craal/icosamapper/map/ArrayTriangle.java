package org.penny_craal.icosamapper.map;

/**
 *
 * @author Ville Jokela
 */
public class ArrayTriangle implements Triangle {
    private byte[] vals;        // values
    private Triangle[] tris;    // triangles
    
    public ArrayTriangle(byte init) {
        vals = new byte[9];
        for (int i = 0; i < vals.length; i++) {
            vals[i] = init;
        }
        tris = null;
    }
    
    @Override
    public byte access(AccessPath ap) {
        if (ap.length() == 1) {
            return vals[ap.head()];
        } else if (tris == null) {
            throw new RuntimeException("Bad path, no such triangle");   // TODO: proper exception?
        } else {
            return tris[ap.head()].access(ap.rest());
        }
    }

    @Override
    public void subdivide(AccessPath ap) {
        if (ap.length() == 1) {
            if (tris == null) {
                tris = new Triangle[9];
                for (int i = 0; i < tris.length; i++) {
                    tris[i] = null;
                }
            }
            tris[ap.head()] = new ArrayTriangle(vals[ap.head()]);
        } else {
            tris[ap.head()].subdivide(ap.rest());
        }
    }

    @Override
    public void setAtPath(AccessPath ap, byte val) {
        if (ap.length() == 1) {
            vals[ap.head()] = val;
        } else {
            tris[ap.head()].setAtPath(ap.rest(), val);
            vals[ap.head()] = tris[ap.head()].getMeanValue();
        }
    }
    
    @Override
    public byte getMeanValue() {
        int n = 0;
        
        for (byte v: vals) {
            n += v;
        }
        
        return (byte) (n/9);
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{AT: { ");
        for (int i = 0; i < vals.length; i++) {
            sb.append(vals[i]);
            if (tris != null) {
                sb.append(": ");
                sb.append(tris[i].toString());
            }
            sb.append(", ");
        }
        sb.delete(sb.length() - 2, sb.length());
        sb.append(" }}");
        return sb.toString();
    }
}