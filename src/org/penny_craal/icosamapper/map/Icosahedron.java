package org.penny_craal.icosamapper.map;

/**
 *
 * @author Ville Jokela & James Pearce
 */
public class Icosahedron {
    private byte[] vals;        // values
    private Triangle[] tris;    // triangles
    
    public Icosahedron(byte init) {
        vals = new byte[20];
        tris = null;
        
        for (int i = 0; i < vals.length; i++) {
            vals[i] = init;
        }
    }

    public byte access(AccessPath ap) throws BadPathException {
        if (ap.length() == 1) {
            return vals[ap.head()];
        } else if (tris != null) {
            return tris[ap.head()].access(ap.rest());
        } else {
            throw new BadPathException(ap);
        }
    }
    
    public void subdivide(AccessPath ap) throws BadPathException {
        if (ap.length() == 1) {
            if (tris == null) {
                tris = new Triangle[20];
                for (int i = 0; i < tris.length; i++) {
                    tris[i] = null;
                }
            }
            tris[ap.head()] = new ArrayTriangle(vals[ap.head()]);
        } else if (tris != null) {
            tris[ap.head()].subdivide(ap.rest());
        } else {
            throw new BadPathException(ap);
        }
    }
    
    public void setAtPath(AccessPath ap, byte val) throws BadPathException {
        if (ap.length() == 1) {
            vals[ap.head()] = val;
        } else if (tris != null) {
            tris[ap.head()].setAtPath(ap.rest(), val);
            vals[ap.head()] = tris[ap.head()].getMeanValue();
        } else {
            throw new BadPathException(ap);
        }
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{IH: { ");
        for (int i = 0; i < vals.length; i++) {
            sb.append(vals[i]);
            if (tris != null && tris[i] != null) {
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